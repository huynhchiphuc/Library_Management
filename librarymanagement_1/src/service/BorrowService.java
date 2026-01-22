/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.Date;
import java.util.List;

import dao.BookCopyDAO;
import dao.BorrowDetailDAO;
import dao.BorrowSlipDAO;
import dao.PenaltyDAO;
import model.BookCopy;
import model.BorrowDetail;
import model.BorrowSlip;
import model.Penalty;
import model.User;
import util.Constants;
import util.DateUtil;

/**
 *
 * @author ASUS
 */
public class BorrowService {
    
    private BorrowSlipDAO borrowSlipDAO;
    private BorrowDetailDAO borrowDetailDAO;
    private BookCopyDAO bookCopyDAO;
    private PenaltyDAO penaltyDAO;
    private AuditService auditService;

    public BorrowService() {
        borrowSlipDAO = new BorrowSlipDAO();
        borrowDetailDAO = new BorrowDetailDAO();
        bookCopyDAO = new BookCopyDAO();
        penaltyDAO = new PenaltyDAO();
        auditService = new AuditService();
    }
    
    public boolean borrowBooks(int maDocGia, List<BookCopy> books, Date hanTra) {
        // 1. Create Borrow Slip
        BorrowSlip slip = new BorrowSlip();
        slip.setMaDocGia(maDocGia);
        slip.setNgayMuon(new Date()); // Now
        slip.setHanTra(hanTra);
        slip.setTrangThai(Constants.BORROW_STATUS_ACTIVE); // Active
        
        int slipId = borrowSlipDAO.insert(slip);
        if (slipId == -1) return false;
        
        // 2. Add details & Update book status
        boolean success = true;
        StringBuilder bookTitles = new StringBuilder();
        for (BookCopy book : books) {
            BorrowDetail detail = new BorrowDetail();
            detail.setMaPhieuMuon(slipId);
            detail.setMaCuonSach(book.getMaCuonSach());
            detail.setTinhTrangTra("Dang muon"); // Initial status
            
            if (!borrowDetailDAO.insert(detail)) {
                success = false;
            }
            
            // Update Book Status to Borrowed
            bookCopyDAO.updateStatus(book.getMaCuonSach(), Constants.BOOK_STATUS_BORROWED);
            
            if (bookTitles.length() > 0) bookTitles.append(", ");
            bookTitles.append(book.getTuaDe());
        }
        
        // 3. Log audit if successful
        if (success) {
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null) {
                String description = String.format("Tạo phiếu mượn (ID: %d) cho độc giả (Mã: %d) - Số lượng sách: %d (%s)", 
                    slipId, maDocGia, books.size(), bookTitles.toString());
                auditService.logAction(currentUser.getId(), "INSERT", "PhieuMuon", slipId, description);
            }
        }
        
        return success;
    }

    public String returnBook(String barcode) {
        // 1. Find book
        BookCopy book = bookCopyDAO.getBookCopyByBarcode(barcode);
        if (book == null) {
            return "Không tìm thấy sách với mã vạch: " + barcode;
        }
        
        // 2. Check if borrowed
        if (!borrowDetailDAO.isBookBorrowed(book.getMaCuonSach())) {
            return "Sách này hiện không được mượn trên hệ thống!";
        }
        
        // 3. Get borrow detail to check overdue
        BorrowDetail detail = borrowDetailDAO.getBorrowDetailByCopy(book.getMaCuonSach());
        if (detail == null) {
            return "Không tìm thấy thông tin mượn sách!";
        }
        
        // 4. Get borrow slip for due date
        BorrowSlip slip = borrowSlipDAO.getBorrowSlip(detail.getMaPhieuMuon());
        if (slip == null) {
            return "Không tìm thấy phiếu mượn!";
        }
        
        Date returnDate = new Date();
        Date dueDate = slip.getHanTra();
        
        // 5. Calculate penalty if overdue
        boolean isOverdue = DateUtil.isOverdue(dueDate, returnDate);
        String message = "Trả sách thành công: " + book.getTuaDe();
        
        if (isOverdue) {
            long overdueDays = DateUtil.calculateOverdueDays(dueDate, returnDate);
            double penaltyAmount = DateUtil.calculatePenaltyAmount(dueDate, returnDate);
            
            // Create penalty record
            Penalty penalty = new Penalty();
            penalty.setMaChiTiet(detail.getMaChiTiet());
            penalty.setMaDocGia(slip.getMaDocGia());
            penalty.setLyDo("Trả sách quá hạn " + overdueDays + " ngày");
            penalty.setSoTien(penaltyAmount);
            penalty.setDaDongTien(false);
            penalty.setNgayTao(returnDate);
            
            penaltyDAO.insert(penalty);
            
            message = String.format("Trả sách thành công: %s\n⚠️ QUÁ HẠN %d ngày\n💰 Phí phạt: %,.0f VNĐ\nVui lòng thanh toán phạt!", 
                                   book.getTuaDe(), overdueDays, penaltyAmount);
        }
        
        // 6. Process return
        boolean updated = borrowDetailDAO.updateReturn(book.getMaCuonSach(), returnDate, "Da tra");
        if (updated) {
            // Update book status to Available
            bookCopyDAO.updateStatus(book.getMaCuonSach(), Constants.BOOK_STATUS_AVAILABLE);
            
            // Log audit for return
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null) {
                String auditDesc = isOverdue 
                    ? String.format("Trả sách: %s (Barcode: %s) - QUÁ HẠN", book.getTuaDe(), barcode)
                    : String.format("Trả sách: %s (Barcode: %s)", book.getTuaDe(), barcode);
                auditService.logAction(currentUser.getId(), "UPDATE", "ChiTietPhieuMuon", detail.getMaChiTiet(), auditDesc);
            }
            
            return message;
        } else {
            return "Lỗi khi cập nhật phiếu mượn!";
        }
    }
}

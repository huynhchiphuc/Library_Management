/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.BookCopyDAO;
import dao.BorrowDetailDAO;
import dao.BorrowSlipDAO;
import java.util.Date;
import java.util.List;
import model.BookCopy;
import model.BorrowDetail;
import model.BorrowSlip;

/**
 *
 * @author ASUS
 */
public class BorrowService {
    
    private BorrowSlipDAO borrowSlipDAO;
    private BorrowDetailDAO borrowDetailDAO;
    private BookCopyDAO bookCopyDAO;

    public BorrowService() {
        borrowSlipDAO = new BorrowSlipDAO();
        borrowDetailDAO = new BorrowDetailDAO();
        bookCopyDAO = new BookCopyDAO();
    }
    
    public boolean borrowBooks(int maDocGia, List<BookCopy> books, Date hanTra) {
        // 1. Create Borrow Slip
        BorrowSlip slip = new BorrowSlip();
        slip.setMaDocGia(maDocGia);
        slip.setNgayMuon(new Date()); // Now
        slip.setHanTra(hanTra);
        slip.setTrangThai(0); // Active
        
        int slipId = borrowSlipDAO.insert(slip);
        if (slipId == -1) return false;
        
        // 2. Add details & Update book status
        boolean success = true;
        for (BookCopy book : books) {
            BorrowDetail detail = new BorrowDetail();
            detail.setMaPhieuMuon(slipId);
            detail.setMaCuonSach(book.getMaCuonSach());
            detail.setTinhTrangTra("Dang muon"); // Initial status
            
            if (!borrowDetailDAO.insert(detail)) {
                success = false;
            }
            
            // Update Book Status to 2 (Borrowed)
            bookCopyDAO.updateStatus(book.getMaCuonSach(), 2);
        }
        
        return success;
    }
}

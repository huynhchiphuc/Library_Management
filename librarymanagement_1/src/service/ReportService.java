/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;

import dao.ReportDAO;

/**
 *
 * @author ASUS
 */
public class ReportService {
    
    private ReportDAO reportDAO;

    public ReportService() {
        reportDAO = new ReportDAO();
    }
        /**
     * Đếm tổng số đầu sách
     * @return Số lượng đầu sách (bảng DauSach)
     */    public int getTotalBooks() {
        return reportDAO.countTotalBookCopies();
    }
    
    public int getTotalReaders() {
        return reportDAO.countTotalReaders();
    }
    
    public int getBorrowedBooks() {
        return reportDAO.countBorrowedBooks();
    }
    
    /**
     * Đếm số phiếu mượn quá hạn
     * @return Số lượng phiếu mượn có HanTra < ngày hiện tại và chưa trả
     * Xử lý: Đếm số bản ghi trong ChiTietMuonTra có NgayTra IS NULL và HanTra < CURDATE()
     */
    public int getOverdueCount() {
        return reportDAO.countOverdueBorrows();
    }
    
    /**
     * Lấy danh sách top độc giả mượn sách nhiều nhất
     * @return List<Object[]> mỗi phần tử chứa: [Họ tên, Số lần mượn]
     * Xử lý: GROUP BY độc giả, đếm số phiếu mượn, ORDER BY giảm dần, LIMIT 5
     */
    public List<Object[]> getTopReaders() {
        return reportDAO.getTopReaders(10);
    }
    
    public List<Object[]> getRecentActivity() {
        return reportDAO.getRecentBorrows(20);
    }

    public List<Object[]> getBorrowingList() {
        return reportDAO.getBorrowingList();
    }
    
    public List<Object[]> getOverdueBooks() {
        return reportDAO.getOverdueBooks();
    }
    
    public List<Object[]> getDueSoonBooks() {
        return reportDAO.getDueSoonBooks();
    }
    
    public int getDueSoonCount() {
        return reportDAO.countDueSoonBooks();
    }
    
    public List<Object[]> getUnpaidPenalties() {
        return reportDAO.getUnpaidPenalties();
    }
    
    public double getTotalUnpaidPenalty() {
        return reportDAO.getTotalUnpaidPenalty();
    }
}

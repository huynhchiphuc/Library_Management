/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ReportDAO;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ReportService {
    
    private ReportDAO reportDAO;

    public ReportService() {
        reportDAO = new ReportDAO();
    }
    
    public int getTotalBooks() {
        return reportDAO.countTotalBookCopies();
    }
    
    public int getTotalReaders() {
        return reportDAO.countTotalReaders();
    }
    
    public int getBorrowedBooks() {
        return reportDAO.countBorrowedBooks();
    }
    
    public int getOverdueCount() {
        return reportDAO.countOverdueBorrows();
    }
    
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

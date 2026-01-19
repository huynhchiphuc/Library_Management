/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import service.ReportService;
import view.ReportForm;

/**
 *
 * @author ASUS
 */
public class ReportController {
    
    private ReportForm view;
    private ReportService reportService;

    public ReportController(ReportForm view) {
        this.view = view;
        this.reportService = new ReportService();
        loadDashboardStats();
        loadTables();
        
        // Add refresh event
        view.getBtnRefresh().addActionListener(e -> {
            loadDashboardStats();
            loadTables();
        });
    }
    
    private void loadDashboardStats() {
        view.getLblTotalBooks().setText(String.valueOf(reportService.getTotalBooks()));
        view.getLblTotalReaders().setText(String.valueOf(reportService.getTotalReaders()));
        view.getLblBorrowed().setText(String.valueOf(reportService.getBorrowedBooks()));
        view.getLblOverdue().setText(String.valueOf(reportService.getOverdueCount()));
    }
    
    private void loadTables() {
        // 1. Recent Activity
        DefaultTableModel modelRecent = (DefaultTableModel) view.getTblRecent().getModel();
        modelRecent.setRowCount(0);
        List<Object[]> recentList = reportService.getRecentActivity();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        for (Object[] row : recentList) {
            modelRecent.addRow(new Object[]{
                row[0], // ID
                row[1], // Name
                sdf.format((java.util.Date)row[2]) // Date
            });
        }
        
        // 2. Top Readers
        DefaultTableModel modelTop = (DefaultTableModel) view.getTblTopReaders().getModel();
        modelTop.setRowCount(0);
        List<Object[]> topList = reportService.getTopReaders();
        int rank = 1;
        for (Object[] row : topList) {
            modelTop.addRow(new Object[]{
                rank++,
                row[0], // Name
                row[1]  // Count
            });
        }
    }
}

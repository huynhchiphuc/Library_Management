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
        
        initEvents();
        loadDashboardStats();
        loadTables();
    }
    
    private void initEvents() {
        // Refresh event
        view.getBtnRefresh().addActionListener(e -> refreshData());
        
        // Export events
        view.getBtnExportRecent().addActionListener(e -> exportRecent());
        view.getBtnExportTop().addActionListener(e -> exportTopReaders());
        view.getBtnExportBorrowing().addActionListener(e -> exportBorrowing());
    }
    
    private void refreshData() {
        loadDashboardStats();
        loadTables();
        javax.swing.JOptionPane.showMessageDialog(view,
            "Dữ liệu đã được cập nhật!",
            "Thành công",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void loadDashboardStats() {
        view.getLblTotalBooks().setText(String.valueOf(reportService.getTotalBooks()));
        view.getLblTotalReaders().setText(String.valueOf(reportService.getTotalReaders()));
        view.getLblBorrowed().setText(String.valueOf(reportService.getBorrowedBooks()));
        view.getLblOverdue().setText(String.valueOf(reportService.getOverdueCount()));
    }
    
    private void loadTables() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfFull = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        // 1. Recent Activity
        DefaultTableModel modelRecent = (DefaultTableModel) view.getTblRecent().getModel();
        modelRecent.setRowCount(0);
        List<Object[]> recentList = reportService.getRecentActivity();
        
        for (Object[] row : recentList) {
            String status = "";
            if (row.length > 4 && row[4] != null) {
                int trangThai = (Integer) row[4];
                status = trangThai == 1 ? "Đang mượn" : "Đã trả";
            }
            
            modelRecent.addRow(new Object[]{
                row[0], // ID
                row[1], // Name
                row[2] != null ? sdfFull.format((java.util.Date)row[2]) : "", // NgayMuon
                row[3] != null ? sdf.format((java.util.Date)row[3]) : "", // HanTra
                status
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
        
        // 3. Borrowing List
        if (view.getTblBorrowing() != null) {
            DefaultTableModel modelBorrowing = (DefaultTableModel) view.getTblBorrowing().getModel();
            modelBorrowing.setRowCount(0);
            List<Object[]> borrowingList = reportService.getBorrowingList();
            
            for (Object[] row : borrowingList) {
                modelBorrowing.addRow(new Object[]{
                    row[0], // MaThe
                    row[1], // HoTen
                    row[2], // TuaDe
                    row[3] != null ? sdf.format((java.util.Date)row[3]) : "", // NgayMuon
                    row[4] != null ? sdf.format((java.util.Date)row[4]) : "" // HanTra
                });
            }
        }
    }
    
    private void exportRecent() {
        javax.swing.JOptionPane.showMessageDialog(view,
            "Chức năng xuất Excel sẽ được triển khai sau.\n" +
            "Hiện tại bạn có thể sao chép dữ liệu từ bảng.",
            "Thông báo",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportTopReaders() {
        javax.swing.JOptionPane.showMessageDialog(view,
            "Chức năng xuất Excel sẽ được triển khai sau.\n" +
            "Hiện tại bạn có thể sao chép dữ liệu từ bảng.",
            "Thông báo",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportBorrowing() {
        javax.swing.JOptionPane.showMessageDialog(view,
            "Chức năng xuất Excel sẽ được triển khai sau.\n" +
            "Hiện tại bạn có thể sao chép dữ liệu từ bảng.",
            "Thông báo",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
}

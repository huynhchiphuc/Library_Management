/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Desktop;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import service.ReportService;
import view.HomeForm;

/**
 *
 * @author ASUS
 */
public class HomeController {
    
    private HomeForm view;
    private ReportService reportService;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Timer clockTimer;

    public HomeController(HomeForm view) {
        this.view = view;
        this.reportService = new ReportService();
        
        initEvents();
        loadData();
        startClock();
    }
    
    private void initEvents() {
        view.getBtnRefresh().addActionListener(e -> loadData());
        view.getBtnCallOverdue().addActionListener(e -> contactReader(view.getTblOverdue()));
        view.getBtnRemindDueSoon().addActionListener(e -> contactReader(view.getTblDueSoon()));
    }
    
    private void loadData() {
        loadStatistics();
        loadOverdueBooks();
        loadDueSoonBooks();
        loadUnpaidPenalties();
    }
    
    private void loadStatistics() {
        // Get statistics
        int overdueCount = reportService.getOverdueCount();
        int dueSoonCount = reportService.getDueSoonCount();
        int borrowingCount = reportService.getBorrowedBooks();
        double unpaidTotal = reportService.getTotalUnpaidPenalty();
        
        // Update labels
        view.getLblOverdueCount().setText(String.valueOf(overdueCount));
        view.getLblDueSoonCount().setText(String.valueOf(dueSoonCount));
        view.getLblBorrowingCount().setText(String.valueOf(borrowingCount));
        view.getLblUnpaidPenalty().setText(String.format("%,.0fđ", unpaidTotal));
    }
    
    private void loadOverdueBooks() {
        DefaultTableModel model = (DefaultTableModel) view.getTblOverdue().getModel();
        model.setRowCount(0);
        
        List<Object[]> list = reportService.getOverdueBooks();
        for (Object[] row : list) {
            model.addRow(row);
        }
    }
    
    private void loadDueSoonBooks() {
        DefaultTableModel model = (DefaultTableModel) view.getTblDueSoon().getModel();
        model.setRowCount(0);
        
        List<Object[]> list = reportService.getDueSoonBooks();
        for (Object[] row : list) {
            model.addRow(row);
        }
    }
    
    private void loadUnpaidPenalties() {
        DefaultTableModel model = (DefaultTableModel) view.getTblUnpaidPenalty().getModel();
        model.setRowCount(0);
        
        List<Object[]> list = reportService.getUnpaidPenalties();
        for (Object[] row : list) {
            model.addRow(row);
        }
    }
    
    private void contactReader(javax.swing.JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một độc giả để liên hệ!");
            return;
        }
        
        String maThe = table.getValueAt(selectedRow, 0).toString();
        String tenDocGia = table.getValueAt(selectedRow, 1).toString();
        String sdt = table.getValueAt(selectedRow, 2).toString();
        String email = table.getValueAt(selectedRow, 3).toString();
        String tenSach = table.getValueAt(selectedRow, 4).toString();
        
        String message = String.format(
            "📋 THÔNG TIN LIÊN HỆ\n\n" +
            "Mã thẻ: %s\n" +
            "Tên độc giả: %s\n" +
            "📞 SĐT: %s\n" +
            "📧 Email: %s\n" +
            "📚 Sách: %s\n\n" +
            "Bạn có muốn:\n" +
            "- Gọi điện thoại (Yes)\n" +
            "- Gửi email (No)\n" +
            "- Hủy (Cancel)",
            maThe, tenDocGia, sdt, email, tenSach
        );
        
        int option = JOptionPane.showConfirmDialog(
            view, 
            message, 
            "Liên hệ độc giả", 
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );
        
        if (option == JOptionPane.YES_OPTION) {
            // Call phone
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI("tel:" + sdt));
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "Số điện thoại: " + sdt + "\nVui lòng gọi thủ công.", 
                        "Thông tin", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, 
                    "Không thể mở ứng dụng gọi điện.\nSố điện thoại: " + sdt, 
                    "Lỗi", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } else if (option == JOptionPane.NO_OPTION) {
            // Send email
            try {
                String subject = "[Thư viện] Thông báo về sách mượn";
                String body = String.format(
                    "Kính gửi %s,\n\n" +
                    "Thư viện xin thông báo về cuốn sách \"%s\" mà bạn đang mượn.\n\n" +
                    "Vui lòng liên hệ thư viện hoặc trả sách đúng hạn.\n\n" +
                    "Trân trọng,\nThư viện",
                    tenDocGia, tenSach
                );
                
                String mailtoUri = String.format("mailto:%s?subject=%s&body=%s", 
                    email, 
                    URI.create(subject.replace(" ", "%20")).toString(),
                    URI.create(body.replace(" ", "%20").replace("\n", "%0A")).toString()
                );
                
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI(mailtoUri));
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "Email: " + email + "\nVui lòng gửi email thủ công.", 
                        "Thông tin", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, 
                    "Không thể mở ứng dụng email.\nEmail: " + email, 
                    "Lỗi", 
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void startClock() {
        // Update clock every second
        clockTimer = new Timer(1000, e -> {
            String dateTime = new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
            view.getLblDateTime().setText("📅 " + dateTime);
        });
        clockTimer.start();
    }
    
    public void stopClock() {
        if (clockTimer != null) {
            clockTimer.stop();
        }
    }
}

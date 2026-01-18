package controller;

import service.AuthService;
import view.BookForm;
import view.BorrowForm;
import view.LoginForm;
import view.MainForm;
import view.PenaltyForm;
import view.ReaderForm;
import view.ReportForm;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

/**
 * Điều khiển màn hình chính và chuyển hướng chức năng
 * @author ASUS
 */
public class MainController {
    
    private final MainForm view;
    
    // Giữ tham chiếu đến các form con để tránh mở nhiều lần (Singleton-ish UI)
    private BookForm bookForm;
    private ReaderForm readerForm;
    private BorrowForm borrowForm;
    private PenaltyForm penaltyForm;
    private ReportForm reportForm;

    public MainController(MainForm view) {
        this.view = view;
        initData();
        initController();
    }
    
    private void initData() {
        // Hiển thị thông tin người dùng đang đăng nhập
        
        if (AuthService.isLoggedIn()) {
            view.getLblUserInfo().setText("Xin chào: " + AuthService.getCurrentUser().getFullName() + " | " + AuthService.getCurrentUser().getUsername());
        } else {
            view.getLblUserInfo().setText("Chưa đăng nhập");
            // Trong thực tế, nên bắt buộc đăng nhập trước khi thấy main form
        }
        
        
        // Full screen
        view.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void initController() {
        
        view.getBtnHome().addActionListener(e -> showHome());
        view.getBtnBook().addActionListener(e -> showBookForm());
        view.getBtnReader().addActionListener(e -> showReaderForm());
        view.getBtnBorrow().addActionListener(e -> showBorrowForm());
        view.getBtnPenalty().addActionListener(e -> showPenaltyForm());
        view.getBtnReport().addActionListener(e -> showReportForm());
        view.getBtnLogout().addActionListener(e -> logout());
        
    }
    
    // Sau khi bạn thiết kế giao diện bên NetBeans và Uncomment các hàm Getter trong View, hãy Uncomment đoạn code trên để logic hoạt động.
    
    private void showPanel(javax.swing.JPanel panel) {
        view.getPnlDesktop().removeAll();
        view.getPnlDesktop().setLayout(new java.awt.BorderLayout());
        view.getPnlDesktop().add(panel, java.awt.BorderLayout.CENTER);
        view.getPnlDesktop().revalidate();
        view.getPnlDesktop().repaint();
    }

    private void showHome() {
        // Xóa các panel con để hiện màn hình desktop trống (hoặc add Home panel nếu có)
        view.getPnlDesktop().removeAll();
        view.getPnlDesktop().revalidate();
        view.getPnlDesktop().repaint();
    }
    
    private void showBookForm() {
        if (bookForm == null) {
            bookForm = new BookForm();
        }
        showPanel(bookForm);
    }
    
    private void showReaderForm() {
        if (readerForm == null) {
            readerForm = new ReaderForm();
        }
        showPanel(readerForm);
    }
    
    private void showBorrowForm() {
        if (borrowForm == null) {
            borrowForm = new BorrowForm();
        }
        showPanel(borrowForm);
    }
    
    private void showPenaltyForm() {
        if (penaltyForm == null) {
            penaltyForm = new PenaltyForm();
        }
        showPanel(penaltyForm);
    }
    
    private void showReportForm() {
        if (reportForm == null) {
            reportForm = new ReportForm();
        }
        showPanel(reportForm);
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new AuthService().logout();
            view.dispose();
            new LoginForm().setVisible(true);
        }
    }
}

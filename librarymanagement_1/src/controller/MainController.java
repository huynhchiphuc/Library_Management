package controller;

import service.AuthService;
import view.AuditLogForm;
import view.BookForm;
import view.BorrowForm;
import view.CategoryForm;
import view.ChangePasswordForm;
import view.HomeForm;
import view.LoginForm;
import view.MainForm;
import view.PenaltyForm;
import view.ReaderForm;
import view.ReportForm;
import view.UserForm;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import util.Constants;

/**
 * Điều khiển màn hình chính và chuyển hướng chức năng
 * @author ASUS
 */
public class MainController {
    
    private final MainForm view;
    
    // Giữ tham chiếu đến các form con để tránh mở nhiều lần (Singleton-ish UI)
    private HomeForm homeForm;
    private BookForm bookForm;
    private ReaderForm readerForm;
    private BorrowForm borrowForm;
    private PenaltyForm penaltyForm;
    private ReportForm reportForm;
    private UserForm userForm;
    private CategoryForm categoryForm;
    private AuditLogForm auditLogForm;

    public MainController(MainForm view) {
        this.view = view;
        initData();
        initController();
    }
    
    private void initData() {
        // Hiển thị thông tin người dùng đang đăng nhập
        if (AuthService.isLoggedIn()) {
            String roleName = AuthService.getCurrentUser().getRoleId() == Constants.ROLE_ADMIN ? "Admin" : "Thủ thư";
            view.getLblUserInfo().setText("👤 " + AuthService.getCurrentUser().getFullName() + 
                                         " (" + roleName + ") | 🔑 " + AuthService.getCurrentUser().getUsername());
        } else {
            view.getLblUserInfo().setText("Chưa đăng nhập");
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
        
        // New buttons
        if (view.getBtnUser() != null) {
            view.getBtnUser().addActionListener(e -> showUserForm());
        }
        if (view.getBtnChangePassword() != null) {
            view.getBtnChangePassword().addActionListener(e -> showChangePasswordForm());
        }
        
        view.getBtnCategory().addActionListener(e -> showCategoryForm());
        view.getBtnAuditLog().addActionListener(e -> showAuditLogForm());
    }
    
    private void showPanel(javax.swing.JPanel panel) {
        view.getPnlDesktop().removeAll();
        view.getPnlDesktop().setLayout(new java.awt.BorderLayout());
        view.getPnlDesktop().add(panel, java.awt.BorderLayout.CENTER);
        view.getPnlDesktop().revalidate();
        view.getPnlDesktop().repaint();
    }

    private void showHome() {
        if (homeForm == null) {
            homeForm = new HomeForm();
        }
        showPanel(homeForm);
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
    
    private void showUserForm() {
        // Check if admin
        if (!AuthService.isLoggedIn() || AuthService.getCurrentUser().getRoleId() != Constants.ROLE_ADMIN) {
            JOptionPane.showMessageDialog(view, "Chỉ Admin mới có quyền quản lý người dùng!", "Không có quyền", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (userForm == null) {
            userForm = new UserForm();
        }
        showPanel(userForm);
    }
    
    private void showChangePasswordForm() {
        if (!AuthService.isLoggedIn()) {
            JOptionPane.showMessageDialog(view, "Vui lòng đăng nhập trước!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ChangePasswordForm cpForm = new ChangePasswordForm();
        cpForm.setVisible(true);
    }
    
    private void showCategoryForm() {
        if (categoryForm == null) {
            categoryForm = new CategoryForm();
        }
        showPanel(categoryForm);
    }
    
    private void showAuditLogForm() {
        // Check if admin
        if (!AuthService.isLoggedIn() || AuthService.getCurrentUser().getRoleId() != Constants.ROLE_ADMIN) {
            JOptionPane.showMessageDialog(view, "Chỉ Admin mới có quyền xem nhật ký hoạt động!", "Không có quyền", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            System.out.println("DEBUG: Bắt đầu tạo AuditLogForm...");
            if (auditLogForm == null) {
                auditLogForm = new AuditLogForm();
                System.out.println("DEBUG: AuditLogForm đã được tạo thành công!");
            }
            System.out.println("DEBUG: Gọi showPanel...");
            showPanel(auditLogForm);
            System.out.println("DEBUG: showPanel hoàn tất!");
        } catch (Exception ex) {
            System.err.println("LỖI khi tạo AuditLogForm:");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, 
                "Lỗi khi mở nhật ký: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
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

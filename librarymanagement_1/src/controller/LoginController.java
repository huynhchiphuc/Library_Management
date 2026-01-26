/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import service.AuthService;
import view.LoginForm;
import view.MainForm;

/**
 * Controller xử lý đăng nhập
 * @author ASUS
 */
public class LoginController {
    
    private final LoginForm view;
    private final AuthService authService;
    
    public LoginController(LoginForm view) {
        this.view = view;
        this.authService = new AuthService();
        initController();
    }
    
    private void initController() {
        // Bind events
        view.getBtnLogin().addActionListener(e -> login());
        view.getBtnExit().addActionListener(e -> exitApplication());
        
        // Enter key on username field moves to password
        view.getTxtUsername().addActionListener(e -> view.getTxtPassword().requestFocus());
        
        // Enter key on password field triggers login
        view.getTxtPassword().addActionListener(e -> login());
    }
    
    /**
     * Xử lý đăng nhập
     * Không có tham số (lấy username/password từ form)
     * Xử lý:
     * 1. Lấy username và password từ các trường nhập liệu
     * 2. Validate không được rỗng
     * 3. Gọi AuthService.login(username, password)
     * 4. Nếu thành công:
     *    - Kiểm tra user có bị vô hiệu hóa không
     *    - Ghi log audit đăng nhập
     *    - Mở MainForm và truyền thông tin user
     *    - Đóng LoginForm
     * 5. Nếu thất bại: hiển thị thông báo lỗi
     */
    private void login() {
        String username = view.getTxtUsername().getText().trim();
        String password = new String(view.getTxtPassword().getPassword());
        
        // Validate input
        if (username.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập tên đăng nhập và mật khẩu!", 
                "Thiếu thông tin", 
                JOptionPane.WARNING_MESSAGE);
            view.getTxtUsername().requestFocus();
            return;
        }
        
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập tên đăng nhập!", 
                "Thiếu thông tin", 
                JOptionPane.WARNING_MESSAGE);
            view.getTxtUsername().requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập mật khẩu!", 
                "Thiếu thông tin", 
                JOptionPane.WARNING_MESSAGE);
            view.getTxtPassword().requestFocus();
            return;
        }
        
        // Disable login button during authentication
        view.getBtnLogin().setEnabled(false);
        view.getBtnLogin().setText("Đang xử lý...");
        
        // Attempt login
        SwingUtilities.invokeLater(() -> {
            try {
                if (authService.login(username, password)) {
                    // Success
                    JOptionPane.showMessageDialog(view, 
                        "Đăng nhập thành công!\nChào mừng " + username + "!", 
                        "Thành công", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Close login form and open main form
                    view.dispose();
                    SwingUtilities.invokeLater(() -> {
                        MainForm mainForm = new MainForm();
                        mainForm.setVisible(true);
                    });
                } else {
                    // Failed
                    JOptionPane.showMessageDialog(view, 
                        "Tên đăng nhập hoặc mật khẩu không đúng!\nVui lòng thử lại.", 
                        "Đăng nhập thất bại", 
                        JOptionPane.ERROR_MESSAGE);
                    
                    // Clear password and focus username
                    view.getTxtPassword().setText("");
                    view.getTxtUsername().selectAll();
                    view.getTxtUsername().requestFocus();
                    
                    // Re-enable login button
                    view.getBtnLogin().setEnabled(true);
                    view.getBtnLogin().setText("ĐĂNG NHẬP");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, 
                    "Lỗi kết nối cơ sở dữ liệu!\n" + ex.getMessage(), 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                
                // Re-enable login button
                view.getBtnLogin().setEnabled(true);
                view.getBtnLogin().setText("ĐĂNG NHẬP");
            }
        });
    }
    
    /**
     * Thoát ứng dụng
     * Xử lý: Hiển thị dialog xác nhận, nếu đồng ý thì gọi System.exit(0)
     */
    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(view,
            "Bạn có chắc muốn thoát ứng dụng?",
            "Xác nhận thoát",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}

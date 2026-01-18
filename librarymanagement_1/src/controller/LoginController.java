/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import service.AuthService;
import view.LoginForm;
import view.MainForm;
import javax.swing.JOptionPane;

/**
 *
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
        
        view.getBtnLogin().addActionListener(e -> login());
        view.getBtnExit().addActionListener(e -> System.exit(0));
        
    }
    
    private void login() {
        
        String username = view.getTxtUsername().getText();
        String password = new String(view.getTxtPassword().getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (authService.login(username, password)) {
            JOptionPane.showMessageDialog(view, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
            // Open Main Form
            java.awt.EventQueue.invokeLater(() -> {
                new MainForm().setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(view, "Tên đăng nhập hoặc mật khẩu không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        
        
        // Code Demo tạm thời cho phép đăng nhập luôn
         java.awt.EventQueue.invokeLater(() -> {
            new MainForm().setVisible(true);
        });
        view.dispose();
    }
    
    // Sau khi bạn thiết kế giao diện bên NetBeans và Uncomment các hàm Getter trong View, hãy Uncomment đoạn code trên để logic hoạt động.
}

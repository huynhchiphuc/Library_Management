/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

/**
 *
 * @author ASUS
 */
public class AuthService {
    
    private final UserDAO userDAO;
    private final AuditService auditService;
    private static User currentUser;
    
    public AuthService() {
        this.userDAO = new UserDAO();
        this.auditService = new AuditService();
    }
    
    /**
     * Xác thực đăng nhập người dùng
     * @param username Tên đăng nhập
     * @param password Mật khẩu chưa mã hóa (plain text)
     * @return true nếu đăng nhập thành công, false nếu thất bại
     * Xử lý: 
     * 1. Lấy User từ database theo username
     * 2. So sánh password với password đã hash trong DB (dùng PasswordUtil)
     * 3. Nếu đúng: lưu currentUser và ghi log đăng nhập
     * 4. Trả về true/false
     */
    public boolean login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        
        if (user != null) {
            // Check password (assuming password in DB is hashed)
            // If you want to use plain text for testing, use: if (user.getPassword().equals(password))
            // But we already implemented PasswordUtil, so let's use it properly.
            // Note: If the user in DB currently has plain text password, this might fail unless we update DB.
            // For safety in this prompt, I will assume we should check hash.
            // But for real world new systems, I should probably check if it matches plain text OR hash if I am migrating.
            // Let's stick to hash check.
            
            if (PasswordUtil.checkPassword(password, user.getPassword())) {
                currentUser = user;
                
                // Log successful login
                String description = String.format("Đăng nhập thành công - User: %s", username);
                auditService.logAction(user.getId(), "LOGIN", "NguoiDung", user.getId(), description);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Đăng xuất người dùng hiện tại
     * Xử lý: Ghi log đăng xuất và set currentUser = null
     */
    public void logout() {
        if (currentUser != null) {
            // Log logout
            String description = String.format("Đăng xuất - User: %s", currentUser.getUsername());
            auditService.logAction(currentUser.getId(), "LOGOUT", "NguoiDung", currentUser.getId(), description);
        }
        currentUser = null;
    }
    
    /**
     * Lấy thông tin người dùng đang đăng nhập
     * @return User object của người dùng hiện tại, null nếu chưa đăng nhập
     */
    public static User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Kiểm tra có người dùng đang đăng nhập hay không
     * @return true nếu có user đang đăng nhập, false nếu chưa
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}

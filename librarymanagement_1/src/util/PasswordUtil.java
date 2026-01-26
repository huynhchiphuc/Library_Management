/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author ASUS
 */
public class PasswordUtil {
    
    /**
     * Mã hóa mật khẩu bằng thuật toán SHA-256
     * @param password Mật khẩu dạng plain text
     * @return Chuỗi mật khẩu đã được hash dạng hex (64 ký tự)
     * Xử lý: Sử dụng MessageDigest SHA-256, convert byte[] sang hex string
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * So sánh mật khẩu plain text với mật khẩu đã hash
     * @param password Mật khẩu dạng plain text (người dùng nhập)
     * @param hashedPassword Mật khẩu đã hash trong database
     * @return true nếu khớp, false nếu không khớp
     * Xử lý: Hash password rồi so sánh string với hashedPassword
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}

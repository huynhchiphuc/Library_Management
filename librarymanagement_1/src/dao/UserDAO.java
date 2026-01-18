/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class UserDAO {
    
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ? AND DangHoatDong = 1";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement createStatement = conn.prepareStatement(sql)) {
            
            createStatement.setString(1, username);
            
            try (ResultSet rs = createStatement.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("MaNguoiDung"));
                    user.setUsername(rs.getString("TenDangNhap"));
                    user.setPassword(rs.getString("MatKhau"));
                    user.setFullName(rs.getString("HoTen"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhoneNumber(rs.getString("SoDienThoai"));
                    user.setRoleId(rs.getInt("MaVaiTro"));
                    user.setActive(rs.getBoolean("DangHoatDong"));
                    user.setCreatedAt(rs.getTimestamp("NgayTao"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

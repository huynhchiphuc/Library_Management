/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;
import util.DBConnection;

/**
 *
 * @author ASUS
 */
public class UserDAO {
    
    /**
     * Lấy thông tin người dùng theo tên đăng nhập
     * @param username Tên đăng nhập cần tìm
     * @return User object nếu tìm thấy và đang hoạt động, null nếu không tìm thấy
     * Xử lý: Truy vấn database bảng NguoiDung với điều kiện TenDangNhap = ? AND DangHoatDong = 1
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ? AND DangHoatDong = 1";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement createStatement = conn.prepareStatement(sql)) {
            
            createStatement.setString(1, username);
            
            try (ResultSet rs = createStatement.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Lấy danh sách tất cả người dùng trong hệ thống
     * @return List<User> danh sách người dùng kèm thông tin vai trò (JOIN với bảng VaiTro)
     * Xử lý: Truy vấn SELECT u.*, v.TenVaiTro FROM NguoiDung u JOIN VaiTro v, sắp xếp theo NgayTao giảm dần
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.*, v.TenVaiTro FROM NguoiDung u " +
                     "JOIN VaiTro v ON u.MaVaiTro = v.MaVaiTro " +
                     "ORDER BY u.NgayTao DESC";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    /**
     * Tìm kiếm người dùng theo từ khóa
     * @param keyword Từ khóa tìm kiếm (tên đăng nhập, họ tên hoặc email)
     * @return List<User> danh sách người dùng khớp với từ khóa
     * Xử lý: Truy vấn với LIKE %keyword% trên 3 trường: TenDangNhap, HoTen, Email
     */
    public List<User> searchUser(String keyword) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.*, v.TenVaiTro FROM NguoiDung u " +
                     "JOIN VaiTro v ON u.MaVaiTro = v.MaVaiTro " +
                     "WHERE u.TenDangNhap LIKE ? OR u.HoTen LIKE ? OR u.Email LIKE ? " +
                     "ORDER BY u.NgayTao DESC";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    /**
     * Thêm người dùng mới vào hệ thống
     * @param user Đối tượng User chứa thông tin cần thêm (username, password đã hash, fullName, email, phone, roleId, isActive)
     * @return true nếu thêm thành công, false nếu thất bại
     * Xử lý: INSERT INTO NguoiDung các trường thông tin, NgayTao tự động = CURRENT_TIMESTAMP
     */
    public boolean insertUser(User user) {
        String sql = "INSERT INTO NguoiDung (TenDangNhap, MatKhau, HoTen, Email, SoDienThoai, MaVaiTro, DangHoatDong) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhoneNumber());
            ps.setInt(6, user.getRoleId());
            ps.setBoolean(7, user.isActive());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Cập nhật thông tin người dùng (không bao gồm mật khẩu)
     * @param user Đối tượng User chứa thông tin cần cập nhật (id bắt buộc)
     * @return true nếu cập nhật thành công, false nếu thất bại
     * Xử lý: UPDATE NguoiDung SET HoTen, Email, SoDienThoai, MaVaiTro, DangHoatDong WHERE MaNguoiDung = ?
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE NguoiDung SET HoTen = ?, Email = ?, SoDienThoai = ?, " +
                     "MaVaiTro = ?, DangHoatDong = ? WHERE MaNguoiDung = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setInt(4, user.getRoleId());
            ps.setBoolean(5, user.isActive());
            ps.setInt(6, user.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Xóa người dùng (xóa mềm: set DangHoatDong = 0)
     * @param userId Mã người dùng cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     * Xử lý: UPDATE NguoiDung SET DangHoatDong = 0 WHERE MaNguoiDung = ?
     */
    public boolean deleteUser(int userId) {
        String sql = "UPDATE NguoiDung SET DangHoatDong = 0 WHERE MaNguoiDung = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updatePassword(String username, String hashedPassword) {
        String sql = "UPDATE NguoiDung SET MatKhau = ? WHERE TenDangNhap = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, hashedPassword);
            ps.setString(2, username);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Reset mật khẩu người dùng
     * @param userId Mã người dùng
     * @param newHashedPassword Mật khẩu mới đã được hash (SHA-256)
     * @return true nếu reset thành công, false nếu thất bại
     * Xử lý: UPDATE NguoiDung SET MatKhau = ? WHERE MaNguoiDung = ?
     */
    public boolean resetPassword(int userId, String newHashedPassword) {
        String sql = "UPDATE NguoiDung SET MatKhau = ? WHERE MaNguoiDung = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, newHashedPassword);
            ps.setInt(2, userId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isUsernameExist(String username) {
        String sql = "SELECT COUNT(*) FROM NguoiDung WHERE TenDangNhap = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private User mapUser(ResultSet rs) throws SQLException {
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

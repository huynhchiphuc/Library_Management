/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.AuditLog;
import util.DBConnection;

/**
 * DAO cho bảng NhatKyHoatDong
 * @author ASUS
 */
public class AuditLogDAO {
    
    /**
     * Ghi log hành động
     */
    public boolean insertLog(int maNguoiDung, String hanhDong, String tenBang, int maBanGhi, String moTaChiTiet) {
        String sql = "INSERT INTO NhatKyHoatDong (MaNguoiDung, HanhDong, TenBang, MaBanGhi, MoTaChiTiet, DiaChiIP) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, maNguoiDung);
            ps.setString(2, hanhDong);
            ps.setString(3, tenBang);
            ps.setInt(4, maBanGhi);
            ps.setString(5, moTaChiTiet);
            ps.setString(6, getClientIP());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Lấy tất cả audit logs
     */
    public List<AuditLog> getAllLogs() {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT a.*, u.HoTen as TenNguoiDung FROM NhatKyHoatDong a " +
                     "LEFT JOIN NguoiDung u ON a.MaNguoiDung = u.MaNguoiDung " +
                     "ORDER BY a.ThoiGian DESC LIMIT 1000";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                AuditLog log = new AuditLog();
                log.setMaNhatKy(rs.getInt("MaNhatKy"));
                log.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                log.setHanhDong(rs.getString("HanhDong"));
                log.setTenBang(rs.getString("TenBang"));
                log.setMaBanGhi(rs.getInt("MaBanGhi"));
                log.setMoTaChiTiet(rs.getString("MoTaChiTiet"));
                log.setDiaChiIP(rs.getString("DiaChiIP"));
                log.setThoiGian(rs.getTimestamp("ThoiGian"));
                log.setTenNguoiDung(rs.getString("TenNguoiDung"));
                logs.add(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }
    
    /**
     * Lấy logs theo người dùng
     */
    public List<AuditLog> getLogsByUser(int maNguoiDung) {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT a.*, u.HoTen as TenNguoiDung FROM NhatKyHoatDong a " +
                     "LEFT JOIN NguoiDung u ON a.MaNguoiDung = u.MaNguoiDung " +
                     "WHERE a.MaNguoiDung = ? " +
                     "ORDER BY a.ThoiGian DESC LIMIT 100";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, maNguoiDung);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AuditLog log = new AuditLog();
                    log.setMaNhatKy(rs.getInt("MaNhatKy"));
                    log.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                    log.setHanhDong(rs.getString("HanhDong"));
                    log.setTenBang(rs.getString("TenBang"));
                    log.setMaBanGhi(rs.getInt("MaBanGhi"));
                    log.setMoTaChiTiet(rs.getString("MoTaChiTiet"));
                    log.setDiaChiIP(rs.getString("DiaChiIP"));
                    log.setThoiGian(rs.getTimestamp("ThoiGian"));
                    log.setTenNguoiDung(rs.getString("TenNguoiDung"));
                    logs.add(log);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }
    
    /**
     * Get client IP (simple implementation)
     */
    private String getClientIP() {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "unknown";
        }
    }
}

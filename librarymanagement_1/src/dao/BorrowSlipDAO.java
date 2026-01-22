/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import model.BorrowSlip;
import util.DBConnection;

/**
 *
 * @author ASUS
 */
public class BorrowSlipDAO {

    public int insert(BorrowSlip slip) {
        String sql = "INSERT INTO PhieuMuon (MaDocGia, MaNguoiDung, NgayMuon, HanTra, GhiChu, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, slip.getMaDocGia());
            if (slip.getMaNguoiDung() == 0) ps.setNull(2, java.sql.Types.INTEGER); // Or handle user later
            else ps.setInt(2, slip.getMaNguoiDung());
            
            ps.setTimestamp(3, new java.sql.Timestamp(slip.getNgayMuon().getTime()));
            ps.setDate(4, new java.sql.Date(slip.getHanTra().getTime()));
            ps.setString(5, slip.getGhiChu());
            ps.setInt(6, slip.getTrangThai());
            
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public BorrowSlip getBorrowSlip(int maPhieuMuon) {
        String sql = "SELECT * FROM PhieuMuon WHERE MaPhieuMuon = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, maPhieuMuon);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BorrowSlip slip = new BorrowSlip();
                    slip.setMaPhieuMuon(rs.getInt("MaPhieuMuon"));
                    slip.setMaDocGia(rs.getInt("MaDocGia"));
                    slip.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                    slip.setNgayMuon(rs.getDate("NgayMuon"));
                    slip.setHanTra(rs.getDate("HanTra"));
                    slip.setGhiChu(rs.getString("GhiChu"));
                    slip.setTrangThai(rs.getInt("TrangThai"));
                    return slip;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

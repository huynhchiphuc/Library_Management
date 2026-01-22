/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.BorrowDetail;
import util.DBConnection;

/**
 *
 * @author ASUS
 */
public class BorrowDetailDAO {

    public boolean insert(BorrowDetail detail) {
        String sql = "INSERT INTO ChiTietMuonTra (MaPhieuMuon, MaCuonSach, TinhTrangTra, GhiChu) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, detail.getMaPhieuMuon());
            ps.setInt(2, detail.getMaCuonSach());
            ps.setString(3, detail.getTinhTrangTra()); // Likely "Dang muon" or similar initial status
            ps.setString(4, detail.getGhiChu());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateReturn(int maCuonSach, java.util.Date ngayTra, String tinhTrang) {
        // Only update the active loan (NgayTra IS NULL)
        String sql = "UPDATE ChiTietMuonTra SET NgayTra = ?, TinhTrangTra = ? " +
                     "WHERE MaCuonSach = ? AND NgayTra IS NULL";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setTimestamp(1, new java.sql.Timestamp(ngayTra.getTime()));
            ps.setString(2, tinhTrang);
            ps.setInt(3, maCuonSach);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isBookBorrowed(int maCuonSach) {
        String sql = "SELECT COUNT(*) FROM ChiTietMuonTra WHERE MaCuonSach = ? AND NgayTra IS NULL";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maCuonSach);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public BorrowDetail getBorrowDetailByCopy(int maCuonSach) {
        String sql = "SELECT * FROM ChiTietMuonTra WHERE MaCuonSach = ? AND NgayTra IS NULL ORDER BY MaChiTiet DESC LIMIT 1";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, maCuonSach);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BorrowDetail detail = new BorrowDetail();
                    detail.setMaChiTiet(rs.getInt("MaChiTiet"));
                    detail.setMaPhieuMuon(rs.getInt("MaPhieuMuon"));
                    detail.setMaCuonSach(rs.getInt("MaCuonSach"));
                    detail.setNgayTra(rs.getDate("NgayTra"));
                    detail.setTinhTrangTra(rs.getString("TinhTrangTra"));
                    detail.setGhiChu(rs.getString("GhiChu"));
                    return detail;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

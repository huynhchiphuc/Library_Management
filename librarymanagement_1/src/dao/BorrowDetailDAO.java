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
}

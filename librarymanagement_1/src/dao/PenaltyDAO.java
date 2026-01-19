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
import model.Penalty;
import util.DBConnection;

/**
 *
 * @author ASUS
 */
public class PenaltyDAO {

    public List<Penalty> getAllPenalties() {
        List<Penalty> list = new ArrayList<>();
        String sql = "SELECT p.*, d.HoTen FROM PhieuPhat p " +
                     "JOIN DocGia d ON p.MaDocGia = d.MaDocGia " +
                     "ORDER BY p.NgayTao DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Penalty p = new Penalty();
                p.setMaPhieuPhat(rs.getInt("MaPhieuPhat"));
                p.setMaChiTiet(rs.getInt("MaChiTiet"));
                p.setMaDocGia(rs.getInt("MaDocGia"));
                p.setLyDo(rs.getString("LyDo"));
                p.setSoTien(rs.getDouble("SoTien"));
                p.setDaDongTien(rs.getBoolean("DaDongTien"));
                p.setNgayTao(rs.getTimestamp("NgayTao"));
                p.setTenDocGia(rs.getString("HoTen"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean insert(Penalty p) {
        String sql = "INSERT INTO PhieuPhat (MaChiTiet, MaDocGia, LyDo, SoTien, DaDongTien, NgayTao) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (p.getMaChiTiet() == 0) ps.setNull(1, java.sql.Types.INTEGER);
            else ps.setInt(1, p.getMaChiTiet());
            
            ps.setInt(2, p.getMaDocGia());
            ps.setString(3, p.getLyDo());
            ps.setDouble(4, p.getSoTien());
            ps.setBoolean(5, p.isDaDongTien());
            ps.setTimestamp(6, new java.sql.Timestamp(p.getNgayTao().getTime()));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateStatus(int id, boolean status) {
        String sql = "UPDATE PhieuPhat SET DaDongTien = ? WHERE MaPhieuPhat = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

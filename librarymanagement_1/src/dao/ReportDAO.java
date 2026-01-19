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
import util.DBConnection;

/**
 * Data Access Object for Reporting and Statistics
 * 
 * @author ASUS
 */
public class ReportDAO {

    public int countTotalBookTitles() {
        return executeCount("SELECT COUNT(*) FROM DauSach");
    }

    public int countTotalBookCopies() {
        return executeCount("SELECT COUNT(*) FROM CuonSach");
    }

    public int countTotalReaders() {
        return executeCount("SELECT COUNT(*) FROM DocGia");
    }

    public int countBorrowedBooks() {
        return executeCount("SELECT COUNT(*) FROM CuonSach WHERE TrangThai = 2");
    }

    public int countOverdueBorrows() {
        String sql = "SELECT COUNT(*) FROM PhieuMuon WHERE HanTra < CURRENT_DATE AND TrangThai = 0";
        return executeCount(sql);
    }
    
    private int executeCount(String sql) {
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Example: Get Top 5 Readers
    // Returns List of Object[] { ReaderName, BorrowsCount }
    public List<Object[]> getTopReaders(int limit) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT d.HoTen, COUNT(p.MaPhieuMuon) as SoLuot " +
                     "FROM PhieuMuon p " +
                     "JOIN DocGia d ON p.MaDocGia = d.MaDocGia " +
                     "GROUP BY d.MaDocGia, d.HoTen " +
                     "ORDER BY SoLuot DESC " +
                     "LIMIT ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    list.add(new Object[]{rs.getString("HoTen"), rs.getInt("SoLuot")});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Example: Recent Borrows
    public List<Object[]> getRecentBorrows(int limit) {
         List<Object[]> list = new ArrayList<>();
        String sql = "SELECT p.MaPhieuMuon, d.HoTen, p.NgayMuon " +
                     "FROM PhieuMuon p " +
                     "JOIN DocGia d ON p.MaDocGia = d.MaDocGia " +
                     "ORDER BY p.NgayMuon DESC " +
                     "LIMIT ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    list.add(new Object[]{
                        rs.getInt("MaPhieuMuon"), 
                        rs.getString("HoTen"), 
                        rs.getTimestamp("NgayMuon")
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

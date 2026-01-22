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
        String sql = "SELECT p.MaPhieuMuon, d.HoTen, p.NgayMuon, p.HanTra, p.TrangThai " +
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
                        rs.getTimestamp("NgayMuon"),
                        rs.getDate("HanTra"),
                        rs.getInt("TrangThai")
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getBorrowingList() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT dg.MaThe, dg.HoTen, ds.TuaDe, pm.NgayMuon, pm.HanTra " +
                     "FROM PhieuMuon pm " +
                     "JOIN DocGia dg ON pm.MaDocGia = dg.MaDocGia " +
                     "JOIN ChiTietMuonTra ct ON pm.MaPhieuMuon = ct.MaPhieuMuon " +
                     "JOIN CuonSach cs ON ct.MaCuonSach = cs.MaCuonSach " +
                     "JOIN DauSach ds ON cs.MaDauSach = ds.MaDauSach " +
                     "WHERE ct.NgayTra IS NULL " +
                     "ORDER BY pm.NgayMuon DESC";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("MaThe"),
                    rs.getString("HoTen"),
                    rs.getString("TuaDe"),
                    rs.getTimestamp("NgayMuon"),
                    rs.getDate("HanTra")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Lấy danh sách sách quá hạn với thông tin độc giả
     * @return List of [MaThe, HoTen, SDT, Email, TenSach, HanTra, QuaHanNgay]
     */
    public List<Object[]> getOverdueBooks() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT dg.MaThe, dg.HoTen, dg.SoDienThoai, dg.Email, ds.TuaDe, " +
                     "pm.HanTra, DATEDIFF(CURRENT_DATE, pm.HanTra) as QuaHanNgay " +
                     "FROM PhieuMuon pm " +
                     "JOIN DocGia dg ON pm.MaDocGia = dg.MaDocGia " +
                     "JOIN ChiTietMuonTra ct ON pm.MaPhieuMuon = ct.MaPhieuMuon " +
                     "JOIN CuonSach cs ON ct.MaCuonSach = cs.MaCuonSach " +
                     "JOIN DauSach ds ON cs.MaDauSach = ds.MaDauSach " +
                     "WHERE ct.NgayTra IS NULL AND pm.HanTra < CURRENT_DATE " +
                     "ORDER BY QuaHanNgay DESC";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("MaThe"),
                    rs.getString("HoTen"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("TuaDe"),
                    rs.getDate("HanTra"),
                    rs.getInt("QuaHanNgay")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Lấy danh sách sách sắp hết hạn (trong 3 ngày)
     * @return List of [MaThe, HoTen, SDT, Email, TenSach, HanTra, ConNgay]
     */
    public List<Object[]> getDueSoonBooks() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT dg.MaThe, dg.HoTen, dg.SoDienThoai, dg.Email, ds.TuaDe, " +
                     "pm.HanTra, DATEDIFF(pm.HanTra, CURRENT_DATE) as ConNgay " +
                     "FROM PhieuMuon pm " +
                     "JOIN DocGia dg ON pm.MaDocGia = dg.MaDocGia " +
                     "JOIN ChiTietMuonTra ct ON pm.MaPhieuMuon = ct.MaPhieuMuon " +
                     "JOIN CuonSach cs ON ct.MaCuonSach = cs.MaCuonSach " +
                     "JOIN DauSach ds ON cs.MaDauSach = ds.MaDauSach " +
                     "WHERE ct.NgayTra IS NULL " +
                     "AND pm.HanTra >= CURRENT_DATE " +
                     "AND pm.HanTra <= DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY) " +
                     "ORDER BY ConNgay ASC";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("MaThe"),
                    rs.getString("HoTen"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("TuaDe"),
                    rs.getDate("HanTra"),
                    rs.getInt("ConNgay")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Đếm số sách sắp hết hạn (trong 3 ngày)
     */
    public int countDueSoonBooks() {
        String sql = "SELECT COUNT(*) FROM PhieuMuon pm " +
                     "JOIN ChiTietMuonTra ct ON pm.MaPhieuMuon = ct.MaPhieuMuon " +
                     "WHERE ct.NgayTra IS NULL " +
                     "AND pm.HanTra >= CURRENT_DATE " +
                     "AND pm.HanTra <= DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY)";
        return executeCount(sql);
    }
    
    /**
     * Lấy danh sách phạt chưa đóng
     * @return List of [MaThe, HoTen, SDT, LyDo, SoTien, NgayTao]
     */
    public List<Object[]> getUnpaidPenalties() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT dg.MaThe, dg.HoTen, dg.SoDienThoai, p.LyDo, p.SoTien, p.NgayTao " +
                     "FROM PhieuPhat p " +
                     "JOIN DocGia dg ON p.MaDocGia = dg.MaDocGia " +
                     "WHERE p.DaDongTien = 0 " +
                     "ORDER BY p.NgayTao DESC";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("MaThe"),
                    rs.getString("HoTen"),
                    rs.getString("SoDienThoai"),
                    rs.getString("LyDo"),
                    rs.getDouble("SoTien"),
                    rs.getTimestamp("NgayTao")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Tổng số tiền phạt chưa đóng
     */
    public double getTotalUnpaidPenalty() {
        String sql = "SELECT COALESCE(SUM(SoTien), 0) FROM PhieuPhat WHERE DaDongTien = 0";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}

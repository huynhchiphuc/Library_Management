/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.BookCopy;
import util.DBConnection;

/**
 *
 * @author ASUS
 */
public class BookCopyDAO {

    public BookCopy getBookCopyByBarcode(String barcode) {
        String sql = "SELECT cs.*, ds.TuaDe, ds.TacGia, ds.NhaXuatBan " +
                     "FROM CuonSach cs " +
                     "JOIN DauSach ds ON cs.MaDauSach = ds.MaDauSach " +
                     "WHERE cs.MaVach = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barcode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BookCopy bc = new BookCopy();
                    bc.setMaCuonSach(rs.getInt("MaCuonSach"));
                    bc.setMaDauSach(rs.getInt("MaDauSach"));
                    bc.setMaVach(rs.getString("MaVach"));
                    bc.setTrangThai(rs.getInt("TrangThai"));
                    bc.setTinhTrang(rs.getString("TinhTrang"));
                    bc.setViTriKe(rs.getString("ViTriKe"));
                    bc.setGiaTien(rs.getDouble("GiaTien"));
                    bc.setNgayNhap(rs.getDate("NgayNhap"));
                    
                    bc.setTuaDe(rs.getString("TuaDe"));
                    bc.setTacGia(rs.getString("TacGia"));
                    bc.setNhaXuatBan(rs.getString("NhaXuatBan"));
                    return bc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Not found
    }
    
    public boolean updateStatus(int maCuonSach, int newStatus) {
        String sql = "UPDATE CuonSach SET TrangThai = ? WHERE MaCuonSach = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStatus);
            ps.setInt(2, maCuonSach);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

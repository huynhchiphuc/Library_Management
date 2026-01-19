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
import model.Reader;
import util.DBConnection;

/**
 *
 * @author ASUS
 */
public class ReaderDAO {
    
    public List<Reader> getAllReaders() {
        List<Reader> list = new ArrayList<>();
        String sql = "SELECT * FROM DocGia";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Reader r = new Reader();
                r.setMaDocGia(rs.getInt("MaDocGia"));
                r.setMaThe(rs.getString("MaThe"));
                r.setHoTen(rs.getString("HoTen"));
                r.setEmail(rs.getString("Email"));
                r.setSoDienThoai(rs.getString("SoDienThoai"));
                r.setDiaChi(rs.getString("DiaChi"));
                r.setGioHanMuon(rs.getInt("GioiHanMuon"));
                r.setNgayHetHan(rs.getDate("NgayHetHan"));
                r.setDiemViPham(rs.getInt("DiemViPham"));
                r.setBiKhoa(rs.getBoolean("BiKhoa"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean addReader(Reader r) {
        String sql = "INSERT INTO DocGia(MaThe, HoTen, Email, SoDienThoai, DiaChi, NgayHetHan, BiKhoa) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getMaThe());
            ps.setString(2, r.getHoTen());
            ps.setString(3, r.getEmail());
            ps.setString(4, r.getSoDienThoai());
            ps.setString(5, r.getDiaChi());
            ps.setDate(6, new java.sql.Date(r.getNgayHetHan().getTime()));
            ps.setBoolean(7, r.isBiKhoa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateReader(Reader r) {
        String sql = "UPDATE DocGia SET HoTen=?, Email=?, SoDienThoai=?, DiaChi=?, NgayHetHan=?, BiKhoa=? WHERE MaThe=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getHoTen());
            ps.setString(2, r.getEmail());
            ps.setString(3, r.getSoDienThoai());
            ps.setString(4, r.getDiaChi());
            ps.setDate(5, new java.sql.Date(r.getNgayHetHan().getTime()));
            ps.setBoolean(6, r.isBiKhoa());
            ps.setString(7, r.getMaThe());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteReader(String maThe) {
        String sql = "DELETE FROM DocGia WHERE MaThe=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maThe);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Reader> searchReader(String keyword) {
        List<Reader> list = new ArrayList<>();
        String sql = "SELECT * FROM DocGia WHERE MaThe LIKE ? OR HoTen LIKE ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reader r = new Reader();
                    r.setMaDocGia(rs.getInt("MaDocGia"));
                    r.setMaThe(rs.getString("MaThe"));
                    r.setHoTen(rs.getString("HoTen"));
                    r.setEmail(rs.getString("Email"));
                    r.setSoDienThoai(rs.getString("SoDienThoai"));
                    r.setDiaChi(rs.getString("DiaChi"));
                    r.setGioHanMuon(rs.getInt("GioiHanMuon"));
                    r.setNgayHetHan(rs.getDate("NgayHetHan"));
                    r.setDiemViPham(rs.getInt("DiemViPham"));
                    r.setBiKhoa(rs.getBoolean("BiKhoa"));
                    list.add(r);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean isMaTheExist(String maThe) {
        String sql = "SELECT count(*) FROM DocGia WHERE MaThe = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maThe);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Reader getReaderByCardId(String maThe) {
        String sql = "SELECT * FROM DocGia WHERE MaThe = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maThe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Reader r = new Reader();
                    r.setMaDocGia(rs.getInt("MaDocGia"));
                    r.setMaThe(rs.getString("MaThe"));
                    r.setHoTen(rs.getString("HoTen"));
                    r.setEmail(rs.getString("Email"));
                    r.setSoDienThoai(rs.getString("SoDienThoai"));
                    r.setDiaChi(rs.getString("DiaChi"));
                    r.setGioHanMuon(rs.getInt("GioiHanMuon"));
                    r.setNgayHetHan(rs.getDate("NgayHetHan"));
                    r.setDiemViPham(rs.getInt("DiemViPham"));
                    r.setBiKhoa(rs.getBoolean("BiKhoa"));
                    return r;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

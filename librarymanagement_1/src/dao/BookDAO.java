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

import model.Book;
import util.DBConnection;

public class BookDAO {
    
    /**
     * Lấy danh sách tất cả đầu sách
     * @return List<Book> danh sách đầu sách kèm thông tin thể loại và số lượng cuốn sách
     * Xử lý: JOIN DauSach với TheLoai, đếm số lượng CuonSach có TrangThai = 1 (có sẵn)
     */
    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        // Join with TheLoai and CuonSach to get aggregated data
        String sql = "SELECT b.*, c.TenTheLoai, COUNT(cs.MaCuonSach) as SoLuong, MAX(cs.GiaTien) as GiaTien " +
                     "FROM DauSach b " +
                     "LEFT JOIN TheLoai c ON b.MaTheLoai = c.MaTheLoai " +
                     "LEFT JOIN CuonSach cs ON b.MaDauSach = cs.MaDauSach " +
                     "GROUP BY b.MaDauSach, b.TuaDe, b.TacGia, b.NhaXuatBan, b.NamXuatBan, b.MaTheLoai, b.HinhAnh, b.MoTa, c.TenTheLoai";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("MaDauSach"));
                b.setTitle(rs.getString("TuaDe"));
                b.setAuthor(rs.getString("TacGia"));
                b.setPublisher(rs.getString("NhaXuatBan"));
                b.setPublishYear(rs.getInt("NamXuatBan"));
                b.setCategoryId(rs.getInt("MaTheLoai"));
                b.setImage(rs.getString("HinhAnh"));
                b.setDescription(rs.getString("MoTa"));
                b.setCategoryName(rs.getString("TenTheLoai"));
                b.setQuantity(rs.getInt("SoLuong"));
                b.setPrice(rs.getDouble("GiaTien"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Thêm đầu sách mới vào database
     * @param b Đối tượng Book chứa thông tin đầu sách
     * @return ID của đầu sách vừa thêm, -1 nếu thất bại
     * Xử lý: INSERT INTO DauSach, sử dụng RETURN_GENERATED_KEYS để lấy ID tự động tăng
     */
    public int insertBook(Book b) {
        String sql = "INSERT INTO DauSach(TuaDe, TacGia, NhaXuatBan, NamXuatBan, MaTheLoai, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getPublisher());
            ps.setInt(4, b.getPublishYear());
            ps.setInt(5, b.getCategoryId());
            ps.setString(6, b.getDescription());
            
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
    
    public boolean insertCopies(int bookId, int quantity, double price) {
        String sql = "INSERT INTO CuonSach(MaDauSach, MaVach, TrangThai, TinhTrang, GiaTien) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < quantity; i++) {
                ps.setInt(1, bookId);
                // Generate unique Barcode: BOOK-{ID}-{Timestamp}-{Index}
                String barcode = "B" + bookId + "-" + System.currentTimeMillis() + i;
                if (barcode.length() > 20) barcode = barcode.substring(0, 20); // truncate if too long
                
                ps.setString(2, barcode);
                ps.setInt(3, 1); // 1 = Available
                ps.setString(4, "Mới");
                ps.setDouble(5, price);
                ps.addBatch();
            }
            
            int[] results = ps.executeBatch();
            return results.length > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int findBookId(String title, String author, int year) {
        String sql = "SELECT MaDauSach FROM DauSach WHERE TuaDe = ? AND TacGia = ? AND NamXuatBan = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateBook(Book b) {
        String sql = "UPDATE DauSach SET TuaDe=?, TacGia=?, NhaXuatBan=?, NamXuatBan=?, MaTheLoai=?, MoTa=? WHERE MaDauSach=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getPublisher());
            ps.setInt(4, b.getPublishYear());
            ps.setInt(5, b.getCategoryId());
            ps.setString(6, b.getDescription());
            ps.setInt(7, b.getId());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int id) {
        String sql = "DELETE FROM DauSach WHERE MaDauSach=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Tìm kiếm sách theo từ khóa
     * @param keyword Từ khóa tìm kiếm
     * @return List<Book> danh sách sách khớp với từ khóa
     * Xử lý: Tìm kiếm LIKE %keyword% trên các trường: TuaDe, TacGia, NhaXuatBan, TenTheLoai
     */
    public List<Book> searchBooks(String keyword) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT b.*, c.TenTheLoai, COUNT(cs.MaCuonSach) as SoLuong, MAX(cs.GiaTien) as GiaTien " +
                     "FROM DauSach b " +
                     "LEFT JOIN TheLoai c ON b.MaTheLoai = c.MaTheLoai " +
                     "LEFT JOIN CuonSach cs ON b.MaDauSach = cs.MaDauSach " +
                     "WHERE b.TuaDe LIKE ? OR b.TacGia LIKE ? OR b.NhaXuatBan LIKE ? OR cs.MaVach LIKE ? " +
                     "GROUP BY b.MaDauSach, b.TuaDe, b.TacGia, b.NhaXuatBan, b.NamXuatBan, b.MaTheLoai, b.HinhAnh, b.MoTa, c.TenTheLoai";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book b = new Book();
                    b.setId(rs.getInt("MaDauSach"));
                    b.setTitle(rs.getString("TuaDe"));
                    b.setAuthor(rs.getString("TacGia"));
                    b.setPublisher(rs.getString("NhaXuatBan"));
                    b.setPublishYear(rs.getInt("NamXuatBan"));
                    b.setCategoryId(rs.getInt("MaTheLoai"));
                    b.setDescription(rs.getString("MoTa"));
                    b.setCategoryName(rs.getString("TenTheLoai"));
                    b.setQuantity(rs.getInt("SoLuong"));
                    b.setPrice(rs.getDouble("GiaTien"));
                    list.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<String> getBarcodes(int bookId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT MaVach FROM CuonSach WHERE MaDauSach = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("MaVach"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Book> getBooksByCategory(int categoryId) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT d.*, t.TenTheLoai, " +
                     "(SELECT COUNT(*) FROM CuonSach c WHERE c.MaDauSach = d.MaDauSach) as SoLuong, " +
                     "(SELECT AVG(c.GiaTien) FROM CuonSach c WHERE c.MaDauSach = d.MaDauSach) as GiaTien " +
                     "FROM DauSach d " +
                     "LEFT JOIN TheLoai t ON d.MaTheLoai = t.MaTheLoai " +
                     "WHERE d.MaTheLoai = ? " +
                     "ORDER BY d.MaDauSach DESC";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapBook(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Book getBookByBarcode(String barcode) {
        String sql = "SELECT d.*, t.TenTheLoai, c.GiaTien, " +
                     "(SELECT COUNT(*) FROM CuonSach cs WHERE cs.MaDauSach = d.MaDauSach) as SoLuong " +
                     "FROM CuonSach c " +
                     "JOIN DauSach d ON c.MaDauSach = d.MaDauSach " +
                     "LEFT JOIN TheLoai t ON d.MaTheLoai = t.MaTheLoai " +
                     "WHERE c.MaVach = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barcode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapBook(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private Book mapBook(ResultSet rs) throws Exception {
        Book b = new Book();
        b.setId(rs.getInt("MaDauSach"));
        b.setTitle(rs.getString("TuaDe"));
        b.setAuthor(rs.getString("TacGia"));
        b.setPublisher(rs.getString("NhaXuatBan"));
        b.setPublishYear(rs.getInt("NamXuatBan"));
        b.setCategoryId(rs.getInt("MaTheLoai"));
        b.setImage(rs.getString("HinhAnh"));
        b.setDescription(rs.getString("MoTa"));
        b.setCategoryName(rs.getString("TenTheLoai"));
        b.setQuantity(rs.getInt("SoLuong"));
        b.setPrice(rs.getDouble("GiaTien"));
        return b;
    }
}


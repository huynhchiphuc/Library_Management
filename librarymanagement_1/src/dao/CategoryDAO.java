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
import model.Category;
import util.DBConnection;

public class CategoryDAO {
    
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM TheLoai";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("MaTheLoai"));
                c.setName(rs.getString("TenTheLoai"));
                c.setDescription(rs.getString("MoTa"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

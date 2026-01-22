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

import model.Role;
import util.DBConnection;

/**
 * DAO cho bảng VaiTro
 * @author ASUS
 */
public class RoleDAO {
    
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM VaiTro ORDER BY MaVaiTro";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Role role = new Role();
                role.setMaVaiTro(rs.getInt("MaVaiTro"));
                role.setTenVaiTro(rs.getString("TenVaiTro"));
                role.setMoTa(rs.getString("MoTa"));
                roles.add(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
    
    public Role getRoleById(int id) {
        String sql = "SELECT * FROM VaiTro WHERE MaVaiTro = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Role role = new Role();
                    role.setMaVaiTro(rs.getInt("MaVaiTro"));
                    role.setTenVaiTro(rs.getString("TenVaiTro"));
                    role.setMoTa(rs.getString("MoTa"));
                    return role;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

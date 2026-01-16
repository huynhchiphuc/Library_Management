/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import util.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   LIBRARY MANAGEMENT SYSTEM - TEST");
        System.out.println("========================================\n");
        
        // Test 1: Load driver và khởi tạo DBConnection
        System.out.println("Test 1: Initializing DBConnection...");
        DBConnection dbConnection = DBConnection.getInstance();
        System.out.println("✓ DBConnection initialized\n");
        
        // Test 2: Test connection
        System.out.println("Test 2: Testing database connection...");
        boolean isTestSuccess = dbConnection.testConnection();
        
        if (isTestSuccess) {
            System.out.println("✓ Connection test passed\n");
        } else {
            System.err.println("✗ Connection test failed\n");
            System.err.println("Please check:");
            System.err.println("1. MySQL service is running");
            System.err.println("2. Database 'DB_QuanLyThuVien' exists");
            System.err.println("3. Username and password are correct");
            System.err.println("4. MySQL JDBC driver (mysql-connector-java.jar) is in classpath");
            return;
        }
        
        // Test 3: Thực hiện query đơn giản
        System.out.println("Test 3: Executing sample query...");
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            
            // Query để lấy thông tin database
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery("SELECT DATABASE() AS DatabaseName, NOW() AS CurrentTime");
            
            if (rs.next()) {
                System.out.println("✓ Query executed successfully");
                System.out.println("  Database: " + rs.getString("DatabaseName"));
                System.out.println("  Server Time: " + rs.getString("CurrentTime"));
            }
            
            DBConnection.closeAll(rs, stmt, null);
            System.out.println("✓ Resources closed properly\n");
            
        } catch (SQLException e) {
            System.err.println("✗ Query execution failed");
            e.printStackTrace();
        }
        
        // Test 4: Kiểm tra trạng thái connection
        System.out.println("Test 4: Checking connection status...");
        if (dbConnection.isConnected()) {
            System.out.println("✓ Connection is active\n");
        } else {
            System.err.println("✗ Connection is not active\n");
        }
        
        // Test 5: Đóng connection
        System.out.println("Test 5: Closing connection...");
        dbConnection.closeConnection();
        System.out.println("✓ Connection closed\n");
        
        // Tổng kết
        System.out.println("========================================");
        System.out.println("   ALL TESTS COMPLETED!");
        System.out.println("========================================");
        
        // Uncomment dòng dưới để giữ console window mở (nếu chạy từ IDE)
        // System.console().readLine("\nPress Enter to exit...");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Lớp quản lý kết nối cơ sở dữ liệu MySQL cho hệ thống quản lý thư viện
 * Sử dụng Singleton pattern để đảm bảo chỉ có một instance duy nhất
 * 
 * @author ASUS
 */
public class DBConnection {
    
    // Thông tin kết nối MySQL
    private static final String DB_SERVER = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "quanlythuvien";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    
    // Connection string cho MySQL
    private static final String DB_URL = "jdbc:mysql://" + DB_SERVER + ":" + DB_PORT 
            + "/" + DB_NAME 
            + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    
    // Singleton instance
    private static DBConnection instance;
    private Connection connection;
    
    /**
     * Private constructor để ngăn việc khởi tạo từ bên ngoài
     */
    private DBConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DB_DRIVER);
            System.out.println("MySQL JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            System.err.println("Please add mysql-connector-java driver to your project libraries");
            e.printStackTrace();
        }
    }
    
    /**
     * Lấy instance duy nhất của DBConnection (Singleton pattern)
     * 
     * @return DBConnection instance
     */
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    
    /**
     * Lấy connection tới MySQL database
     * Tự động tạo connection mới nếu connection hiện tại null hoặc đã đóng
     * 
     * @return Connection object
     * @throws SQLException nếu không thể kết nối
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Kết nối với MySQL
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                
                System.out.println("MySQL connected successfully!");
                System.out.println("Database: " + DB_NAME);
                
                // Thiết lập auto-commit
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Failed to connect to MySQL!");
                System.err.println("Server: " + DB_SERVER + ":" + DB_PORT);
                System.err.println("Database: " + DB_NAME);
                System.err.println("User: " + DB_USER);
                System.err.println("\nTroubleshooting tips:");
                System.err.println("1. Check if MySQL service is running");
                System.err.println("2. Verify database '" + DB_NAME + "' exists");
                System.err.println("3. Confirm username and password are correct");
                System.err.println("4. Check if MySQL allows connections from localhost");
                System.err.println("5. Verify firewall settings for port " + DB_PORT);
                throw e;
            }
        }
        return connection;
    }
    
    /**
     * Kiểm tra xem connection có đang hoạt động không
     * 
     * @return true nếu connection đang hoạt động, false nếu không
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Đóng connection tới database
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("MySQL connection closed!");
            } catch (SQLException e) {
                System.err.println("Error closing database connection!");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Đóng PreparedStatement
     * 
     * @param pstmt PreparedStatement cần đóng
     */
    public static void closePreparedStatement(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing PreparedStatement!");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Đóng Statement
     * 
     * @param stmt Statement cần đóng
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing Statement!");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Đóng ResultSet
     * 
     * @param rs ResultSet cần đóng
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet!");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Đóng tất cả resources (ResultSet, PreparedStatement, Connection)
     * 
     * @param rs ResultSet cần đóng
     * @param pstmt PreparedStatement cần đóng
     * @param conn Connection cần đóng
     */
    public static void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        closeResultSet(rs);
        closePreparedStatement(pstmt);
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing Connection!");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Đóng tất cả resources (ResultSet, Statement, Connection)
     * 
     * @param rs ResultSet cần đóng
     * @param stmt Statement cần đóng
     * @param conn Connection cần đóng
     */
    public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
        closeResultSet(rs);
        closeStatement(stmt);
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing Connection!");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Test kết nối MySQL database
     * 
     * @return true nếu kết nối thành công, false nếu không
     */
    public boolean testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("\n===== DATABASE CONNECTION TEST =====");
                System.out.println("Status: SUCCESS");
                System.out.println("Database Product: " + conn.getMetaData().getDatabaseProductName());
                System.out.println("Database Version: " + conn.getMetaData().getDatabaseProductVersion());
                System.out.println("Driver Name: " + conn.getMetaData().getDriverName());
                System.out.println("Driver Version: " + conn.getMetaData().getDriverVersion());
                System.out.println("URL: " + conn.getMetaData().getURL());
                System.out.println("====================================\n");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("\n===== DATABASE CONNECTION TEST =====");
            System.err.println("Status: FAILED");
            System.err.println("====================================\n");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Lấy connection string hiện tại (để debug)
     * 
     * @return connection string
     */
    public static String getConnectionString() {
        return DB_URL;
    }
    

}

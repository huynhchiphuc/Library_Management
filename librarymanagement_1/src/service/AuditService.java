/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AuditLogDAO;
import java.util.List;
import model.AuditLog;

/**
 * Service cho Audit Log
 * @author ASUS
 */
public class AuditService {
    
    private AuditLogDAO auditLogDAO;
    
    public AuditService() {
        this.auditLogDAO = new AuditLogDAO();
    }
    
    /**
     * Ghi log hành động
     */
    public boolean logAction(int maNguoiDung, String hanhDong, String tenBang, int maBanGhi, String moTaChiTiet) {
        return auditLogDAO.insertLog(maNguoiDung, hanhDong, tenBang, maBanGhi, moTaChiTiet);
    }
    
    /**
     * Lấy tất cả logs
     */
    public List<AuditLog> getAllLogs() {
        return auditLogDAO.getAllLogs();
    }
    
    /**
     * Lấy logs theo user
     */
    public List<AuditLog> getLogsByUser(int maNguoiDung) {
        return auditLogDAO.getLogsByUser(maNguoiDung);
    }
}

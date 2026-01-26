/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;

import dao.AuditLogDAO;
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
     * Ghi nhật ký hoạt động của người dùng
     * @param maNguoiDung Mã người dùng thực hiện hành động
     * @param hanhDong Loại hành động (ĐĂNG NHẬP, THÊM, SỬA, XÓA)
     * @param tenBang Tên bảng bị tác động (DauSach, DocGia, NguoiDung...)
     * @param maBanGhi Mã bản ghi bị tác động
     * @param moTaChiTiet Mô tả chi tiết hành động
     * @return true nếu ghi log thành công, false nếu thất bại
     * Xử lý: INSERT INTO NhatKyHoatDong với ThoiGian = CURRENT_TIMESTAMP
     */
    public boolean logAction(int maNguoiDung, String hanhDong, String tenBang, int maBanGhi, String moTaChiTiet) {
        return auditLogDAO.insertLog(maNguoiDung, hanhDong, tenBang, maBanGhi, moTaChiTiet);
    }
    
    /**
     * Lấy tất cả nhật ký hoạt động
     * @return List<AuditLog> danh sách tất cả log sắp xếp theo thời gian mới nhất
     */
    public List<AuditLog> getAllLogs() {
        return auditLogDAO.getAllLogs();
    }
    
    /**
     * Lấy nhật ký hoạt động theo người dùng
     * @param maNguoiDung Mã người dùng cần xem log
     * @return List<AuditLog> danh sách log của người dùng đó
     */
    public List<AuditLog> getLogsByUser(int maNguoiDung) {
        return auditLogDAO.getLogsByUser(maNguoiDung);
    }
}

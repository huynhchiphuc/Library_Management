/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.Date;
import java.util.List;

import dao.PenaltyDAO;
import model.Penalty;
import model.User;

/**
 *
 * @author ASUS
 */
public class PenaltyService {
    
    private PenaltyDAO penaltyDAO;
    private AuditService auditService;

    public PenaltyService() {
        penaltyDAO = new PenaltyDAO();
        auditService = new AuditService();
    }
    
    public List<Penalty> getAllPenalties() {
        return penaltyDAO.getAllPenalties();
    }
    
    public boolean createPenalty(int maDocGia, String lyDo, double soTien) {
        Penalty p = new Penalty();
        p.setMaDocGia(maDocGia);
        p.setLyDo(lyDo);
        p.setSoTien(soTien);
        p.setDaDongTien(false);
        p.setNgayTao(new Date());
        
        boolean result = penaltyDAO.insert(p);
        
        // Log audit if successful
        if (result) {
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null) {
                String description = String.format("Tạo phạt cho độc giả (Mã: %d) - Lý do: %s - Số tiền: %,.0f VNĐ", 
                    maDocGia, lyDo, soTien);
                auditService.logAction(currentUser.getId(), "INSERT", "PhatTien", 0, description);
            }
        }
        
        return result;
    }
    
    public boolean payPenalty(int penaltyId) {
        boolean result = penaltyDAO.updateStatus(penaltyId, true);
        
        // Log audit if successful
        if (result) {
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null) {
                String description = String.format("Thanh toán phạt (ID: %d)", penaltyId);
                auditService.logAction(currentUser.getId(), "UPDATE", "PhatTien", penaltyId, description);
            }
        }
        
        return result;
    }
    
    public double getTotalUnpaidPenalty(int maDocGia) {
        return penaltyDAO.getTotalUnpaidPenalty(maDocGia);
    }
    
    public List<Penalty> searchPenalties(String keyword) {
        return penaltyDAO.searchPenalties(keyword);
    }
}

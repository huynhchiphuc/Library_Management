/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.PenaltyDAO;
import java.util.Date;
import java.util.List;
import model.Penalty;

/**
 *
 * @author ASUS
 */
public class PenaltyService {
    
    private PenaltyDAO penaltyDAO;

    public PenaltyService() {
        penaltyDAO = new PenaltyDAO();
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
        return penaltyDAO.insert(p);
    }
    
    public boolean payPenalty(int penaltyId) {
        return penaltyDAO.updateStatus(penaltyId, true);
    }
    
    public double getTotalUnpaidPenalty(int maDocGia) {
        return penaltyDAO.getTotalUnpaidPenalty(maDocGia);
    }
}

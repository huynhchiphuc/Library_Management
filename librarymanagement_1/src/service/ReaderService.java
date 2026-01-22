/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;

import dao.ReaderDAO;
import model.Reader;
import model.User;

/**
 *
 * @author ASUS
 */
public class ReaderService {
    
    private ReaderDAO readerDAO;
    private AuditService auditService;

    public ReaderService() {
        readerDAO = new ReaderDAO();
        auditService = new AuditService();
    }

    public List<Reader> getAllReaders() {
        return readerDAO.getAllReaders();
    }
    
    public Reader findReaderByCardId(String cardId) {
        return readerDAO.getReaderByCardId(cardId);
    }
    
    public int getCurrentBorrowCount(int maDocGia) {
        return readerDAO.getCurrentBorrowCount(maDocGia);
    }
    
    public boolean addReader(Reader reader) {
        boolean result = readerDAO.addReader(reader);
        
        // Log audit if successful
        if (result) {
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null) {
                String description = String.format("Thêm độc giả: %s (Mã thẻ: %s)", 
                    reader.getHoTen(), reader.getMaThe());
                auditService.logAction(currentUser.getId(), "INSERT", "DocGia", 0, description);
            }
        }
        
        return result;
    }
    
    public boolean updateReader(Reader reader) {
        boolean result = readerDAO.updateReader(reader);
        
        // Log audit if successful
        if (result) {
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null) {
                String description = String.format("Cập nhật độc giả: %s (Mã thẻ: %s)", 
                    reader.getHoTen(), reader.getMaThe());
                auditService.logAction(currentUser.getId(), "UPDATE", "DocGia", 0, description);
            }
        }
        
        return result;
    }
    
    public boolean deleteReader(String maThe, String hoTen) {
        boolean result = readerDAO.deleteReader(maThe);
        
        // Log audit if successful
        if (result) {
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null) {
                String description = String.format("Xóa độc giả: %s (Mã thẻ: %s)", 
                    hoTen, maThe);
                auditService.logAction(currentUser.getId(), "DELETE", "DocGia", 0, description);
            }
        }
        
        return result;
    }
    
    public String generateNextMaThe() {
        return readerDAO.generateNextMaThe();
    }
    
    public List<Reader> searchReaders(String keyword) {
        return readerDAO.searchReader(keyword);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ReaderDAO;
import java.util.List;
import model.Reader;

/**
 *
 * @author ASUS
 */
public class ReaderService {
    
    private ReaderDAO readerDAO;

    public ReaderService() {
        readerDAO = new ReaderDAO();
    }

    public List<Reader> getAllReaders() {
        return readerDAO.getAllReaders();
    }
    
    public Reader findReaderByCardId(String cardId) {
        return readerDAO.getReaderByCardId(cardId);
    }
}

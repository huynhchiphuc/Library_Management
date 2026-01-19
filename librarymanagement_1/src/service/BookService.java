/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.BookCopyDAO;
import dao.BookDAO;
import java.util.List;
import model.Book;
import model.BookCopy;

/**
 *
 * @author ASUS
 */
public class BookService {
    
    private BookDAO bookDAO;
    private BookCopyDAO bookCopyDAO;

    public BookService() {
        bookDAO = new BookDAO();
        bookCopyDAO = new BookCopyDAO();
    }
    
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
    
    public BookCopy findBookCopyByBarcode(String barcode) {
        return bookCopyDAO.getBookCopyByBarcode(barcode);
    }
    
    public boolean updateBookCopyStatus(int maCuonSach, int status) {
        return bookCopyDAO.updateStatus(maCuonSach, status);
    }
}

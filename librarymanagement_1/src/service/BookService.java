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
    
    public boolean addBook(Book book, int quantity, double price) {
        // 1. Check if Book Title exists
        int existingId = bookDAO.findBookId(book.getTitle(), book.getAuthor(), book.getPublishYear());
        int bookId;
        
        if (existingId != -1) {
            bookId = existingId;
        } else {
            // Create new Title
            bookId = bookDAO.insertBook(book);
            if (bookId == -1) return false;
        }
        
        // 2. Add copies
        return bookDAO.insertCopies(bookId, quantity, price);
    }
    
    public boolean updateBookInfo(Book b) {
        return bookDAO.updateBook(b);
    }
    
    public boolean deleteBook(int id) {
        return bookDAO.deleteBook(id);
    }
    
    public List<Book> searchBooks(String keyword) {
        return bookDAO.searchBooks(keyword);
    }

    public List<String> getBarcodes(int bookId) {
        return bookDAO.getBarcodes(bookId);
    }
}

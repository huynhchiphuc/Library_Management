/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;

import dao.BookCopyDAO;
import dao.BookDAO;
import model.Book;
import model.BookCopy;
import model.User;

/**
 *
 * @author ASUS
 */
public class BookService {
    
    private BookDAO bookDAO;
    private BookCopyDAO bookCopyDAO;
    private AuditService auditService;

    public BookService() {
        bookDAO = new BookDAO();
        bookCopyDAO = new BookCopyDAO();
        auditService = new AuditService();
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
        boolean isNewBook = false;
        
        if (existingId != -1) {
            bookId = existingId;
        } else {
            // Create new Title
            bookId = bookDAO.insertBook(book);
            if (bookId == -1) return false;
            isNewBook = true;
        }
        
        // 2. Add copies
        boolean result = bookDAO.insertCopies(bookId, quantity, price);
        
        // 3. Log audit if successful
        if (result) {
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null) {
                String action = isNewBook ? "INSERT" : "UPDATE";
                String description = isNewBook 
                    ? String.format("Thêm sách mới: %s (Tác giả: %s) - Số lượng: %d", 
                        book.getTitle(), book.getAuthor(), quantity)
                    : String.format("Thêm %d bản sao cho sách: %s", 
                        quantity, book.getTitle());
                auditService.logAction(currentUser.getId(), action, "DauSach", bookId, description);
            }
        }
        
        return result;
    }
    
    public boolean updateBookInfo(Book b) {
        boolean result = bookDAO.updateBook(b);
        
        // Log audit if successful
        if (result) {
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null) {
                String description = String.format("Cập nhật thông tin sách: %s (ID: %d)", 
                    b.getTitle(), b.getId());
                auditService.logAction(currentUser.getId(), "UPDATE", "DauSach", b.getId(), description);
            }
        }
        
        return result;
    }
    
    public boolean deleteBook(int id) {
        // Get book info before deleting for audit log
        Book book = null;
        List<Book> allBooks = bookDAO.getAllBooks();
        for (Book b : allBooks) {
            if (b.getId() == id) {
                book = b;
                break;
            }
        }
        
        boolean result = bookDAO.deleteBook(id);
        
        // Log audit if successful
        if (result) {
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null && book != null) {
                String description = String.format("Xóa sách: %s (ID: %d)", 
                    book.getTitle(), id);
                auditService.logAction(currentUser.getId(), "DELETE", "DauSach", id, description);
            }
        }
        
        return result;
    }
    
    public List<Book> searchBooks(String keyword) {
        return bookDAO.searchBooks(keyword);
    }

    public List<String> getBarcodes(int bookId) {
        return bookDAO.getBarcodes(bookId);
    }
    
    public List<Book> getBooksByCategory(int categoryId) {
        return bookDAO.getBooksByCategory(categoryId);
    }
    
    public Book getBookByBarcode(String barcode) {
        return bookDAO.getBookByBarcode(barcode);
    }
}

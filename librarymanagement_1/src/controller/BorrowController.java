/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.BookCopy;
import model.Reader;
import service.BookService;
import service.BorrowService;
import service.ReaderService;
import view.BorrowForm;

/**
 *
 * @author ASUS
 */
public class BorrowController {
    
    private BorrowForm view;
    private ReaderService readerService;
    private BookService bookService;
    private BorrowService borrowService;
    
    private Reader currentReader;
    private List<BookCopy> cart;
    private DefaultTableModel tableModel;

    public BorrowController(BorrowForm view) {
        this.view = view;
        this.readerService = new ReaderService();
        this.bookService = new BookService();
        this.borrowService = new BorrowService();
        this.cart = new ArrayList<>();
        this.tableModel = (DefaultTableModel) view.getTblBorrow().getModel();
        
        initEvents();
    }
    
    private void initEvents() {
        // Check Reader
        view.getBtnCheckReader().addActionListener(e -> checkReader());
        
        // Add Book
        view.getBtnAddBook().addActionListener(e -> addBook());
        
        // Borrow
        view.getBtnBorrow().addActionListener(e -> borrow());
        
        // Reset
        view.getBtnReset().addActionListener(e -> resetForm());
        
        // Return (Placeholder for now)
        view.getBtnReturn().addActionListener(e -> JOptionPane.showMessageDialog(view, "Chức năng Nhận trả đang phát triển!"));
    }
    
    private void checkReader() {
        String cardId = view.getTxtMaThe().getText().trim();
        if (cardId.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã thẻ độc giả!");
            return;
        }
        
        Reader reader = readerService.findReaderByCardId(cardId);
        if (reader != null) {
            this.currentReader = reader;
            view.getLblTenDocGia().setText(reader.getHoTen());
            if (reader.isBiKhoa()) {
                 view.getLblTenDocGia().setText(reader.getHoTen() + " (ĐANG BỊ KHÓA)");
                 view.getLblTenDocGia().setForeground(java.awt.Color.RED);
            } else {
                 view.getLblTenDocGia().setForeground(new java.awt.Color(0, 102, 204));
            }
        } else {
            this.currentReader = null;
            view.getLblTenDocGia().setText("Không tìm thấy độc giả!");
            view.getLblTenDocGia().setForeground(java.awt.Color.RED);
        }
    }
    
    private void addBook() {
        String barcode = view.getTxtMaSach().getText().trim();
        if (barcode.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã sách (Barcode)!");
            return;
        }
        
        // Check duplicate in cart
        for (BookCopy b : cart) {
            if (b.getMaVach().equals(barcode)) {
                JOptionPane.showMessageDialog(view, "Sách này đã có trong danh sách mượn!");
                return;
            }
        }
        
        BookCopy book = bookService.findBookCopyByBarcode(barcode);
        if (book != null) {
            if (book.getTrangThai() != 1) { // 1 = Available
                JOptionPane.showMessageDialog(view, "Sách này không khả dụng (Đã mượn, mất, v.v...)!");
                return;
            }
            
            cart.add(book);
            refreshTable();
            view.getTxtMaSach().setText("");
        } else {
            JOptionPane.showMessageDialog(view, "Không tìm thấy sách với mã vạch này!");
        }
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hanTraStr = view.getTxtHanTra().getText();
        
        for (BookCopy b : cart) {
            tableModel.addRow(new Object[]{
                b.getMaVach(),
                b.getTuaDe(),
                b.getTacGia(),
                String.format("%,.0f", b.getGiaTien()),
                hanTraStr // Display due date for reference
            });
        }
    }
    
    private void borrow() {
        if (currentReader == null) {
            JOptionPane.showMessageDialog(view, "Chưa chọn độc giả hoặc độc giả không hợp lệ!");
            return;
        }
        if (currentReader.isBiKhoa()) {
            JOptionPane.showMessageDialog(view, "Độc giả đang bị khóa, không thể mượn sách!");
            return;
        }
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Danh sách sách mượn đang trống!");
            return;
        }
        
        // Parse Due Date
        String dateStr = view.getTxtHanTra().getText().trim();
        Date dueDate = null;
        try {
            dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Ngày trả không hợp lệ (dd/MM/yyyy)!");
            return;
        }
        
        if (dueDate.before(new Date())) {
            JOptionPane.showMessageDialog(view, "Hạn trả phải sau ngày hôm nay!");
            return;
        }
        
        // Execute Borrow
        boolean success = borrowService.borrowBooks(currentReader.getMaDocGia(), cart, dueDate);
        if (success) {
            JOptionPane.showMessageDialog(view, "Mượn sách thành công!");
            resetForm();
        } else {
            JOptionPane.showMessageDialog(view, "Có lỗi xảy ra khi mượn sách!");
        }
    }
    
    private void resetForm() {
        currentReader = null;
        cart.clear();
        refreshTable();
        view.getTxtMaThe().setText("");
        view.getTxtMaSach().setText("");
        view.getLblTenDocGia().setText("...");
        view.getLblTenDocGia().setForeground(new java.awt.Color(0, 102, 204));
        view.getTxtHanTra().setText("");
    }
}

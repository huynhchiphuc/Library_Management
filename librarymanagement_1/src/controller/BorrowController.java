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
        
        initView();
        initEvents();
    }
    
    /**
     * Khởi tạo giao diện ban đầu
     * Xử lý: Khởi tạo cart (ArrayList), tạo table model, set hạn trả mặc định = hôm nay + 14 ngày
     */
    private void initView() {
        // Disable auto fields
        view.getTxtHanTra().setEditable(false);
        view.getTxtHanTra().setBackground(new java.awt.Color(240, 240, 240));
        
        // Set default due date (14 days from now)
        setDefaultDueDate();
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
        
        // Return
        view.getBtnReturn().addActionListener(e -> returnBook());
    }
    
    /**
     * Set hạn trả mặc định
     * Xử lý: Tính ngày hiện tại + 14 ngày (DEFAULT_BORROW_DAYS), format dd/MM/yyyy, đưa vào txtDueDate
     */
    private void setDefaultDueDate() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, util.Constants.DEFAULT_BORROW_DAYS); // 14 days
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        view.getTxtHanTra().setText(sdf.format(cal.getTime()));
    }
    
    /**
     * Xử lý trả sách
     * Không có tham số (lấy barcode từ txtBarcode)
     * Xử lý:
     * 1. Validate barcode không rỗng
     * 2. Gọi BorrowService.returnBook(barcode)
     * 3. Service sẽ: cập nhật trạng thái sách, tính phạt trễ hạn (nếu có)
     * 4. Hiển thị thông báo kết quả (kèm tiền phạt nếu có)
     * 5. Clear form
     */
    private void returnBook() {
        String barcode = view.getTxtMaSach().getText().trim();
        if (barcode.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập Mã vạch của sách cần trả!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
                "Xác nhận nhận trả sách có mã vạch: " + barcode + "?", 
                "Xác nhận trả sách", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String result = borrowService.returnBook(barcode);
            
            if (result.startsWith("Trả sách thành công")) {
                JOptionPane.showMessageDialog(view, result, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                view.getTxtMaSach().setText("");
                view.getTxtMaSach().requestFocus();
            } else {
                JOptionPane.showMessageDialog(view, result, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void checkReader() {
        String cardId = view.getTxtMaThe().getText().trim();
        if (cardId.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập mã thẻ độc giả!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Reader reader = readerService.findReaderByCardId(cardId);
        if (reader != null) {
            this.currentReader = reader;
            
            // Check reader status
            if (reader.isBiKhoa()) {
                 view.getLblTenDocGia().setText(reader.getHoTen() + " (ĐANG BỊ KHÓA)");
                 view.getLblTenDocGia().setForeground(java.awt.Color.RED);
                 JOptionPane.showMessageDialog(view, 
                     "Độc giả này đang bị khóa!\n" +
                     "Không thể mượn sách.",
                     "Cảnh báo",
                     JOptionPane.WARNING_MESSAGE);
            } else {
                 view.getLblTenDocGia().setText(reader.getHoTen());
                 view.getLblTenDocGia().setForeground(new java.awt.Color(0, 153, 0)); // Green
                 
                 // Show reader info
                 int borrowed = readerService.getCurrentBorrowCount(reader.getMaDocGia());
                 int limit = reader.getGioHanMuon();
                 
                 JOptionPane.showMessageDialog(view, 
                     "Tìm thấy độc giả:\n" +
                     "Họ tên: " + reader.getHoTen() + "\n" +
                     "SĐT: " + reader.getSoDienThoai() + "\n" +
                     "Đang mượn: " + borrowed + "/" + limit + " cuốn",
                     "Thông tin độc giả",
                     JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            this.currentReader = null;
            view.getLblTenDocGia().setText("Không tìm thấy độc giả!");
            view.getLblTenDocGia().setForeground(java.awt.Color.RED);
            JOptionPane.showMessageDialog(view, 
                "Không tìm thấy độc giả với mã thẻ: " + cardId,
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addBook() {
        if (currentReader == null) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng kiểm tra độc giả trước!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String barcode = view.getTxtMaSach().getText().trim();
        if (barcode.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập mã vạch sách!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Check duplicate in cart
        for (BookCopy b : cart) {
            if (b.getMaVach().equals(barcode)) {
                JOptionPane.showMessageDialog(view, 
                    "Sách này đã có trong danh sách mượn!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        
        BookCopy book = bookService.findBookCopyByBarcode(barcode);
        if (book != null) {
            if (book.getTrangThai() != 1) { // 1 = Available
                JOptionPane.showMessageDialog(view, 
                    "Sách này không khả dụng!\n" +
                    "Trạng thái: " + getStatusText(book.getTrangThai()),
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            cart.add(book);
            refreshTable();
            view.getTxtMaSach().setText("");
            view.getTxtMaSach().requestFocus();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Không tìm thấy sách với mã vạch: " + barcode,
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getStatusText(int status) {
        switch(status) {
            case 1: return "Sẵn sàng";
            case 2: return "Đang mượn";
            case 3: return "Mất";
            case 4: return "Hỏng";
            default: return "Không xác định";
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
            JOptionPane.showMessageDialog(view, 
                "Chưa chọn độc giả hoặc độc giả không hợp lệ!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (currentReader.isBiKhoa()) {
            JOptionPane.showMessageDialog(view, 
                "Độc giả đang bị khóa, không thể mượn sách!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Danh sách sách mượn đang trống!\n" +
                "Vui lòng thêm sách vào danh sách.",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Parse Due Date
        String dateStr = view.getTxtHanTra().getText().trim();
        Date dueDate = null;
        try {
            dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Ngày trả không hợp lệ!\n" +
                "Định dạng: dd/MM/yyyy",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (dueDate.before(new Date())) {
            JOptionPane.showMessageDialog(view, 
                "Hạn trả phải sau ngày hôm nay!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirm
        int confirm = JOptionPane.showConfirmDialog(view,
            "Xác nhận cho mượn:\n" +
            "Độc giả: " + currentReader.getHoTen() + "\n" +
            "Số sách: " + cart.size() + " cuốn\n" +
            "Hạn trả: " + dateStr,
            "Xác nhận mượn sách",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Execute Borrow
        boolean success = borrowService.borrowBooks(currentReader.getMaDocGia(), cart, dueDate);
        if (success) {
            JOptionPane.showMessageDialog(view, 
                "Mượn sách thành công!\n" +
                "Số sách: " + cart.size() + " cuốn\n" +
                "Hạn trả: " + dateStr,
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
            resetForm();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Có lỗi xảy ra khi mượn sách!\n" +
                "Độc giả có thể đã vượt quá giới hạn mượn.",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
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
        setDefaultDueDate();
    }
}

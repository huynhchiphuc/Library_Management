/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.BookDAO;
import dao.CategoryDAO;
import model.Book;
import model.Category;
import service.BookService;
import view.BookForm;

public class BookController {
    
    private final BookForm view;
    private final BookDAO bookDAO;
    private final CategoryDAO categoryDAO;
    private final BookService bookService;
    
    public BookController(BookForm view) {
        this.view = view;
        this.bookDAO = new BookDAO();
        this.categoryDAO = new CategoryDAO();
        this.bookService = new BookService();
        
        initView();
        initController();
    }
    
    /**
     * Khởi tạo giao diện ban đầu
     * Xử lý: Load danh sách thể loại vào ComboBox, load dữ liệu sách vào bảng
     */
    private void initView() {
        loadCategories();
        loadTableData(bookDAO.getAllBooks());
        
        // Disable auto-generated fields
        view.getTxtMaDauSach().setEditable(false);
        view.getTxtMaDauSach().setBackground(new java.awt.Color(240, 240, 240));
        view.getTxtMaDauSach().setText("(Tự động)");
        
        view.getTxtBarcodes().setEditable(false);
        view.getTxtBarcodes().setBackground(new java.awt.Color(240, 240, 240));
    }
    
    /**
     * Khởi tạo event handlers cho các nút và components
     * Xử lý: Gắn ActionListener cho các button (Thêm, Sửa, Xóa, Reset, Tìm kiếm)
     *        và MouseListener cho JTable để fill form khi click chọn dòng
     */
    private void initController() {
        view.getBtnAdd().addActionListener(e -> addBook());
        view.getBtnEdit().addActionListener(e -> updateBook());
        view.getBtnDelete().addActionListener(e -> deleteBook());
        view.getBtnReset().addActionListener(e -> clearForm());
        view.getBtnSearch().addActionListener(e -> performSearch());
        view.getBtnViewAll().addActionListener(e -> viewAll());
        
        // Add filter by category
        view.getCboTheLoai().addActionListener(e -> {
            if (view.getCboTheLoai().getItemCount() > 0) {
                filterByCategory();
            }
        });
        
        view.getTblBook().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTblBook().getSelectedRow() != -1) {
                fillForm();
                updateButtonStates(true);
            }
        });
        
        // Initial button state
        updateButtonStates(false);
    }
    
    /**
     * Load danh sách thể loại vào ComboBox
     * Xử lý: Lấy danh sách từ CategoryDAO, thêm vào ComboBox model
     */
    private void loadCategories() {
        List<Category> categories = categoryDAO.getAllCategories();
        DefaultComboBoxModel<Category> model = new DefaultComboBoxModel<>();
        for (Category c : categories) {
            model.addElement(c);
        }
        // Suppress unchecked warning
        @SuppressWarnings("unchecked")
        javax.swing.JComboBox<Category> cbo = (javax.swing.JComboBox<Category>) view.getCboTheLoai();
        cbo.setModel(model);
    }
    
    /**
     * Hiển thị danh sách sách lên JTable
     * @param books Danh sách Book cần hiển thị
     * Xử lý: Clear bảng, duyệt List<Book>, thêm từng dòng với format:
     *        Mã sách, Tựa đề, Tác giả, NXB, Năm XB, Thể loại, Số lượng, Giá
     */
    private void loadTableData(List<Book> books) {
        DefaultTableModel model = (DefaultTableModel) view.getTblBook().getModel();
        model.setColumnIdentifiers(new Object[]{"Mã", "Tựa đề", "Tác giả", "Thể loại", "NXB", "Năm XB", "Giá tiền", "Số lượng", "Mô tả"});
        model.setRowCount(0);
        
        for (Book b : books) {
            // Format price with thousand separator
            String formattedPrice = String.format("%,.0f VNĐ", b.getPrice());
            
            Object[] row = {
                b.getId(),
                b.getTitle(),
                b.getAuthor(),
                b.getCategoryName(),
                b.getPublisher(),
                b.getPublishYear(),
                formattedPrice,
                b.getQuantity(),
                b.getDescription()
            };
            model.addRow(row);
        }
        
        // Update result count
        view.getLblResultCount().setText("Tổng: " + books.size() + " kết quả");
    }
    
    private void fillForm() {
        int row = view.getTblBook().getSelectedRow();
        if (row >= 0) {
            int id = (int) view.getTblBook().getValueAt(row, 0);
            view.getTxtMaDauSach().setText(String.valueOf(view.getTblBook().getValueAt(row, 0)));
            view.getTxtTuaDe().setText((String) view.getTblBook().getValueAt(row, 1));
            view.getTxtTacGia().setText((String) view.getTblBook().getValueAt(row, 2));
            view.getTxtNXB().setText((String) view.getTblBook().getValueAt(row, 4));
            view.getTxtNamXB().setText(String.valueOf(view.getTblBook().getValueAt(row, 5)));
            
            // Remove formatting from price (remove "VND" and commas)
            String priceStr = view.getTblBook().getValueAt(row, 6).toString();
            priceStr = priceStr.replace(" VN\u0110", "").replace(",", "").trim();
            view.getTxtGiaTien().setText(priceStr);
            
            view.getTxtSoLuong().setText(String.valueOf(view.getTblBook().getValueAt(row, 7)));
            
            // Handle Description (might be null)
            Object descObj = view.getTblBook().getValueAt(row, 8);
            view.getTxtMoTa().setText(descObj != null ? descObj.toString() : "");
            
            // Show Barcodes
            List<String> barcodes = bookService.getBarcodes(id);
            view.getTxtBarcodes().setText(String.join(", ", barcodes));
            
            String categoryName = (String) view.getTblBook().getValueAt(row, 3);
            selectCategoryByName(categoryName);
        }
    }
    
    private void selectCategoryByName(String name) {
        DefaultComboBoxModel<Category> model = (DefaultComboBoxModel<Category>) view.getCboTheLoai().getModel();
        for (int i = 0; i < model.getSize(); i++) {
            Category c = model.getElementAt(i);
            if (c.getName().equals(name)) {
                view.getCboTheLoai().setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void addBook() {
        Book b = getBookFromForm();
        if (b != null) {
            // Check quantity and price
            if (b.getQuantity() <= 0) {
                 JOptionPane.showMessageDialog(view, "Số lượng phải lớn hơn 0!");
                 return;
            }
            if (b.getPrice() < 0) {
                 JOptionPane.showMessageDialog(view, "Giá tiền không được âm!");
                 return;
            }
            
            if (bookService.addBook(b, b.getQuantity(), b.getPrice())) {
                JOptionPane.showMessageDialog(view, 
                    "Thêm thành công!\n" +
                    "- Đã tạo " + b.getQuantity() + " cuốn sách với mã vạch tự động\n" +
                    "- Nếu sách đã tồn tại, số lượng đã được cập nhật",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
                loadTableData(bookService.getAllBooks());
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateBook() {
        String idStr = view.getTxtMaDauSach().getText().trim();
        
        // Check if editing existing book
        if (idStr.isEmpty() || "(Tự động)".equals(idStr)) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn sách từ bảng để sửa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Book b = getBookFromForm();
        if (b != null) {
            try {
                b.setId(Integer.parseInt(idStr));
                if (bookService.updateBookInfo(b)) {
                    JOptionPane.showMessageDialog(view, 
                        "Cập nhật thành công!\n" +
                        "Lưu ý: Số lượng và mã vạch không thay đổi",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadTableData(bookService.getAllBooks());
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Mã đầu sách không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteBook() {
        String idStr = view.getTxtMaDauSach().getText().trim();
        
        // Check if a book is selected
        if (idStr.isEmpty() || "(Tự động)".equals(idStr)) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn sách từ bảng để xóa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int bookId = Integer.parseInt(idStr);
            String bookTitle = view.getTxtTuaDe().getText();
            
            int confirm = JOptionPane.showConfirmDialog(view, 
                "Bạn có chắc muốn xóa sách:\n" +
                "Mã: " + bookId + "\n" +
                "Tên: " + bookTitle + "\n\n" +
                "Lưu ý: Xóa sách sẽ xóa tất cả cuốn sách và mã vạch liên quan!",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (bookService.deleteBook(bookId)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadTableData(bookService.getAllBooks());
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "Xóa thất bại!\n" +
                        "Sách có thể đang được mượn hoặc có dữ liệu liên quan.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Mã đầu sách không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performSearch() {
        String searchText = view.getTxtSearch().getText().trim();
        
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập từ khóa hoặc mã vạch!");
            return;
        }
        
        String searchType = (String) view.getCboSearchType().getSelectedItem();
        
        if ("Từ khóa".equals(searchType)) {
            // Search by keyword
            List<Book> results = bookService.searchBooks(searchText);
            loadTableData(results);
            
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không tìm thấy kết quả nào!");
            }
        } else {
            // Search by barcode
            Book book = bookService.getBookByBarcode(searchText);
            if (book != null) {
                List<Book> singleResult = new ArrayList<>();
                singleResult.add(book);
                loadTableData(singleResult);
            } else {
                JOptionPane.showMessageDialog(view, "Không tìm thấy sách với mã vạch: " + searchText);
                loadTableData(new ArrayList<>());
            }
        }
    }
    
    private void viewAll() {
        view.getTxtSearch().setText("");
        loadTableData(bookService.getAllBooks());
        //clearForm();
    }
    
    private void filterByCategory() {
        Object selected = view.getCboTheLoai().getSelectedItem();
        if (selected != null && selected instanceof Category) {
            Category cat = (Category) selected;
            if (cat.getId() > 0) {
                loadTableData(bookService.getBooksByCategory(cat.getId()));
            }
        }
    }
    
    private void updateButtonStates(boolean isRowSelected) {
        view.getBtnEdit().setEnabled(isRowSelected);
        view.getBtnDelete().setEnabled(isRowSelected);
        view.getBtnAdd().setEnabled(true); // Always enabled
        view.getBtnReset().setEnabled(true); // Always enabled
    }
    
    private void clearForm() {
        view.getTxtMaDauSach().setText("(Tự động)");
        view.getTxtTuaDe().setText("");
        view.getTxtTacGia().setText("");
        view.getTxtNXB().setText("");
        view.getTxtNamXB().setText("");
        view.getTxtGiaTien().setText("");
        view.getTxtSoLuong().setText("");
        view.getTxtMoTa().setText("");
        view.getTxtBarcodes().setText("(Sẽ tự động sinh sau khi thêm)");
        if (view.getCboTheLoai().getItemCount() > 0) {
            view.getCboTheLoai().setSelectedIndex(0);
        }
        
        // Clear selection
        view.getTblBook().clearSelection();
        updateButtonStates(false);
    }
    
    private Book getBookFromForm() {
        try {
            String title = view.getTxtTuaDe().getText().trim();
            String author = view.getTxtTacGia().getText().trim();
            String nxb = view.getTxtNXB().getText().trim();
            
            // Validate empty fields
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Tuựa sách không được để trống!");
                return null;
            }
            if (author.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Tác giả không được để trống!");
                return null;
            }
            
            // Validate Year
            String yearStr = view.getTxtNamXB().getText().trim();
            if (yearStr.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Năm xuất bản không được để trống!");
                return null;
            }
            int year = Integer.parseInt(yearStr);
            int currentYear = java.time.Year.now().getValue();
            if (year < 1000 || year > currentYear + 1) {
                JOptionPane.showMessageDialog(view, "Năm xuất bản không hợp lệ (1000 - " + (currentYear + 1) + ")!");
                return null;
            }
            
            // Validate Price & Quantity
            double price = 0;
            String priceStr = view.getTxtGiaTien().getText().trim();
            if (!priceStr.isEmpty()) {
                 price = Double.parseDouble(priceStr);
                 if (price < 0) {
                     JOptionPane.showMessageDialog(view, "Giá tiền không được âm!");
                     return null;
                 }
            }
            
            int qty = 0;
            String qtyStr = view.getTxtSoLuong().getText().trim();
            if(!qtyStr.isEmpty()) {
                qty = Integer.parseInt(qtyStr);
                if (qty < 0) {
                    JOptionPane.showMessageDialog(view, "Số lượng không được âm!");
                    return null;
                }
            }
            
            // Validate Category
            Object selected = view.getCboTheLoai().getSelectedItem();
            if (selected == null || !(selected instanceof Category)) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn Thể loại (cần có dữ liệu trong CSDL)!");
                return null;
            }
            Category cat = (Category) selected;
            
            String desc = view.getTxtMoTa().getText().trim();
            
            Book b = new Book();
            b.setTitle(title);
            b.setAuthor(author);
            b.setPublisher(nxb);
            b.setPublishYear(year);
            b.setCategoryId(cat.getId());
            b.setDescription(desc);
            b.setPrice(price);
            b.setQuantity(qty);
            
            return b;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Năm XB, Giá tiền, Số lượng phải là số hợp lệ!");
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi dữ liệu: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

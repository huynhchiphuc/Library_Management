/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CategoryDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Book;
import model.Category;
import view.BookForm;

public class BookController {
    
    private final BookForm view;
    private final BookDAO bookDAO;
    private final CategoryDAO categoryDAO;
    
    public BookController(BookForm view) {
        this.view = view;
        this.bookDAO = new BookDAO();
        this.categoryDAO = new CategoryDAO();
        
        initView();
        initController();
    }
    
    private void initView() {
        loadCategories();
        loadTableData(bookDAO.getAllBooks());
    }
    
    private void initController() {
        view.getBtnAdd().addActionListener(e -> addBook());
        view.getBtnEdit().addActionListener(e -> updateBook());
        view.getBtnDelete().addActionListener(e -> deleteBook());
        view.getBtnReset().addActionListener(e -> clearForm());
        view.getBtnSearch().addActionListener(e -> searchBook());
        
        view.getTblBook().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTblBook().getSelectedRow() != -1) {
                fillForm();
            }
        });
    }
    
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
    
    private void loadTableData(List<Book> books) {
        DefaultTableModel model = (DefaultTableModel) view.getTblBook().getModel();
        model.setColumnIdentifiers(new Object[]{"Mã", "Tựa đề", "Tác giả", "Thể loại", "NXB", "Năm XB", "Giá tiền", "Số lượng", "Mô tả"});
        model.setRowCount(0);
        
        for (Book b : books) {
            Object[] row = {
                b.getId(),
                b.getTitle(),
                b.getAuthor(),
                b.getCategoryName(),
                b.getPublisher(),
                b.getPublishYear(),
                b.getPrice(),
                b.getQuantity(),
                b.getDescription()
            };
            model.addRow(row);
        }
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
            view.getTxtGiaTien().setText(String.valueOf(view.getTblBook().getValueAt(row, 6)));
            view.getTxtSoLuong().setText(String.valueOf(view.getTblBook().getValueAt(row, 7)));
            
            // Handle Description (might be null)
            Object descObj = view.getTblBook().getValueAt(row, 8);
            view.getTxtMoTa().setText(descObj != null ? descObj.toString() : "");
            
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
            if (bookDAO.insertBook(b)) {
                JOptionPane.showMessageDialog(view, "Thêm thành công!");
                loadTableData(bookDAO.getAllBooks());
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!");
            }
        }
    }
    
    private void updateBook() {
        Book b = getBookFromForm();
        if (b != null && !view.getTxtMaDauSach().getText().isEmpty()) {
            b.setId(Integer.parseInt(view.getTxtMaDauSach().getText()));
            if (bookDAO.updateBook(b)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadTableData(bookDAO.getAllBooks());
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
            }
        }
    }
    
    private void deleteBook() {
        String idStr = view.getTxtMaDauSach().getText();
        if (!idStr.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (bookDAO.deleteBook(Integer.parseInt(idStr))) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadTableData(bookDAO.getAllBooks());
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại!");
                }
            }
        }
    }
    
    private void searchBook() {
        String keyword = JOptionPane.showInputDialog(view, "Nhập từ khóa tìm kiếm:");
        if (keyword != null && !keyword.isEmpty()) {
            loadTableData(bookDAO.searchBooks(keyword));
        } else {
            loadTableData(bookDAO.getAllBooks());
        }
    }
    
    private void clearForm() {
        view.getTxtMaDauSach().setText("");
        view.getTxtTuaDe().setText("");
        view.getTxtTacGia().setText("");
        view.getTxtNXB().setText("");
        view.getTxtNamXB().setText("");
        view.getTxtMoTa().setText("");
        if (view.getCboTheLoai().getItemCount() > 0) {
            view.getCboTheLoai().setSelectedIndex(0);
        }
    }
    
    private Book getBookFromForm() {
        try {
            String title = view.getTxtTuaDe().getText();
            String author = view.getTxtTacGia().getText();
            String nxb = view.getTxtNXB().getText();
            
            // Validate Year
            String yearStr = view.getTxtNamXB().getText();
            if (yearStr.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Năm xuất bản không được để trống!");
                return null;
            }
            int year = Integer.parseInt(yearStr);
            
            // Validate Category
            Object selected = view.getCboTheLoai().getSelectedItem();
            if (selected == null || !(selected instanceof Category)) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn Thể loại (cần có dữ liệu trong CSDL)!");
                return null;
            }
            Category cat = (Category) selected;
            
            String desc = view.getTxtMoTa().getText();
            
            Book b = new Book();
            b.setTitle(title);
            b.setAuthor(author);
            b.setPublisher(nxb);
            b.setPublishYear(year);
            b.setCategoryId(cat.getId());
            b.setDescription(desc);
            
            return b;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Năm xuất bản phải là số nguyên!");
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi dữ liệu: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

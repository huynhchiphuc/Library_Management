/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.CategoryDAO;
import model.Category;
import view.CategoryForm;

/**
 *
 * @author ASUS
 */
public class CategoryController {
    
    private CategoryForm view;
    private CategoryDAO dao;

    public CategoryController(CategoryForm view) {
        this.view = view;
        this.dao = new CategoryDAO();
        
        initView();
        initEvents();
        loadData();
    }
    
    private void initView() {
        // Setup initial state
        view.getTxtMaTheLoai().setText("(Tự động)");
        updateButtonStates(false);
    }
    
    private void initEvents() {
        view.getBtnAdd().addActionListener(e -> addCategory());
        view.getBtnEdit().addActionListener(e -> updateCategory());
        view.getBtnDelete().addActionListener(e -> deleteCategory());
        view.getBtnReset().addActionListener(e -> clearForm());
        view.getBtnSearch().addActionListener(e -> performSearch());
        view.getBtnViewAll().addActionListener(e -> loadData());
        
        view.getTblCategory().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectRow();
                updateButtonStates(view.getTblCategory().getSelectedRow() != -1);
            }
        });
    }
    
    private void loadData() {
        List<Category> list = dao.getAllCategories();
        displayCategories(list);
    }
    
    private void displayCategories(List<Category> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTblCategory().getModel();
        model.setRowCount(0);
        
        for (Category cat : list) {
            model.addRow(new Object[]{
                cat.getId(),
                cat.getName(),
                cat.getDescription()
            });
        }
        
        view.getLblResultCount().setText("Tổng: " + list.size() + " thể loại");
    }
    
    private void addCategory() {
        String tenTheLoai = view.getTxtTenTheLoai().getText().trim();
        String moTa = view.getTxtMoTa().getText().trim();
        
        if (tenTheLoai.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập tên thể loại!", 
                "Cảnh báo", 
                JOptionPane.WARNING_MESSAGE);
            view.getTxtTenTheLoai().requestFocus();
            return;
        }
        
        // Check duplicate
        if (dao.isCategoryExist(tenTheLoai)) {
            JOptionPane.showMessageDialog(view, 
                "Thể loại '" + tenTheLoai + "' đã tồn tại!\n" +
                "Vui lòng chọn tên khác.",
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Category cat = new Category();
        cat.setName(tenTheLoai);
        cat.setDescription(moTa.isEmpty() ? null : moTa);
        
        if (dao.insertCategory(cat)) {
            JOptionPane.showMessageDialog(view, 
                "Thêm thể loại thành công!\n" +
                "Tên: " + tenTheLoai,
                "Thành công", 
                JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Có lỗi xảy ra khi thêm thể loại!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateCategory() {
        String maTheLoai = view.getTxtMaTheLoai().getText().trim();
        if (maTheLoai.isEmpty() || maTheLoai.equals("(Tự động)")) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn thể loại cần sửa!", 
                "Cảnh báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String tenTheLoai = view.getTxtTenTheLoai().getText().trim();
        String moTa = view.getTxtMoTa().getText().trim();
        
        if (tenTheLoai.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập tên thể loại!", 
                "Cảnh báo", 
                JOptionPane.WARNING_MESSAGE);
            view.getTxtTenTheLoai().requestFocus();
            return;
        }
        
        Category cat = new Category();
        cat.setId(Integer.parseInt(maTheLoai));
        cat.setName(tenTheLoai);
        cat.setDescription(moTa.isEmpty() ? null : moTa);
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Xác nhận cập nhật thể loại:\n" +
            "Mã: " + maTheLoai + "\n" +
            "Tên mới: " + tenTheLoai,
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.updateCategory(cat)) {
                JOptionPane.showMessageDialog(view, 
                    "Cập nhật thể loại thành công!",
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Có lỗi xảy ra khi cập nhật!",
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteCategory() {
        String maTheLoai = view.getTxtMaTheLoai().getText().trim();
        if (maTheLoai.isEmpty() || maTheLoai.equals("(Tự động)")) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn thể loại cần xóa!", 
                "Cảnh báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String tenTheLoai = view.getTxtTenTheLoai().getText().trim();
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Cảnh báo: Xóa thể loại có thể ảnh hưởng đến các sách đang liên kết!\n\n" +
            "Thể loại: " + tenTheLoai + "\n\n" +
            "Bạn có chắc chắn muốn xóa?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteCategory(Integer.parseInt(maTheLoai))) {
                JOptionPane.showMessageDialog(view, 
                    "Xóa thể loại thành công!",
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Xóa thể loại thất bại!\n" +
                    "Có thể thể loại đang được sử dụng.",
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void selectRow() {
        int selectedRow = view.getTblCategory().getSelectedRow();
        if (selectedRow >= 0) {
            view.getTxtMaTheLoai().setText(view.getTblCategory().getValueAt(selectedRow, 0).toString());
            view.getTxtTenTheLoai().setText(view.getTblCategory().getValueAt(selectedRow, 1).toString());
            
            Object moTa = view.getTblCategory().getValueAt(selectedRow, 2);
            view.getTxtMoTa().setText(moTa == null ? "" : moTa.toString());
        }
    }
    
    private void clearForm() {
        view.getTxtMaTheLoai().setText("(Tự động)");
        view.getTxtTenTheLoai().setText("");
        view.getTxtMoTa().setText("");
        view.getTblCategory().clearSelection();
        view.getTxtTenTheLoai().requestFocus();
        updateButtonStates(false);
    }
    
    private void performSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Vui lòng nhập từ khóa tìm kiếm!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<Category> results = dao.searchCategories(keyword);
        displayCategories(results);
    }
    
    private void updateButtonStates(boolean isRowSelected) {
        view.getBtnEdit().setEnabled(isRowSelected);
        view.getBtnDelete().setEnabled(isRowSelected);
    }
}

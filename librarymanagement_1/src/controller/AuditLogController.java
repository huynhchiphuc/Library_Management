/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.AuditLogDAO;
import dao.UserDAO;
import model.AuditLog;
import model.User;
import view.AuditLogForm;

/**
 *
 * @author ASUS
 */
public class AuditLogController {
    
    private AuditLogForm view;
    private AuditLogDAO dao;
    private UserDAO userDAO;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public AuditLogController(AuditLogForm view) {
        this.view = view;
        this.dao = new AuditLogDAO();
        this.userDAO = new UserDAO();
        
        initEvents();
        loadUsers();
        loadData();
    }
    
    private void initEvents() {
        view.getBtnRefresh().addActionListener(e -> loadData());
        view.getBtnFilter().addActionListener(e -> filterData());
        view.getBtnSearch().addActionListener(e -> performSearch());
    }
    
    private void loadUsers() {
        view.getCboUser().removeAllItems();
        view.getCboUser().addItem("-- Tất cả --");
        
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            view.getCboUser().addItem(user.getFullName() + " (" + user.getUsername() + ")");
        }
    }
    
    private void loadData() {
        List<AuditLog> list = dao.getAllLogs();
        displayLogs(list);
    }
    
    private void displayLogs(List<AuditLog> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTblAuditLog().getModel();
        model.setRowCount(0);
        
        for (AuditLog log : list) {
            model.addRow(new Object[]{
                log.getMaNhatKy(),
                log.getTenNguoiDung(),
                log.getHanhDong(),
                log.getTenBang(),
                log.getMaBanGhi(),
                log.getMoTaChiTiet(),
                log.getDiaChiIP(),
                sdf.format(log.getThoiGian())
            });
        }
        
        view.getLblResultCount().setText("Tổng: " + list.size() + " bản ghi");
    }
    
    private void filterData() {
        String userSelection = (String) view.getCboUser().getSelectedItem();
        String actionSelection = (String) view.getCboAction().getSelectedItem();
        String tableSelection = (String) view.getCboTable().getSelectedItem();
        
        List<AuditLog> allLogs = dao.getAllLogs();
        List<AuditLog> filteredLogs = new java.util.ArrayList<>();
        
        for (AuditLog log : allLogs) {
            boolean matchUser = userSelection.equals("-- Tất cả --") || 
                               (log.getTenNguoiDung() != null && userSelection.contains(log.getTenNguoiDung()));
            boolean matchAction = actionSelection.equals("-- Tất cả --") || 
                                 log.getHanhDong().equals(actionSelection);
            boolean matchTable = tableSelection.equals("-- Tất cả --") || 
                                log.getTenBang().equals(tableSelection);
            
            if (matchUser && matchAction && matchTable) {
                filteredLogs.add(log);
            }
        }
        
        displayLogs(filteredLogs);
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
        
        List<AuditLog> allLogs = dao.getAllLogs();
        List<AuditLog> searchResults = new java.util.ArrayList<>();
        
        for (AuditLog log : allLogs) {
            if ((log.getTenNguoiDung() != null && log.getTenNguoiDung().toLowerCase().contains(keyword.toLowerCase())) ||
                (log.getHanhDong() != null && log.getHanhDong().toLowerCase().contains(keyword.toLowerCase())) ||
                (log.getTenBang() != null && log.getTenBang().toLowerCase().contains(keyword.toLowerCase())) ||
                (log.getMoTaChiTiet() != null && log.getMoTaChiTiet().toLowerCase().contains(keyword.toLowerCase()))) {
                searchResults.add(log);
            }
        }
        
        displayLogs(searchResults);
    }
}

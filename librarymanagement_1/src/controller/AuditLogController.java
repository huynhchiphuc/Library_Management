/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.AuditLogDAO;
import dao.UserDAO;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
        DefaultTableModel model = (DefaultTableModel) view.getTblAuditLog().getModel();
        model.setRowCount(0);
        
        List<AuditLog> list = dao.getAllLogs();
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
    }
    
    private void filterData() {
        // TODO: Implement filter logic based on combo selections
        // For now, just reload all data
        loadData();
        JOptionPane.showMessageDialog(view, "Chức năng lọc đang được phát triển!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}

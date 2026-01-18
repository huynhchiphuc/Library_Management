/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ReaderDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Reader;
import view.ReaderForm;

/**
 *
 * @author ASUS
 */
public class ReaderController {
    
    private final ReaderForm view;
    private final ReaderDAO dao;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ReaderController(ReaderForm view) {
        this.view = view;
        this.dao = new ReaderDAO();
        initController();
    }
    
    private void initController() {
        loadData();
        
        view.getBtnAdd().addActionListener(e -> addReader());
        view.getBtnEdit().addActionListener(e -> updateReader());
        view.getBtnDelete().addActionListener(e -> deleteReader());
        view.getBtnReset().addActionListener(e -> clearForm());
        view.getBtnSearch().addActionListener(e -> searchReader());
        
        view.getTblReader().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectRow();
            }
        });
    }
    
    private void loadData() {
        List<Reader> list = dao.getAllReaders();
        DefaultTableModel model = (DefaultTableModel) view.getTblReader().getModel();
        model.setRowCount(0);
        
        // Define columns if not set
        if (model.getColumnCount() == 0) {
            model.setColumnIdentifiers(new String[]{"Mã thẻ", "Họ tên", "Email", "SĐT", "Địa chỉ", "Ngày hết hạn", "Trạng thái"});
        }
        
        for (Reader r : list) {
            model.addRow(new Object[]{
                r.getMaThe(),
                r.getHoTen(),
                r.getEmail(),
                r.getSoDienThoai(),
                r.getDiaChi(),
                dateFormat.format(r.getNgayHetHan()),
                r.isBiKhoa() ? "Đang khóa" : "Hoạt động"
            });
        }
    }
    
    private void selectRow() {
        int row = view.getTblReader().getSelectedRow();
        if (row >= 0) {
            view.getTxtMaThe().setText(view.getTblReader().getValueAt(row, 0).toString());
            view.getTxtHoTen().setText(view.getTblReader().getValueAt(row, 1).toString());
            // Check for null values
            Object email = view.getTblReader().getValueAt(row, 2);
            view.getTxtEmail().setText(email != null ? email.toString() : "");
            
            Object sdt = view.getTblReader().getValueAt(row, 3);
            view.getTxtSDT().setText(sdt != null ? sdt.toString() : "");
            
            Object diachi = view.getTblReader().getValueAt(row, 4);
            view.getTxtDiaChi().setText(diachi != null ? diachi.toString() : "");
            
            view.getTxtNgayHetHan().setText(view.getTblReader().getValueAt(row, 5).toString());
            
            String status = view.getTblReader().getValueAt(row, 6).toString();
            view.getChkBiKhoa().setSelected(status.equals("Đang khóa"));
        }
    }
    
    private void clearForm() {
        view.getTxtMaThe().setText("");
        view.getTxtHoTen().setText("");
        view.getTxtEmail().setText("");
        view.getTxtSDT().setText("");
        view.getTxtDiaChi().setText("");
        view.getTxtNgayHetHan().setText("");
        view.getChkBiKhoa().setSelected(false);
        view.getTblReader().clearSelection();
        loadData();
    }
    
    private Reader getModelFromForm() {
        String maThe = view.getTxtMaThe().getText().trim();
        String hoTen = view.getTxtHoTen().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        String sdt = view.getTxtSDT().getText().trim();
        String diaChi = view.getTxtDiaChi().getText().trim();
        String ngayHetHanStr = view.getTxtNgayHetHan().getText().trim();
        boolean biKhoa = view.getChkBiKhoa().isSelected();
        
        if (maThe.isEmpty() || hoTen.isEmpty() || ngayHetHanStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin bắt buộc!");
            return null;
        }
        
        Date ngayHetHan;
        try {
            ngayHetHan = dateFormat.parse(ngayHetHanStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(view, "Định dạng ngày không hợp lệ (dd/MM/yyyy)!");
            return null;
        }
        
        Reader r = new Reader();
        r.setMaThe(maThe);
        r.setHoTen(hoTen);
        r.setEmail(email);
        r.setSoDienThoai(sdt);
        r.setDiaChi(diaChi);
        r.setNgayHetHan(ngayHetHan);
        r.setBiKhoa(biKhoa);
        
        return r;
    }
    
    private void addReader() {
        Reader r = getModelFromForm();
        if (r == null) return;
        
        if (dao.isMaTheExist(r.getMaThe())) {
            JOptionPane.showMessageDialog(view, "Mã thẻ này đã tồn tại!");
            return;
        }
        
        if (dao.addReader(r)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            clearForm();
        } else {
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm!");
        }
    }
    
    private void updateReader() {
        Reader r = getModelFromForm();
        if (r == null) return;
        
        if (JOptionPane.showConfirmDialog(view, "Bạn có chắn chắn muốn sửa?") == JOptionPane.YES_OPTION) {
             if (dao.updateReader(r)) {
                JOptionPane.showMessageDialog(view, "Sửa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi sửa!");
            }
        }
    }
    
    private void deleteReader() {
        String maThe = view.getTxtMaThe().getText().trim();
        if (maThe.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn hoặc nhập Mã thẻ cần xóa!");
            return;
        }
        
        if (JOptionPane.showConfirmDialog(view, "Bạn có chắn chắn muốn xóa bạn đọc [" + maThe + "] không?") == JOptionPane.YES_OPTION) {
             if (dao.deleteReader(maThe)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi xóa!");
            }
        }
    }
    
    private void searchReader() {
        String keyword = JOptionPane.showInputDialog(view, "Nhập Mã thẻ hoặc Tên cần tìm:");
        if (keyword != null && !keyword.isEmpty()) {
            List<Reader> list = dao.searchReader(keyword);
            DefaultTableModel model = (DefaultTableModel) view.getTblReader().getModel();
            model.setRowCount(0);
            for (Reader r : list) {
                model.addRow(new Object[]{
                    r.getMaThe(),
                    r.getHoTen(),
                    r.getEmail(),
                    r.getSoDienThoai(),
                    r.getDiaChi(),
                    dateFormat.format(r.getNgayHetHan()),
                    r.isBiKhoa() ? "Đang khóa" : "Hoạt động"
                });
            }
        } else {
            loadData();
        }
    }
    
}

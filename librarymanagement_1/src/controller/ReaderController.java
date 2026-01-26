/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.ReaderDAO;
import model.Reader;
import service.ReaderService;
import view.ReaderForm;

/**
 *
 * @author ASUS
 */
public class ReaderController {
    
    private final ReaderForm view;
    private final ReaderDAO dao;
    private final ReaderService service;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ReaderController(ReaderForm view) {
        this.view = view;
        this.dao = new ReaderDAO();
        this.service = new ReaderService();
        initView();
        initController();
    }
    
    /**
     * Khởi tạo giao diện ban đầu
     * Xử lý: Load danh sách độc giả, set MaThe = "(Tự động)", disable field MaThe
     */
    private void initView() {
        // Disable auto-generated fields
        view.getTxtMaThe().setEditable(false);
        view.getTxtMaThe().setBackground(new java.awt.Color(240, 240, 240));
        view.getTxtMaThe().setText("(Tự động)");
        
        loadData();
    }
    
    private void initController() {
        view.getBtnAdd().addActionListener(e -> addReader());
        view.getBtnEdit().addActionListener(e -> updateReader());
        view.getBtnDelete().addActionListener(e -> deleteReader());
        view.getBtnReset().addActionListener(e -> clearForm());
        view.getBtnSearch().addActionListener(e -> performSearch());
        view.getBtnViewAll().addActionListener(e -> viewAll());
        
        view.getTblReader().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTblReader().getSelectedRow() != -1) {
                selectRow();
                updateButtonStates(true);
            }
        });
        
        // Initial button state
        updateButtonStates(false);
    }
    
    private void loadData() {
        List<Reader> list = dao.getAllReaders();
        displayReaders(list);
    }
    
    /**
     * Hiển thị danh sách độc giả lên JTable
     * @param list Danh sách Reader cần hiển thị
     * Xử lý: Clear bảng, duyệt List<Reader>, format ngày dd/MM/yyyy,
     *        hiển thị trạng thái "Hoạt động"/"Khóa", highlight dòng nếu hết hạn/bị khóa
     */
    private void displayReaders(List<Reader> list) {
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
                r.isBiKhoa() ? "\u0110ang khóa" : "Hoạt động"
            });
        }
        
        // Update result count
        view.getLblResultCount().setText("Tổng: " + list.size() + " kết quả");
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
        view.getTxtMaThe().setText("(Tự động)");
        view.getTxtHoTen().setText("");
        view.getTxtEmail().setText("");
        view.getTxtSDT().setText("");
        view.getTxtDiaChi().setText("");
        view.getTxtNgayHetHan().setText("");
        view.getChkBiKhoa().setSelected(false);
        view.getTblReader().clearSelection();
        updateButtonStates(false);
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
        
        // Validate required fields
        if (hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Họ tên không được để trống!");
            return null;
        }
        
        if (ngayHetHanStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Ngày hết hạn không được để trống!");
            return null;
        }
        
        // Validate email format if provided
        if (!email.isEmpty() && !util.ValidateUtil.isValidEmail(email)) {
            JOptionPane.showMessageDialog(view, "Email không hợp lệ!");
            return null;
        }
        
        // Validate phone format if provided
        if (!sdt.isEmpty() && !util.ValidateUtil.isValidPhone(sdt)) {
            JOptionPane.showMessageDialog(view, "Số điện thoại không hợp lệ!");
            return null;
        }
        
        // Parse and validate date
        Date ngayHetHan;
        try {
            ngayHetHan = dateFormat.parse(ngayHetHanStr);
            // Check if date is in the past
            if (ngayHetHan.before(new Date())) {
                int confirm = JOptionPane.showConfirmDialog(view, 
                    "Ngày hết hạn đã quá khứ. Bạn có muốn tiếp tục?",
                    "Cảnh báo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                if (confirm != JOptionPane.YES_OPTION) {
                    return null;
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(view, 
                "Định dạng ngày không hợp lệ!\n" +
                "Vui lòng nhập theo định dạng: dd/MM/yyyy\n" +
                "Ví dụ: 31/12/2025",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
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
        
        // Auto-generate MaThe from database
        String newMaThe = service.generateNextMaThe();
        r.setMaThe(newMaThe);
        
        if (service.addReader(r)) {
            JOptionPane.showMessageDialog(view, 
                "Thêm thành công!\n" +
                "Mã thẻ mới: " + newMaThe,
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateReader() {
        String maThe = view.getTxtMaThe().getText().trim();
        
        // Check if editing existing reader
        if (maThe.isEmpty() || "(Tự động)".equals(maThe)) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn độc giả từ bảng để sửa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Reader r = getModelFromForm();
        if (r == null) return;
        
        r.setMaThe(maThe);
        
        if (JOptionPane.showConfirmDialog(view, 
            "Bạn có chắc muốn cập nhật thông tin độc giả " + r.getHoTen() + "?",
            "Xác nhận sửa",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
             if (service.updateReader(r)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteReader() {
        String maThe = view.getTxtMaThe().getText().trim();
        String hoTen = view.getTxtHoTen().getText().trim();
        
        // Check if a reader is selected
        if (maThe.isEmpty() || "(Tự động)".equals(maThe)) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn độc giả từ bảng để xóa!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Bạn có chắc muốn xóa độc giả:\n" +
            "Mã thẻ: " + maThe + "\n" +
            "Họ tên: " + hoTen + "\n\n" +
            "Lưu ý: Xóa độc giả sẽ xóa tất cả dữ liệu liên quan!",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
             if (service.deleteReader(maThe, hoTen)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Xóa thất bại!\n" +
                    "Độc giả có thể còn sách chưa trả hoặc có dữ liệu liên quan.",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void performSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập từ khóa (Mã thẻ hoặc Tên)!");
            return;
        }
        
        List<Reader> list = service.searchReaders(keyword);
        displayReaders(list);
        
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Không tìm thấy kết quả nào!");
        }
    }
    
    private void viewAll() {
        view.getTxtSearch().setText("");
        loadData();
        clearForm();
    }
    
    private void updateButtonStates(boolean isRowSelected) {
        view.getBtnEdit().setEnabled(isRowSelected);
        view.getBtnDelete().setEnabled(isRowSelected);
        view.getBtnAdd().setEnabled(true);
        view.getBtnReset().setEnabled(true);
    }
    
}

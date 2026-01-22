/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Penalty;
import model.Reader;
import service.PenaltyService;
import service.ReaderService;
import view.PenaltyForm;

/**
 *
 * @author ASUS
 */
public class PenaltyController {
    
    private PenaltyForm view;
    private PenaltyService penaltyService;
    private ReaderService readerService;
    
    private Reader currentReader;

    public PenaltyController(PenaltyForm view) {
        this.view = view;
        this.penaltyService = new PenaltyService();
        this.readerService = new ReaderService();
        
        initView();
        initEvents();
        loadData();
    }
    
    private void initView() {
        // Setup button states
        updateButtonStates(false);
    }
    
    private void initEvents() {
        view.getBtnCheckReader().addActionListener(e -> checkReader());
        view.getBtnAddPenalty().addActionListener(e -> addPenalty());
        view.getBtnPay().addActionListener(e -> payPenalty());
        view.getBtnRefresh().addActionListener(e -> loadData());
        view.getBtnSearch().addActionListener(e -> performSearch());
        view.getBtnViewAll().addActionListener(e -> loadData());
        
        // Table selection listener
        view.getTblPenalty().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates(view.getTblPenalty().getSelectedRow() != -1);
            }
        });
    }
    
    private void checkReader() {
        String maThe = view.getTxtMaThe().getText().trim();
        if (maThe.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập mã thẻ độc giả!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Reader r = readerService.findReaderByCardId(maThe);
        if (r != null) {
            currentReader = r;
            
            // Calculate and display total unpaid penalty
            double totalUnpaid = penaltyService.getTotalUnpaidPenalty(r.getMaDocGia());
            String debtInfo = String.format(" - Tổng nợ: %,.0f VNĐ", totalUnpaid);
            view.getLblTenDocGia().setText(r.getHoTen() + debtInfo);
            
            if (r.isBiKhoa() || totalUnpaid > 0) {
                view.getLblTenDocGia().setForeground(java.awt.Color.RED);
            } else {
                view.getLblTenDocGia().setForeground(new java.awt.Color(0, 153, 0)); // Green
            }
            
            // Show reader info
            JOptionPane.showMessageDialog(view, 
                "Tìm thấy độc giả:\n" +
                "Họ tên: " + r.getHoTen() + "\n" +
                "SĐT: " + r.getSoDienThoai() + "\n" +
                "Tổng tiền chưa đóng: " + String.format("%,.0f VNĐ", totalUnpaid) + "\n" +
                "Trạng thái: " + (r.isBiKhoa() ? "ĐANG BỊ KHÓA" : "Bình thường"),
                "Thông tin độc giả",
                totalUnpaid > 0 || r.isBiKhoa() ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
        } else {
            currentReader = null;
            view.getLblTenDocGia().setText("Không tìm thấy!");
            view.getLblTenDocGia().setForeground(java.awt.Color.RED);
            JOptionPane.showMessageDialog(view, 
                "Không tìm thấy độc giả với mã thẻ: " + maThe,
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadData() {
        List<Penalty> list = penaltyService.getAllPenalties();
        displayPenalties(list);
    }
    
    private void displayPenalties(List<Penalty> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTblPenalty().getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        for (Penalty p : list) {
            model.addRow(new Object[]{
                p.getMaPhieuPhat(),
                p.getTenDocGia(),
                p.getLyDo(),
                String.format("%,.0f VNĐ", p.getSoTien()),
                p.isDaDongTien() ? "Đã đóng" : "Chưa đóng",
                sdf.format(p.getNgayTao())
            });
        }
        
        // Update result count
        view.getLblResultCount().setText("Tổng: " + list.size() + " bản ghi");
    }
    
    private void addPenalty() {
        if (currentReader == null) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng kiểm tra độc giả trước!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String lyDo = view.getTxtLyDo().getText().trim();
        String soTienStr = view.getTxtSoTien().getText().trim();
        
        if (lyDo.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập lý do phạt!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            view.getTxtLyDo().requestFocus();
            return;
        }
        
        if (soTienStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập số tiền phạt!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            view.getTxtSoTien().requestFocus();
            return;
        }
        
        try {
            double soTien = Double.parseDouble(soTienStr.replace(",", ""));
            
            if (soTien <= 0) {
                JOptionPane.showMessageDialog(view, 
                    "Số tiền phạt phải lớn hơn 0!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Confirm
            int confirm = JOptionPane.showConfirmDialog(view,
                "Xác nhận tạo phiếu phạt:\n" +
                "Độc giả: " + currentReader.getHoTen() + "\n" +
                "Lý do: " + lyDo + "\n" +
                "Số tiền: " + String.format("%,.0f VNĐ", soTien),
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            
            boolean success = penaltyService.createPenalty(currentReader.getMaDocGia(), lyDo, soTien);
            if (success) {
                JOptionPane.showMessageDialog(view, 
                    "Tạo phiếu phạt thành công!\n" +
                    "Độc giả: " + currentReader.getHoTen() + "\n" +
                    "Số tiền: " + String.format("%,.0f VNĐ", soTien),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
                view.getTxtLyDo().setText("");
                view.getTxtSoTien().setText("");
                view.getTxtLyDo().requestFocus();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Có lỗi xảy ra khi tạo phiếu phạt!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, 
                "Số tiền không hợp lệ!\n" +
                "Vui lòng nhập số hợp lệ.",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void payPenalty() {
        int selectedRow = view.getTblPenalty().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn phiếu phạt để đóng tiền!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String status = view.getTblPenalty().getValueAt(selectedRow, 4).toString();
        if ("Đã đóng".equals(status)) {
            JOptionPane.showMessageDialog(view, 
                "Phiếu này đã đóng tiền rồi!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String tenDG = view.getTblPenalty().getValueAt(selectedRow, 1).toString();
        String soTien = view.getTblPenalty().getValueAt(selectedRow, 3).toString();
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Xác nhận đã thu tiền:\n" +
            "Độc giả: " + tenDG + "\n" +
            "Số tiền: " + soTien,
            "Xác nhận đóng tiền", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(view.getTblPenalty().getValueAt(selectedRow, 0).toString());
            boolean success = penaltyService.payPenalty(id);
            if (success) {
                JOptionPane.showMessageDialog(view, 
                    "Cập nhật thành công!\n" +
                    "Phiếu phạt đã được đánh dấu là đã đóng tiền.",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Có lỗi xảy ra khi cập nhật!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void performSearch() {
        String searchText = view.getTxtSearch().getText().trim();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng nhập từ khóa tìm kiếm!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<Penalty> results = penaltyService.searchPenalties(searchText);
        displayPenalties(results);
    }
    
    private void updateButtonStates(boolean isRowSelected) {
        view.getBtnPay().setEnabled(isRowSelected);
    }
}

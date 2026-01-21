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
        
        initEvents();
        loadData();
    }
    
    private void initEvents() {
        view.getBtnCheckReader().addActionListener(e -> checkReader());
        view.getBtnAddPenalty().addActionListener(e -> addPenalty());
        view.getBtnPay().addActionListener(e -> payPenalty());
        view.getBtnRefresh().addActionListener(e -> loadData());
    }
    
    private void checkReader() {
        String maThe = view.getTxtMaThe().getText().trim();
        if (maThe.isEmpty()) {
             JOptionPane.showMessageDialog(view, "Vui lòng nhập mã thẻ!");
             return;
        }
        
        Reader r = readerService.findReaderByCardId(maThe);
        if (r != null) {
            currentReader = r;
            view.getLblTenDocGia().setText(r.getHoTen());
            
            // Calculate and display total unpaid penalty
            double totalUnpaid = penaltyService.getTotalUnpaidPenalty(r.getMaDocGia());
            String debtInfo = String.format(" - Tổng nợ: %,.0f VNĐ", totalUnpaid);
            view.getLblTenDocGia().setText(r.getHoTen() + debtInfo);
            
            if (r.isBiKhoa() || totalUnpaid > 0) {
                view.getLblTenDocGia().setForeground(java.awt.Color.RED);
            } else {
                view.getLblTenDocGia().setForeground(new java.awt.Color(0, 102, 204));
            }
        } else {
            currentReader = null;
            view.getLblTenDocGia().setText("Không tìm thấy!");
            view.getLblTenDocGia().setForeground(java.awt.Color.RED);
        }
    }
    
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) view.getTblPenalty().getModel();
        model.setRowCount(0);
        
        List<Penalty> list = penaltyService.getAllPenalties();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        for (Penalty p : list) {
            model.addRow(new Object[]{
                p.getMaPhieuPhat(),
                p.getTenDocGia(),
                p.getLyDo(),
                String.format("%,.0f", p.getSoTien()),
                p.isDaDongTien() ? "Đã đóng" : "Chưa đóng",
                sdf.format(p.getNgayTao())
            });
        }
    }
    
    private void addPenalty() {
        if (currentReader == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn độc giả trước!");
            return;
        }
        String lyDo = view.getTxtLyDo().getText().trim();
        String soTienStr = view.getTxtSoTien().getText().trim();
        
        if (lyDo.isEmpty() || soTienStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập lý do và số tiền!");
            return;
        }
        
        try {
            double soTien = Double.parseDouble(soTienStr);
            boolean success = penaltyService.createPenalty(currentReader.getMaDocGia(), lyDo, soTien);
            if (success) {
                JOptionPane.showMessageDialog(view, "Tạo phiếu phạt thành công!");
                loadData();
                view.getTxtLyDo().setText("");
                view.getTxtSoTien().setText("");
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi tạo phiếu phạt!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Số tiền không hợp lệ!");
        }
    }
    
    private void payPenalty() {
        int selectedRow = view.getTblPenalty().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn phiếu phạt để đóng tiền!");
            return;
        }
        
        String status = view.getTblPenalty().getValueAt(selectedRow, 4).toString();
        if ("Đã đóng".equals(status)) {
            JOptionPane.showMessageDialog(view, "Phiếu này đã đóng tiền rồi!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, "Xác nhận đã thu tiền cho phiếu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(view.getTblPenalty().getValueAt(selectedRow, 0).toString());
            boolean success = penaltyService.payPenalty(id);
            if (success) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi cập nhật!");
            }
        }
    }
}

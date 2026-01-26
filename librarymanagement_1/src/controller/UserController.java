/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.UserDAO;
import model.User;
import util.Constants;
import util.PasswordUtil;
import util.ValidateUtil;
import view.UserForm;

/**
 *
 * @author ASUS
 */
public class UserController {
    
    private final UserForm view;
    private final UserDAO userDAO;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETIME_FORMAT);
    
    public UserController(UserForm view) {
        this.view = view;
        this.userDAO = new UserDAO();
        
        initController();
        loadData();
    }
    
    private void initController() {
        view.getBtnAdd().addActionListener(e -> addUser());
        view.getBtnEdit().addActionListener(e -> updateUser());
        view.getBtnDelete().addActionListener(e -> deleteUser());
        view.getBtnReset().addActionListener(e -> clearForm());
        view.getBtnResetPassword().addActionListener(e -> resetPassword());
        view.getBtnSearch2().addActionListener(e -> searchUser());
        view.getBtnViewAll().addActionListener(e -> loadData());
        
        view.getTblUser().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTblUser().getSelectedRow() != -1) {
                fillForm();
            }
        });
    }
    
    /**
     * Xử lý tìm kiếm người dùng
     * Không có tham số đầu vào (lấy keyword từ txtSearch)
     * Không có giá trị trả về (cập nhật JTable trực tiếp)
     * Xử lý:
     * 1. Lấy keyword từ txtSearch.getText()
     * 2. Validate keyword không rỗng
     * 3. Gọi userDAO.searchUser(keyword)
     * 4. Hiển thị kết quả lên JTable
     * 5. Cập nhật lblResultCount: "Tổng: X kết quả"
     */
    private void searchUser() {
        String keyword = view.getTxtSearch().getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<User> users = userDAO.searchUser(keyword);
        DefaultTableModel model = (DefaultTableModel) view.getTblUser().getModel();
        model.setRowCount(0);
        
        for (User u : users) {
            Object[] row = {
                u.getId(),
                u.getUsername(),
                u.getFullName(),
                u.getEmail(),
                u.getPhoneNumber(),
                u.getRoleId() == Constants.ROLE_ADMIN ? "Admin" : "Thủ thư",
                u.isActive() ? "Hoạt động" : "Khóa",
                dateFormat.format(u.getCreatedAt())
            };
            model.addRow(row);
        }
        
        view.getLblResultCount().setText("Tổng: " + users.size() + " kết quả");
    }
    
    /**
     * Load danh sách người dùng lên bảng
     * Không có tham số đầu vào
     * Không có giá trị trả về
     * Xử lý:
     * 1. Gọi userDAO.getAllUsers() lấy danh sách tất cả user
     * 2. Clear JTable (setRowCount(0))
     * 3. Duyệt qua List<User>, thêm từng dòng vào JTable
     * 4. Format: Mã ND, Tên ĐN, Họ tên, Email, SĐT, Vai trò, Trạng thái, Ngày tạo
     * 5. Cập nhật lblResultCount: "Tổng: X kết quả"
     */
    private void loadData() {
        List<User> users = userDAO.getAllUsers();
        DefaultTableModel model = (DefaultTableModel) view.getTblUser().getModel();
        model.setRowCount(0);
        
        for (User u : users) {
            Object[] row = {
                u.getId(),
                u.getUsername(),
                u.getFullName(),
                u.getEmail(),
                u.getPhoneNumber(),
                u.getRoleId() == Constants.ROLE_ADMIN ? "Admin" : "Thủ thư",
                u.isActive() ? "Hoạt động" : "Khóa",
                dateFormat.format(u.getCreatedAt())
            };
            model.addRow(row);
        }
        
        view.getLblResultCount().setText("Tổng: " + users.size() + " kết quả");
    }
    
    private void fillForm() {
        int row = view.getTblUser().getSelectedRow();
        if (row >= 0) {
            view.getTxtMaNguoiDung().setText(view.getTblUser().getValueAt(row, 0).toString());
            view.getTxtTenDangNhap().setText(view.getTblUser().getValueAt(row, 1).toString());
            view.getTxtHoTen().setText(view.getTblUser().getValueAt(row, 2).toString());
            
            Object email = view.getTblUser().getValueAt(row, 3);
            view.getTxtEmail().setText(email != null ? email.toString() : "");
            
            Object phone = view.getTblUser().getValueAt(row, 4);
            view.getTxtSoDienThoai().setText(phone != null ? phone.toString() : "");
            
            String role = view.getTblUser().getValueAt(row, 5).toString();
            view.getCboVaiTro().setSelectedIndex(role.equals("Admin") ? 0 : 1);
            
            String status = view.getTblUser().getValueAt(row, 6).toString();
            view.getChkDangHoatDong().setSelected(status.equals("Hoạt động"));
            
            // Clear password field when editing
            view.getTxtMatKhau().setText("");
        }
    }
    
    /**
     * Xử lý thêm người dùng mới
     * Không có tham số đầu vào (lấy dữ liệu từ form)
     * Không có giá trị trả về (hiển thị thông báo qua JOptionPane)
     * Xử lý:
     * 1. Validate input: username, password, fullName không được rỗng
     * 2. Validate email hợp lệ (nếu có nhập)
     * 3. Kiểm tra username đã tồn tại chưa (gọi userDAO.isUsernameExist)
     * 4. Hash password (gọi PasswordUtil.hashPassword)
     * 5. Tạo User object và set các thuộc tính
     * 6. Gọi userDAO.insertUser() để thêm vào database
     * 7. Nếu thành công: refresh bảng, clear form, hiển thị thông báo
     * 8. Nếu thất bại: hiển thị thông báo lỗi
     */
    private void addUser() {
        String username = view.getTxtTenDangNhap().getText().trim();
        String password = new String(view.getTxtMatKhau().getPassword()).trim();
        String fullName = view.getTxtHoTen().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        String phone = view.getTxtSoDienThoai().getText().trim();
        
        // Validate
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin bắt buộc (*)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!ValidateUtil.isValidUsername(username)) {
            JOptionPane.showMessageDialog(view, "Tên đăng nhập phải từ 3-20 ký tự, chỉ chứa chữ, số và gạch dưới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!ValidateUtil.isValidPassword(password)) {
            JOptionPane.showMessageDialog(view, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!email.isEmpty() && !ValidateUtil.isValidEmail(email)) {
            JOptionPane.showMessageDialog(view, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!phone.isEmpty() && !ValidateUtil.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(view, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check username exists
        if (userDAO.isUsernameExist(username)) {
            JOptionPane.showMessageDialog(view, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create user
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setFullName(fullName);
        user.setEmail(email.isEmpty() ? null : email);
        user.setPhoneNumber(phone.isEmpty() ? null : phone);
        user.setRoleId(view.getCboVaiTro().getSelectedIndex() == 0 ? Constants.ROLE_ADMIN : Constants.ROLE_LIBRARIAN);
        user.setActive(view.getChkDangHoatDong().isSelected());
        
        if (userDAO.insertUser(user)) {
            JOptionPane.showMessageDialog(view, "Thêm người dùng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm người dùng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Xử lý cập nhật thông tin người dùng
     * Không có tham số đầu vào (lấy dữ liệu từ form)
     * Không có giá trị trả về (hiển thị thông báo qua JOptionPane)
     * Xử lý:
     * 1. Kiểm tra đã chọn user chưa (MaNguoiDung != rỗng)
     * 2. Validate: fullName, email, roleId
     * 3. Lấy User cũ từ database
     * 4. Cập nhật các thuộc tính mới (không cập nhật password ở đây)
     * 5. Gọi userDAO.updateUser()
     * 6. Nếu thành công: refresh bảng, clear form
     * 7. Nếu thất bại: hiển thị thông báo lỗi
     */
    private void updateUser() {
        String userId = view.getTxtMaNguoiDung().getText().trim();
        if (userId.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn người dùng cần sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String fullName = view.getTxtHoTen().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        String phone = view.getTxtSoDienThoai().getText().trim();
        
        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Họ tên không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!email.isEmpty() && !ValidateUtil.isValidEmail(email)) {
            JOptionPane.showMessageDialog(view, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!phone.isEmpty() && !ValidateUtil.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(view, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User user = new User();
        user.setId(Integer.parseInt(userId));
        user.setFullName(fullName);
        user.setEmail(email.isEmpty() ? null : email);
        user.setPhoneNumber(phone.isEmpty() ? null : phone);
        user.setRoleId(view.getCboVaiTro().getSelectedIndex() == 0 ? Constants.ROLE_ADMIN : Constants.ROLE_LIBRARIAN);
        user.setActive(view.getChkDangHoatDong().isSelected());
        
        if (userDAO.updateUser(user)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteUser() {
        String userId = view.getTxtMaNguoiDung().getText().trim();
        if (userId.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn người dùng cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa người dùng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (userDAO.deleteUser(Integer.parseInt(userId))) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void resetPassword() {
        String userId = view.getTxtMaNguoiDung().getText().trim();
        if (userId.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn người dùng cần đặt lại mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String newPassword = JOptionPane.showInputDialog(view, "Nhập mật khẩu mới (mặc định: 123456):");
        if (newPassword == null) { // Cancelled
            return;
        }
        
        if (newPassword.isEmpty()) {
            newPassword = "123456";
        }
        
        if (!ValidateUtil.isValidPassword(newPassword)) {
            JOptionPane.showMessageDialog(view, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String hashedPassword = PasswordUtil.hashPassword(newPassword);
        
        if (userDAO.resetPassword(Integer.parseInt(userId), hashedPassword)) {
            JOptionPane.showMessageDialog(view, "Đặt lại mật khẩu thành công!\nMật khẩu mới: " + newPassword, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Đặt lại mật khẩu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        view.getTxtMaNguoiDung().setText("");
        view.getTxtTenDangNhap().setText("");
        view.getTxtMatKhau().setText("");
        view.getTxtHoTen().setText("");
        view.getTxtEmail().setText("");
        view.getTxtSoDienThoai().setText("");
        view.getCboVaiTro().setSelectedIndex(1); // Default: Thủ thư
        view.getChkDangHoatDong().setSelected(true);
        view.getTblUser().clearSelection();
    }
}

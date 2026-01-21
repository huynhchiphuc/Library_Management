/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 * Lớp chứa các hằng số sử dụng trong hệ thống
 * @author ASUS
 */
public class Constants {
    
    // ========== TRẠNG THÁI SÁCH ==========
    public static final int BOOK_STATUS_AVAILABLE = 1;  // Sẵn sàng
    public static final int BOOK_STATUS_BORROWED = 2;   // Đang được mượn
    public static final int BOOK_STATUS_LOST = 3;       // Mất
    public static final int BOOK_STATUS_DAMAGED = 4;    // Hỏng
    
    // ========== QUY ĐỊNH MƯỢN SÁCH ==========
    public static final int DEFAULT_BORROW_DAYS = 14;         // Số ngày mượn mặc định
    public static final int MAX_BOOKS_PER_BORROW = 5;         // Giới hạn số sách mượn mỗi lần
    public static final int MAX_BORROW_LIMIT = 5;             // Giới hạn tổng số sách đang mượn
    public static final int VIOLATION_LOCK_THRESHOLD = 10;    // Điểm vi phạm để khóa tài khoản
    
    // ========== PHÍ PHẠT ==========
    public static final double PENALTY_PER_DAY = 5000.0;      // Phí phạt mỗi ngày quá hạn (VNĐ)
    public static final double PENALTY_LOST_BOOK = 100000.0;  // Phí phạt làm mất sách (VNĐ)
    public static final double PENALTY_DAMAGED_BOOK = 50000.0;// Phí phạt làm hỏng sách (VNĐ)
    
    // ========== TRẠNG THÁI PHIẾU MƯỢN ==========
    public static final int BORROW_STATUS_ACTIVE = 0;     // Đang mượn
    public static final int BORROW_STATUS_COMPLETED = 1;  // Đã trả hết
    public static final int BORROW_STATUS_OVERDUE = 2;    // Quá hạn
    
    // ========== VAI TRÒ NGƯỜI DÙNG ==========
    public static final int ROLE_ADMIN = 1;        // Quản trị viên
    public static final int ROLE_LIBRARIAN = 2;    // Thủ thư
    
    // ========== ĐỊNH DẠNG NGÀY THÁNG ==========
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm:ss";
    
    // ========== THÔNG BÁO ==========
    public static final String MSG_SUCCESS = "Thành công!";
    public static final String MSG_ERROR = "Có lỗi xảy ra!";
    public static final String MSG_CONFIRM_DELETE = "Bạn có chắc muốn xóa?";
    public static final String MSG_REQUIRED_FIELDS = "Vui lòng điền đầy đủ thông tin!";
    public static final String MSG_INVALID_INPUT = "Dữ liệu không hợp lệ!";
    public static final String MSG_LOGIN_FAILED = "Tên đăng nhập hoặc mật khẩu không đúng!";
    public static final String MSG_READER_LOCKED = "Độc giả đã bị khóa!";
    public static final String MSG_BOOK_NOT_AVAILABLE = "Sách không khả dụng!";
    public static final String MSG_EXCEED_BORROW_LIMIT = "Vượt quá giới hạn mượn sách!";
    public static final String MSG_OVERDUE_PENALTY = "Sách trả quá hạn. Vui lòng thanh toán phạt!";
    
    // ========== HÀNH ĐỘNG AUDIT LOG ==========
    public static final String ACTION_LOGIN = "ĐĂNG NHẬP";
    public static final String ACTION_LOGOUT = "ĐĂNG XUẤT";
    public static final String ACTION_CREATE = "THÊM MỚI";
    public static final String ACTION_UPDATE = "CẬP NHẬT";
    public static final String ACTION_DELETE = "XÓA";
    public static final String ACTION_BORROW = "CHO MƯỢN";
    public static final String ACTION_RETURN = "TRẢ SÁCH";
    public static final String ACTION_PENALTY = "PHẠT";
    
    // ========== TÊN BẢNG ==========
    public static final String TABLE_BOOK = "DauSach";
    public static final String TABLE_BOOK_COPY = "CuonSach";
    public static final String TABLE_READER = "DocGia";
    public static final String TABLE_USER = "NguoiDung";
    public static final String TABLE_BORROW = "PhieuMuon";
    public static final String TABLE_PENALTY = "PhieuPhat";
    public static final String TABLE_CATEGORY = "TheLoai";
    
    // ========== MÃ VẠCH ==========
    public static final String BARCODE_PREFIX = "B-";
    
    // Private constructor to prevent instantiation
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }
}

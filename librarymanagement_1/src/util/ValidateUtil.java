/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.regex.Pattern;

/**
 * Lớp tiện ích xác thực dữ liệu đầu vào
 * @author ASUS
 */
public class ValidateUtil {
    
    // Regular expressions
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$"
    );
    
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_]{3,20}$"
    );
    
    /**
     * Kiểm tra chuỗi có rỗng hoặc null không
     * @param str Chuỗi cần kiểm tra
     * @return true nếu rỗng hoặc null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Kiểm tra email có hợp lệ không
     * @param email Email cần kiểm tra
     * @return true nếu hợp lệ
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Kiểm tra số điện thoại Việt Nam có hợp lệ không
     * @param phone Số điện thoại cần kiểm tra
     * @return true nếu hợp lệ
     */
    public static boolean isValidPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        // Remove all spaces and dots for validation
        String cleanPhone = phone.replaceAll("[\\s.]", "");
        return PHONE_PATTERN.matcher(cleanPhone).matches();
    }
    
    /**
     * Kiểm tra tên đăng nhập có hợp lệ không (3-20 ký tự, chỉ chữ, số, gạch dưới)
     * @param username Tên đăng nhập
     * @return true nếu hợp lệ
     */
    public static boolean isValidUsername(String username) {
        if (isEmpty(username)) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }
    
    /**
     * Kiểm tra mật khẩu có đủ mạnh không (tối thiểu 6 ký tự)
     * @param password Mật khẩu
     * @return true nếu hợp lệ
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
    
    /**
     * Kiểm tra số có phải là số nguyên dương không
     * @param str Chuỗi cần kiểm tra
     * @return true nếu là số nguyên dương
     */
    public static boolean isPositiveInteger(String str) {
        if (isEmpty(str)) {
            return false;
        }
        try {
            int value = Integer.parseInt(str);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Kiểm tra số có phải là số thực dương không
     * @param str Chuỗi cần kiểm tra
     * @return true nếu là số thực dương
     */
    public static boolean isPositiveDouble(String str) {
        if (isEmpty(str)) {
            return false;
        }
        try {
            double value = Double.parseDouble(str);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Kiểm tra năm có hợp lệ không (1900 - năm hiện tại + 1)
     * @param year Năm cần kiểm tra
     * @return true nếu hợp lệ
     */
    public static boolean isValidYear(int year) {
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return year >= 1900 && year <= currentYear + 1;
    }
    
    /**
     * Kiểm tra độ dài chuỗi có nằm trong khoảng cho phép không
     * @param str Chuỗi cần kiểm tra
     * @param minLength Độ dài tối thiểu
     * @param maxLength Độ dài tối đa
     * @return true nếu hợp lệ
     */
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        if (str == null) {
            return false;
        }
        int length = str.trim().length();
        return length >= minLength && length <= maxLength;
    }
    
    /**
     * Kiểm tra mã thẻ độc giả có hợp lệ không (DG + 6 số)
     * @param cardId Mã thẻ
     * @return true nếu hợp lệ
     */
    public static boolean isValidCardId(String cardId) {
        if (isEmpty(cardId)) {
            return false;
        }
        // Format: DG + 6 digits (e.g., DG000001)
        return Pattern.matches("^DG\\d{6}$", cardId);
    }
    
    /**
     * Kiểm tra mã vạch có hợp lệ không
     * @param barcode Mã vạch
     * @return true nếu hợp lệ
     */
    public static boolean isValidBarcode(String barcode) {
        if (isEmpty(barcode)) {
            return false;
        }
        // Format: B-{bookId}-{timestamp}
        return barcode.startsWith(Constants.BARCODE_PREFIX) && barcode.length() >= 10;
    }
    
    /**
     * Loại bỏ khoảng trắng thừa và chuẩn hóa chuỗi
     * @param str Chuỗi cần chuẩn hóa
     * @return Chuỗi đã chuẩn hóa
     */
    public static String normalizeString(String str) {
        if (str == null) {
            return "";
        }
        return str.trim().replaceAll("\\s+", " ");
    }
    
    /**
     * Chuẩn hóa số điện thoại (loại bỏ khoảng trắng và dấu chấm)
     * @param phone Số điện thoại
     * @return Số điện thoại đã chuẩn hóa
     */
    public static String normalizePhone(String phone) {
        if (phone == null) {
            return "";
        }
        return phone.replaceAll("[\\s.]", "");
    }
    
    /**
     * Kiểm tra chuỗi chỉ chứa chữ cái và khoảng trắng
     * @param str Chuỗi cần kiểm tra
     * @return true nếu hợp lệ
     */
    public static boolean isAlphabeticWithSpaces(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return Pattern.matches("^[a-zA-ZÀ-ỹ\\s]+$", str);
    }
    
    /**
     * Xác thực toàn bộ thông tin độc giả
     * @param name Họ tên
     * @param email Email
     * @param phone Số điện thoại
     * @return Thông báo lỗi hoặc null nếu hợp lệ
     */
    public static String validateReaderInfo(String name, String email, String phone) {
        if (isEmpty(name) || !isValidLength(name, 2, 100)) {
            return "Họ tên phải từ 2-100 ký tự!";
        }
        
        if (!isEmpty(email) && !isValidEmail(email)) {
            return "Email không hợp lệ!";
        }
        
        if (!isEmpty(phone) && !isValidPhone(phone)) {
            return "Số điện thoại không hợp lệ!";
        }
        
        return null; // Valid
    }
    
    /**
     * Xác thực thông tin sách
     * @param title Tựa đề
     * @param author Tác giả
     * @param year Năm xuất bản
     * @param price Giá tiền
     * @param quantity Số lượng
     * @return Thông báo lỗi hoặc null nếu hợp lệ
     */
    public static String validateBookInfo(String title, String author, int year, double price, int quantity) {
        if (isEmpty(title) || !isValidLength(title, 1, 200)) {
            return "Tựa đề phải từ 1-200 ký tự!";
        }
        
        if (year > 0 && !isValidYear(year)) {
            return "Năm xuất bản không hợp lệ!";
        }
        
        if (price < 0) {
            return "Giá tiền không được âm!";
        }
        
        if (quantity <= 0) {
            return "Số lượng phải lớn hơn 0!";
        }
        
        return null; // Valid
    }
    
    // Private constructor to prevent instantiation
    private ValidateUtil() {
        throw new AssertionError("Cannot instantiate ValidateUtil class");
    }
}

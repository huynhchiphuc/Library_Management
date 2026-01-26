/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Lớp tiện ích xử lý ngày tháng
 * @author ASUS
 */
public class DateUtil {
    
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(Constants.DATE_FORMAT);
    private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat(Constants.DATETIME_FORMAT);
    
    /**
     * Chuyển đổi chuỗi sang Date theo định dạng dd/MM/yyyy
     * @param dateString Chuỗi ngày tháng (ví dụ: "25/01/2026")
     * @return Date object nếu parse thành công, null nếu có lỗi
     * Xử lý: Sử dụng SimpleDateFormat với pattern dd/MM/yyyy
     */
    public static Date parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return DATE_FORMATTER.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + dateString);
            return null;
        }
    }
    
    /**
     * Chuyển đổi Date sang chuỗi theo định dạng dd/MM/yyyy
     * @param date Đối tượng Date cần format
     * @return Chuỗi ngày tháng dạng "dd/MM/yyyy", chuỗi rỗng nếu date = null
     * Xử lý: Sử dụng SimpleDateFormat.format()
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_FORMATTER.format(date);
    }
    
    /**
     * Chuyển đổi Date sang String theo định dạng dd/MM/yyyy HH:mm:ss
     * @param date Đối tượng Date
     * @return Chuỗi ngày giờ hoặc chuỗi rỗng nếu date là null
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return DATETIME_FORMATTER.format(date);
    }
    
    /**
     * Lấy ngày hiện tại
     * @return Date hiện tại
     */
    public static Date getCurrentDate() {
        return new Date();
    }
    
    /**
     * Tính ngày hẹn trả mặc định (ngày hiện tại + số ngày mượn)
     * @param borrowDate Ngày mượn
     * @param days Số ngày mượn
     * @return Ngày hẹn trả
     */
    public static Date calculateDueDate(Date borrowDate, int days) {
        if (borrowDate == null) {
            borrowDate = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(borrowDate);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
    
    /**
     * Tính ngày hẹn trả mặc định với số ngày mượn chuẩn
     * @return Ngày hẹn trả
     */
    public static Date calculateDefaultDueDate() {
        return calculateDueDate(new Date(), Constants.DEFAULT_BORROW_DAYS);
    }
    
    /**
     * Tính số ngày giữa hai ngày
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return Số ngày (có thể âm nếu endDate < startDate)
     */
    public static long daysBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        long diffInMillies = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Kiểm tra xem ngày trả có quá hạn hay không
     * @param dueDate Ngày hẹn trả
     * @param returnDate Ngày trả thực tế (null = ngày hiện tại)
     * @return true nếu quá hạn
     */
    public static boolean isOverdue(Date dueDate, Date returnDate) {
        if (dueDate == null) {
            return false;
        }
        if (returnDate == null) {
            returnDate = new Date();
        }
        return returnDate.after(dueDate);
    }
    
    /**
     * Tính số ngày quá hạn
     * @param dueDate Ngày hẹn trả
     * @param returnDate Ngày trả thực tế (null = ngày hiện tại)
     * @return Số ngày quá hạn (0 nếu không quá hạn)
     */
    public static long calculateOverdueDays(Date dueDate, Date returnDate) {
        if (!isOverdue(dueDate, returnDate)) {
            return 0;
        }
        if (returnDate == null) {
            returnDate = new Date();
        }
        return daysBetween(dueDate, returnDate);
    }
    
    /**
     * Tính phí phạt dựa trên số ngày quá hạn
     * @param dueDate Ngày hẹn trả
     * @param returnDate Ngày trả thực tế (null = ngày hiện tại)
     * @return Số tiền phạt
     */
    public static double calculatePenaltyAmount(Date dueDate, Date returnDate) {
        long overdueDays = calculateOverdueDays(dueDate, returnDate);
        return overdueDays * Constants.PENALTY_PER_DAY;
    }
    
    /**
     * Thêm số ngày vào một ngày
     * @param date Ngày gốc
     * @param days Số ngày cần thêm
     * @return Ngày mới
     */
    public static Date addDays(Date date, int days) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
    
    /**
     * Thêm số tháng vào một ngày
     * @param date Ngày gốc
     * @param months Số tháng cần thêm
     * @return Ngày mới
     */
    public static Date addMonths(Date date, int months) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }
    
    /**
     * Kiểm tra xem một ngày có phải là hôm nay không
     * @param date Ngày cần kiểm tra
     * @return true nếu là hôm nay
     */
    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Làm tròn ngày về 00:00:00
     * @param date Ngày cần làm tròn
     * @return Ngày đã làm tròn
     */
    public static Date truncateTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    // Private constructor to prevent instantiation
    private DateUtil() {
        throw new AssertionError("Cannot instantiate DateUtil class");
    }
}

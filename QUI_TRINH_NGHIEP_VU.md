# QUY TRÌNH NGHIỆP VỤ - HỆ THỐNG QUẢN LÝ THƯ VIỆN

Tài liệu này mô tả chi tiết các quy trình nghiệp vụ được áp dụng trong hệ thống phần mềm Quản Lý Thư Viện.

---

## 1. Đăng Nhập Hệ Thống
*   **Quy trình:**
    1.  Người dùng (Thủ thư/Admin) mở ứng dụng.
    2.  Nhập **Tên đăng nhập** và **Mật khẩu**.
    3.  Hệ thống kiểm tra thông tin trong cơ sở dữ liệu (`NguoiDung`).
    4.  Nếu đúng: Chuyển vào **Màn hình chính (Dashboard)**.
    5.  Nếu sai: Thông báo lỗi và yêu cầu nhập lại.

---

## 2. Quản Lý Sách (Kho Sách)
Hệ thống quản lý sách theo mô hình 2 cấp: **Đầu sách** (Thông tin thư mục) và **Cuốn sách** (Sách vật lý).

### 2.1. Thêm Mới Sách ("Smart Add")
*   **Mục đích:** Nhập sách mới hoặc nhập thêm số lượng cho sách đã có.
*   **Quy trình:**
    1.  Thủ thư nhập thông tin cơ bản: *Tựa đề, Tác giả, Năm xuất bản*, *Giá tiền, Số lượng nhập*.
    2.  Hệ thống tự động kiểm tra xem sách này đã tồn tại chưa (Dựa trên bộ ba: Tựa đề + Tác giả + Năm XB).
    3.  **Trường hợp 1 (Sách cũ):**
        *   Hệ thống lấy ID của đầu sách đã có.
        *   Tạo thêm các bản ghi **Cuốn sách** mới vào kho.
        *   Sinh mã vạch tự động cho từng cuốn.
    4.  **Trường hợp 2 (Sách mới):**
        *   Hệ thống tạo mới **Đầu sách**.
        *   Sau đó tạo các bản ghi **Cuốn sách** và sinh mã vạch.
    5.  **Sinh mã vạch:** Mã vạch được tạo tự động theo quy tắc `B-{MãĐầuSách}-{TimeStamp}{STT}` để đảm bảo duy nhất.

### 2.2. Tìm kiếm Sách
*   Hỗ trợ tìm kiếm theo **Từ khóa** (Tên sách, Tác giả...) hoặc **Mã vạch** (Quét mã vạch trên sách để tìm thông tin gốc).

---

## 3. Quy Trình Mượn Sách
*   **Điều kiện:** Độc giả phải có thẻ và không bị khóa.
*   **Các bước:**
    1.  **Nhập thông tin Độc giả:**
        *   Thủ thư nhập **Mã thẻ độc giả**.
        *   Bấm "Kiểm tra". Hệ thống hiển thị tên độc giả và trạng thái (có bị khóa hay không).
    2.  **Nhập sách mượn:**
        *   Thủ thư nhập (hoặc quét) **Mã vạch (Barcode)** của cuốn sách.
        *   Bấm "Thêm sách".
        *   Hệ thống kiểm tra sách có hợp lệ (đang sẵn sàng) không. Nếu hợp lệ, thêm vào danh sách chờ mượn.
    3.  **Xác nhận Mượn:**
        *   Thủ thư chọn **Ngày hẹn trả**.
        *   Bấm **"CHO MƯỢN"**.
        *   Hệ thống tạo **Phiếu mượn** và các **Chi tiết phiếu mượn**.
        *   Cập nhật trạng thái các cuốn sách sang **"Đang mượn"** (Status = 2).

---

## 4. Quy Trình Trả Sách
*   **Đặc điểm:** Trả sách nhanh thông qua mã vạch, không cần nhớ mã độc giả.
*   **Các bước:**
    1.  Độc giả mang sách trả.
    2.  Thủ thư nhập (hoặc quét) **Mã vạch** cuốn sách vào ô Mã sách tại form Mượn - Trả.
    3.  Bấm nút **"NHẬN TRẢ"**.
    4.  Hệ thống xử lý:
        *   Tìm giao dịch mượn gần nhất chưa trả của cuốn sách đó.
        *   Cập nhật ngày trả thực tế là thời điểm hiện tại.
        *   Cập nhật trạng thái trả là "Đã trả".
        *   Cập nhật trạng thái cuốn sách trong kho về **"Sẵn sàng"** (Status = 1).
    5.  Thông báo trả thành công.

---

## 5. Quản Lý Độc Giả
*   Quản lý thông tin cá nhân độc giả, mã thẻ.
*   Theo dõi lịch sử mượn trả.
*   Khóa thẻ nếu vi phạm nhiều lần.

---

## 6. Vi Phạm & Phạt (Dự kiến logic)
*   Nếu trả sách quá hạn (Ngày trả thực tế > Ngày hẹn trả): Tính phí phạt dựa trên số ngày quá hạn * đơn giá phạt.
*   Nếu làm mất/hỏng sách: Phạt theo giá trị sách hoặc quy định thư viện.

---

## 7. Báo Cáo & Thống Kê (Dashboard)
Hệ thống cung cấp các chỉ số thời gian thực:
*   **Tổng quan:** Số lượng sách trong kho, Số độc giả, Số sách đang được mượn, Số lượt quá hạn.
*   **Danh sách đang mượn:** Liệt kê chi tiết ai đang giữ sách gì, ngày mượn và hạn trả để thủ thư dễ dàng đòi sách.
*   **Top Độc giả:** Xếp hạng các độc giả tích cực nhất.
*   **Hoạt động gần đây:** Log các giao dịch mượn trả mới nhất.

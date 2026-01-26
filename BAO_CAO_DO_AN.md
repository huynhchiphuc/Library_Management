# BÁO CÁO ĐỒ ÁN
# HỆ THỐNG QUẢN LÝ THƯ VIỆN

---

## 1. MỤC ĐÍCH CỦA CHƯƠNG TRÌNH

Hệ thống Quản lý Thư viện được phát triển nhằm mục đích:

- **Số hóa quy trình quản lý**: Thay thế phương thức quản lý thủ công bằng hệ thống điện tử, giúp tăng hiệu quả và giảm thiểu sai sót.

- **Quản lý tài nguyên sách**: Theo dõi chi tiết thông tin đầu sách (tựa đề, tác giả, nhà xuất bản) và cuốn sách cụ thể (mã vạch, tình trạng, vị trí kệ), hỗ trợ nhập kho và kiểm kê.

- **Quản lý độc giả**: Lưu trữ thông tin độc giả, quản lý thẻ thư viện, theo dõi hạn thẻ, điểm vi phạm và trạng thái khóa/mở thẻ.

- **Xử lý mượn trả**: Tự động hóa quy trình mượn sách và trả sách, tính toán hạn trả, cảnh báo trễ hạn và ghi nhận tình trạng sách khi trả.

- **Quản lý phạt**: Tính toán tự động tiền phạt dựa trên số ngày trễ hạn hoặc mất/hỏng sách, theo dõi trạng thái đóng tiền phạt.

- **Thống kê báo cáo**: Cung cấp các báo cáo về sách mượn nhiều, độc giả tích cực, tình hình phạt, và các chỉ số hoạt động của thư viện.

- **Phân quyền người dùng**: Hỗ trợ nhiều vai trò (Quản lý, Thủ thư) với các quyền hạn khác nhau, đảm bảo bảo mật và kiểm soát truy cập.

- **Ghi nhận hoạt động**: Lưu lại nhật ký hoạt động của người dùng (đăng nhập, thêm/sửa/xóa dữ liệu) để kiểm toán và truy vết.

Hệ thống được xây dựng bằng Java Swing, sử dụng cơ sở dữ liệu MySQL, phù hợp với các thư viện trường học, thư viện công cộng hoặc thư viện doanh nghiệp có quy mô vừa và nhỏ.

---

## 2. CHỨC NĂNG CỦA CHƯƠNG TRÌNH

Hệ thống Quản lý Thư viện bao gồm các chức năng chính sau:

### 2.1. Quản lý Đăng nhập và Xác thực
- Đăng nhập với tên đăng nhập và mật khẩu
- Hiển thị/ẩn mật khẩu
- Kiểm tra thông tin đăng nhập và vai trò người dùng
- Ghi nhận nhật ký đăng nhập

### 2.2. Quản lý Đầu sách và Cuốn sách
- **Thêm đầu sách mới**: Nhập thông tin tựa đề, tác giả, nhà xuất bản, năm xuất bản, thể loại, mô tả
- **Thêm cuốn sách**: Tự động tạo mã vạch cho từng cuốn sách, nhập số lượng, giá tiền, vị trí kệ
- **Sửa thông tin**: Chỉnh sửa thông tin đầu sách hoặc cuốn sách
- **Xóa đầu sách**: Xóa đầu sách (kiểm tra điều kiện không còn cuốn sách nào tham chiếu)
- **Tìm kiếm**: Tìm kiếm theo tựa đề, tác giả, nhà xuất bản, thể loại
- **Hiển thị danh sách**: Hiển thị toàn bộ đầu sách với số lượng cuốn hiện có

### 2.3. Quản lý Độc giả
- **Thêm độc giả**: Nhập thông tin họ tên, email, số điện thoại, địa chỉ, tự động tạo mã thẻ
- **Cập nhật thông tin**: Sửa thông tin cá nhân, gia hạn thẻ
- **Khóa/Mở khóa thẻ**: Quản lý trạng thái hoạt động của thẻ độc giả
- **Theo dõi vi phạm**: Hiển thị điểm vi phạm, giới hạn mượn sách
- **Tìm kiếm độc giả**: Tìm theo mã thẻ, họ tên, số điện thoại

### 2.4. Quản lý Mượn Trả Sách
- **Lập phiếu mượn**: Kiểm tra mã độc giả, thêm sách vào phiếu mượn, nhập hạn trả
- **Kiểm tra điều kiện mượn**: Kiểm tra thẻ còn hạn, không bị khóa, chưa quá giới hạn mượn, sách còn khả dụng
- **Trả sách**: Nhận trả sách, ghi nhận tình trạng sách khi trả
- **Tính phạt tự động**: Tự động tạo phiếu phạt nếu trả trễ hoặc sách bị hư hỏng
- **Xem lịch sử mượn trả**: Tra cứu phiếu mượn theo độc giả hoặc trạng thái

### 2.5. Quản lý Phạt
- **Xem danh sách phạt**: Hiển thị các phiếu phạt đã lập
- **Ghi nhận đóng tiền**: Cập nhật trạng thái đã đóng tiền phạt
- **Thống kê công nợ**: Xem tổng số tiền phạt chưa thu

### 2.6. Quản lý Thể loại Sách
- **Thêm thể loại**: Tạo thể loại sách mới (Văn học, Khoa học, Lịch sử, ...)
- **Sửa, xóa thể loại**: Quản lý danh mục thể loại
- **Hiển thị danh sách**: Liệt kê các thể loại hiện có

### 2.7. Quản lý Người dùng (Admin)
- **Thêm người dùng**: Tạo tài khoản cho thủ thư, quản lý
- **Phân quyền**: Gán vai trò (Quản lý, Thủ thư) cho người dùng
- **Kích hoạt/Vô hiệu hóa**: Quản lý trạng thái hoạt động của tài khoản
- **Đổi mật khẩu**: Cho phép người dùng thay đổi mật khẩu

### 2.8. Báo cáo và Thống kê
- **Top sách mượn nhiều**: Thống kê đầu sách được mượn nhiều nhất
- **Độc giả tích cực**: Liệt kê độc giả mượn sách nhiều nhất
- **Báo cáo phạt**: Thống kê tình hình phạt theo thời gian
- **Thống kê tổng quan**: Tổng số sách, độc giả, phiếu mượn đang hoạt động

### 2.9. Nhật ký Hoạt động (Audit Log)
- **Ghi nhận hoạt động**: Lưu lại các thao tác thêm/sửa/xóa trên các bảng dữ liệu
- **Xem nhật ký**: Tra cứu lịch sử hoạt động theo người dùng, thời gian, loại hành động
- **Truy vết**: Hỗ trợ kiểm toán và giải quyết sự cố

### 2.10. Trang Chủ (Dashboard)
- **Hiển thị thống kê nhanh**: Tổng số sách, độc giả, phiếu mượn đang hoạt động
- **Thông báo**: Hiển thị số phiếu mượn quá hạn, phạt chưa thu
- **Menu điều hướng**: Truy cập nhanh các chức năng chính

---

## 3. XÂY DỰNG CHƯƠNG TRÌNH

### 3.1. Cơ sở dữ liệu

Hệ thống sử dụng cơ sở dữ liệu **MySQL** với các bảng chính sau:

#### 3.1.1. Bảng VaiTro (Role)
Lưu trữ các vai trò người dùng trong hệ thống.

| Trường | Kiểu dữ liệu | Ràng buộc | Mô tả |
|--------|--------------|-----------|-------|
| MaVaiTro | INT | PRIMARY KEY, AUTO_INCREMENT | Mã vai trò |
| TenVaiTro | VARCHAR(50) | NOT NULL, UNIQUE | Tên vai trò (Quản lý, Thủ thư) |
| MoTa | VARCHAR(255) | NULL | Mô tả vai trò |

#### 3.1.2. Bảng TheLoai (Category)
Lưu trữ thông tin thể loại sách.

| Trường | Kiểu dữ liệu | Ràng buộc | Mô tả |
|--------|--------------|-----------|-------|
| MaTheLoai | INT | PRIMARY KEY, AUTO_INCREMENT | Mã thể loại |
| TenTheLoai | VARCHAR(100) | NOT NULL | Tên thể loại |
| MoTa | VARCHAR(500) | NULL | Mô tả thể loại |

#### 3.1.3. Bảng DauSach (Book)
Lưu trữ thông tin đầu sách (thông tin chung về một cuốn sách).

| Trường | Kiểu dữ liệu | Ràng buộc | Mô tả |
|--------|--------------|-----------|-------|
| MaDauSach | INT | PRIMARY KEY, AUTO_INCREMENT | Mã đầu sách |
| TuaDe | VARCHAR(200) | NOT NULL | Tựa đề sách |
| TacGia | VARCHAR(150) | NULL | Tác giả |
| NhaXuatBan | VARCHAR(150) | NULL | Nhà xuất bản |
| NamXuatBan | INT | NULL | Năm xuất bản |
| MaTheLoai | INT | FOREIGN KEY → TheLoai | Thể loại |
| HinhAnh | VARCHAR(500) | NULL | Đường dẫn hình ảnh |
| MoTa | TEXT | NULL | Mô tả nội dung |

#### 3.1.4. Bảng CuonSach (BookCopy)
Lưu trữ thông tin từng cuốn sách cụ thể (bản sao vật lý).

| Trường | Kiểu dữ liệu | Ràng buộc | Mô tả |
|--------|--------------|-----------|-------|
| MaCuonSach | INT | PRIMARY KEY, AUTO_INCREMENT | Mã cuốn sách |
| MaDauSach | INT | FOREIGN KEY → DauSach, NOT NULL | Đầu sách |
| MaVach | VARCHAR(50) | NOT NULL | Mã vạch |
| TrangThai | TINYINT | NOT NULL, DEFAULT 1 | Trạng thái (1: Có sẵn, 0: Đang mượn) |
| TinhTrang | VARCHAR(100) | DEFAULT 'Mới' | Tình trạng vật lý |
| ViTriKe | VARCHAR(50) | NULL | Vị trí kệ |
| GiaTien | DECIMAL(18,2) | NULL | Giá tiền |
| NgayNhap | DATE | DEFAULT CURRENT_DATE | Ngày nhập kho |

#### 3.1.5. Bảng DocGia (Reader)
Lưu trữ thông tin độc giả.

| Trường | Kiểu dữ liệu | Ràng buộc | Mô tả |
|--------|--------------|-----------|-------|
| MaDocGia | INT | PRIMARY KEY, AUTO_INCREMENT | Mã độc giả |
| MaThe | VARCHAR(20) | NOT NULL | Mã thẻ độc giả |
| HoTen | VARCHAR(100) | NOT NULL | Họ tên |
| Email | VARCHAR(100) | NULL | Email |
| SoDienThoai | VARCHAR(15) | NULL | Số điện thoại |
| DiaChi | VARCHAR(255) | NULL | Địa chỉ |
| GioiHanMuon | INT | DEFAULT 5 | Giới hạn số sách được mượn |
| NgayHetHan | DATE | NOT NULL | Ngày hết hạn thẻ |
| DiemViPham | INT | DEFAULT 0 | Điểm vi phạm |
| BiKhoa | TINYINT(1) | DEFAULT 0 | Trạng thái khóa (0: Mở, 1: Khóa) |

#### 3.1.6. Bảng NguoiDung (User)
Lưu trữ tài khoản người dùng hệ thống.

| Trường | Kiểu dữ liệu | Ràng buộc | Mô tả |
|--------|--------------|-----------|-------|
| MaNguoiDung | INT | PRIMARY KEY, AUTO_INCREMENT | Mã người dùng |
| TenDangNhap | VARCHAR(50) | NOT NULL | Tên đăng nhập |
| MatKhau | VARCHAR(255) | NOT NULL | Mật khẩu (đã mã hóa) |
| HoTen | VARCHAR(100) | NOT NULL | Họ tên |
| Email | VARCHAR(100) | NULL | Email |
| SoDienThoai | VARCHAR(20) | NULL | Số điện thoại |
| MaVaiTro | INT | FOREIGN KEY → VaiTro, NOT NULL | Vai trò |
| DangHoatDong | TINYINT(1) | DEFAULT 1 | Trạng thái hoạt động |
| NgayTao | DATETIME | DEFAULT CURRENT_TIMESTAMP | Ngày tạo tài khoản |

#### 3.1.7. Bảng NhatKyHoatDong (AuditLog)
Lưu trữ nhật ký hoạt động của người dùng.

| Trường | Kiểu dữ liệu | Ràng buộc | Mô tả |
|--------|--------------|-----------|-------|
| MaNhatKy | INT | PRIMARY KEY, AUTO_INCREMENT | Mã nhật ký |
| MaNguoiDung | INT | FOREIGN KEY → NguoiDung | Người thực hiện |
| HanhDong | VARCHAR(50) | NOT NULL | Hành động (Thêm, Sửa, Xóa, Đăng nhập) |
| TenBang | VARCHAR(50) | NULL | Tên bảng tác động |
| MaBanGhi | INT | NULL | Mã bản ghi |
| MoTaChiTiet | TEXT | NULL | Mô tả chi tiết |
| DiaChiIP | VARCHAR(50) | NULL | Địa chỉ IP |
| ThoiGian | DATETIME | DEFAULT CURRENT_TIMESTAMP | Thời gian |

#### 3.1.8. Bảng PhieuMuon (BorrowSlip)
Lưu trữ phiếu mượn sách.

| Trường | Kiểu dữ liệu | Ràng buộc | Mô tả |
|--------|--------------|-----------|-------|
| MaPhieuMuon | INT | PRIMARY KEY, AUTO_INCREMENT | Mã phiếu mượn |
| MaDocGia | INT | FOREIGN KEY → DocGia, NOT NULL | Độc giả mượn |
| MaNguoiDung | INT | FOREIGN KEY → NguoiDung | Người lập phiếu |
| NgayMuon | DATETIME | DEFAULT CURRENT_TIMESTAMP | Ngày mượn |
| HanTra | DATE | NOT NULL | Hạn trả |
| GhiChu | VARCHAR(255) | NULL | Ghi chú |
| TrangThai | TINYINT | DEFAULT 0 | Trạng thái (0: Đang mượn, 1: Đã trả) |

#### 3.1.9. Bảng ChiTietMuonTra (BorrowDetail)
Lưu trữ chi tiết các cuốn sách trong phiếu mượn.

| Trường | Kiểu dữ liệu | Ràng buộc | Mô tả |
|--------|--------------|-----------|-------|
| MaChiTiet | INT | PRIMARY KEY, AUTO_INCREMENT | Mã chi tiết |
| MaPhieuMuon | INT | FOREIGN KEY → PhieuMuon, NOT NULL | Phiếu mượn |
| MaCuonSach | INT | FOREIGN KEY → CuonSach, NOT NULL | Cuốn sách |
| NgayTra | DATETIME | NULL | Ngày trả (NULL nếu chưa trả) |
| TinhTrangTra | VARCHAR(100) | NULL | Tình trạng khi trả |
| GhiChu | VARCHAR(255) | NULL | Ghi chú |

#### 3.1.10. Bảng PhieuPhat (Penalty)
Lưu trữ phiếu phạt.

| Trường | Kiểu dữ liệu | Ràng buộc | Mô tả |
|--------|--------------|-----------|-------|
| MaPhieuPhat | INT | PRIMARY KEY, AUTO_INCREMENT | Mã phiếu phạt |
| MaChiTiet | INT | FOREIGN KEY → ChiTietMuonTra | Chi tiết mượn trả |
| MaDocGia | INT | FOREIGN KEY → DocGia, NOT NULL | Độc giả bị phạt |
| LyDo | VARCHAR(200) | NULL | Lý do phạt |
| SoTien | DECIMAL(18,2) | NOT NULL, DEFAULT 0 | Số tiền phạt |
| DaDongTien | TINYINT(1) | DEFAULT 0 | Đã đóng tiền (0: Chưa, 1: Đã đóng) |
| NgayTao | DATETIME | DEFAULT CURRENT_TIMESTAMP | Ngày tạo phiếu |

#### 3.1.11. Mối quan hệ giữa các bảng

```
VaiTro (1) ----< (N) NguoiDung
TheLoai (1) ----< (N) DauSach
DauSach (1) ----< (N) CuonSach
DocGia (1) ----< (N) PhieuMuon
DocGia (1) ----< (N) PhieuPhat
NguoiDung (1) ----< (N) PhieuMuon
NguoiDung (1) ----< (N) NhatKyHoatDong
PhieuMuon (1) ----< (N) ChiTietMuonTra
CuonSach (1) ----< (N) ChiTietMuonTra
ChiTietMuonTra (1) ----< (1) PhieuPhat
```

### 3.2. Thiết kế các lớp

Hệ thống được thiết kế theo mô hình **MVC (Model-View-Controller)** và phân tách thành các lớp rõ ràng:

#### 3.2.1. Cấu trúc Package

```
librarymanagement_1
│
├── model/              # Lớp Model - Đại diện cho dữ liệu
│   ├── Book.java
│   ├── BookCopy.java
│   ├── BorrowSlip.java
│   ├── BorrowDetail.java
│   ├── Category.java
│   ├── Reader.java
│   ├── User.java
│   ├── Role.java
│   ├── Penalty.java
│   └── AuditLog.java
│
├── view/               # Lớp View - Giao diện người dùng
│   ├── LoginForm.java
│   ├── MainForm.java
│   ├── HomeForm.java
│   ├── BookForm.java
│   ├── ReaderForm.java
│   ├── BorrowForm.java
│   ├── PenaltyForm.java
│   ├── ReportForm.java
│   ├── CategoryForm.java
│   ├── UserForm.java
│   ├── AuditLogForm.java
│   └── ChangePasswordForm.java
│
├── controller/         # Lớp Controller - Xử lý logic điều khiển
│   ├── LoginController.java
│   ├── MainController.java
│   ├── HomeController.java
│   ├── BookController.java
│   ├── ReaderController.java
│   ├── BorrowController.java
│   ├── PenaltyController.java
│   ├── ReportController.java
│   ├── CategoryController.java
│   ├── UserController.java
│   └── AuditLogController.java
│
├── dao/                # Lớp DAO - Truy xuất cơ sở dữ liệu
│   ├── BookDAO.java
│   ├── BookCopyDAO.java
│   ├── BorrowSlipDAO.java
│   ├── BorrowDetailDAO.java
│   ├── CategoryDAO.java
│   ├── ReaderDAO.java
│   ├── UserDAO.java
│   ├── RoleDAO.java
│   ├── PenaltyDAO.java
│   ├── ReportDAO.java
│   └── AuditLogDAO.java
│
├── service/            # Lớp Service - Xử lý logic nghiệp vụ
│   ├── AuthService.java
│   ├── BookService.java
│   ├── BorrowService.java
│   ├── ReaderService.java
│   ├── PenaltyService.java
│   ├── ReportService.java
│   └── AuditService.java
│
├── util/               # Lớp Tiện ích
│   ├── DBConnection.java
│   ├── Constants.java
│   ├── DateUtil.java
│   ├── ValidateUtil.java
│   └── PasswordUtil.java
│
└── main/               # Lớp Main - Điểm khởi chạy
    └── Main.java
```

#### 3.2.2. Sơ đồ phân lớp

**Lớp Model (Thực thể)**
- Chứa các thuộc tính (private fields)
- Cung cấp Getter/Setter
- Constructor mặc định và có tham số

**Lớp View (Giao diện)**
- Kế thừa từ JFrame hoặc JPanel
- Sử dụng Java Swing components
- Không chứa logic xử lý, chỉ hiển thị và nhận input

**Lớp Controller (Điều khiển)**
- Nhận sự kiện từ View
- Gọi Service/DAO để xử lý
- Cập nhật lại View

**Lớp DAO (Data Access Object)**
- Chứa các phương thức CRUD (Create, Read, Update, Delete)
- Kết nối và truy vấn cơ sở dữ liệu
- Trả về Model hoặc List<Model>

**Lớp Service (Nghiệp vụ)**
- Chứa logic nghiệp vụ phức tạp
- Kết hợp nhiều DAO để xử lý
- Kiểm tra điều kiện, validate dữ liệu

**Lớp Util (Tiện ích)**
- DBConnection: Quản lý kết nối CSDL (Singleton Pattern)
- Constants: Lưu các hằng số
- DateUtil: Xử lý định dạng ngày tháng
- ValidateUtil: Kiểm tra tính hợp lệ dữ liệu
- PasswordUtil: Mã hóa mật khẩu (BCrypt)

---

## 4. XÂY DỰNG CÁC LỚP TRONG CHƯƠNG TRÌNH

### 4.1. Lớp thứ 1: MainForm (Menu Chính)

**Mô tả**: `MainForm` là giao diện chính của hệ thống, hiển thị sau khi người dùng đăng nhập thành công. Form này cung cấp menu điều hướng đến các chức năng khác nhau của hệ thống.

**Chức năng**:
- Hiển thị menu sidebar với các nút: Trang chủ, Quản lý Sách, Quản lý Độc giả, Mượn/Trả sách, Phạt, Báo cáo, Thể loại, Người dùng, Nhật ký hoạt động
- Hiển thị thông tin người dùng đang đăng nhập (họ tên, vai trò) trên header
- Chức năng đăng xuất
- Hiển thị nội dung các form con trong khu vực desktop (JPanel chứa)
- Phân quyền hiển thị: Ẩn các menu mà người dùng không có quyền truy cập

**Các thành phần chính**:
- **Sidebar Panel**: Chứa các nút điều hướng với màu nền xanh đậm (#2C3E50)
- **Header Panel**: Hiển thị thông tin người dùng và nút đăng xuất
- **Desktop Panel**: Khu vực hiển thị nội dung (dùng CardLayout hoặc thay thế JPanel)
- **Button Menu**: Các nút bấm cho từng chức năng (btnHome, btnBook, btnReader, btnBorrow, btnPenalty, btnReport, btnCategory, btnUser, btnAuditLog, btnLogout)

**Luồng hoạt động**:
1. Người dùng đăng nhập thành công → `LoginController` mở `MainForm`
2. `MainController` nhận thông tin người dùng từ `LoginController`
3. `MainController` phân quyền hiển thị menu dựa trên vai trò
4. Người dùng click vào menu → `MainController` tạo form tương ứng và hiển thị trong desktop panel
5. Người dùng click đăng xuất → Đóng `MainForm`, mở lại `LoginForm`

**Code minh họa (các phần chính)**:

```java
public class MainForm extends javax.swing.JFrame {
    private MainController controller;
    
    // Components
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnBook;
    private javax.swing.JButton btnReader;
    private javax.swing.JButton btnBorrow;
    private javax.swing.JButton btnPenalty;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnCategory;
    private javax.swing.JButton btnUser;
    private javax.swing.JButton btnAuditLog;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel lblUserInfo;
    private javax.swing.JPanel pnlDesktop;
    
    public MainForm() {
        initComponents();
        controller = new MainController(this);
        setLocationRelativeTo(null);
    }
    
    // Các phương thức getter cho Controller sử dụng
    public javax.swing.JButton getBtnHome() { return btnHome; }
    public javax.swing.JButton getBtnBook() { return btnBook; }
    public javax.swing.JButton getBtnReader() { return btnReader; }
    // ... các getter khác
    
    public javax.swing.JPanel getPnlDesktop() { return pnlDesktop; }
    public javax.swing.JLabel getLblUserInfo() { return lblUserInfo; }
}
```

**Controller tương ứng**:

```java
public class MainController {
    private final MainForm view;
    private model.User currentUser;
    
    public MainController(MainForm view) {
        this.view = view;
        initController();
    }
    
    public void setCurrentUser(model.User user) {
        this.currentUser = user;
        updateUserInfo();
        applyPermissions();
    }
    
    private void initController() {
        view.getBtnHome().addActionListener(e -> showHome());
        view.getBtnBook().addActionListener(e -> showBook());
        view.getBtnReader().addActionListener(e -> showReader());
        view.getBtnBorrow().addActionListener(e -> showBorrow());
        view.getBtnPenalty().addActionListener(e -> showPenalty());
        view.getBtnReport().addActionListener(e -> showReport());
        view.getBtnCategory().addActionListener(e -> showCategory());
        view.getBtnUser().addActionListener(e -> showUser());
        view.getBtnAuditLog().addActionListener(e -> showAuditLog());
        view.getBtnLogout().addActionListener(e -> logout());
    }
    
    private void showHome() {
        view.getPnlDesktop().removeAll();
        view.getPnlDesktop().add(new HomeForm());
        view.getPnlDesktop().revalidate();
        view.getPnlDesktop().repaint();
    }
    
    // Các phương thức show khác tương tự...
    
    private void applyPermissions() {
        // Ẩn menu User và AuditLog nếu không phải Admin
        if (currentUser.getRoleId() != 1) {
            view.getBtnUser().setVisible(false);
            view.getBtnAuditLog().setVisible(false);
        }
    }
    
    private void logout() {
        view.dispose();
        new LoginForm().setVisible(true);
    }
}
```

---

### 4.2. Lớp thứ 2: LoginForm (Đăng nhập)

**Mô tả**: `LoginForm` là giao diện đăng nhập, cho phép người dùng nhập tên đăng nhập và mật khẩu để truy cập hệ thống.

**Chức năng**:
- Nhập tên đăng nhập và mật khẩu
- Hiển thị/ẩn mật khẩu (checkbox)
- Kiểm tra thông tin đăng nhập
- Ghi nhận nhật ký đăng nhập
- Chuyển đến MainForm nếu đăng nhập thành công
- Thoát chương trình

**Các thành phần chính**:
- **txtUsername**: JTextField - Nhập tên đăng nhập
- **txtPassword**: JPasswordField - Nhập mật khẩu
- **chkShowPassword**: JCheckBox - Hiển thị/ẩn mật khẩu
- **btnLogin**: JButton - Đăng nhập
- **btnExit**: JButton - Thoát chương trình

**Luồng hoạt động**:
1. Người dùng nhập tên đăng nhập và mật khẩu
2. Click nút "ĐĂNG NHẬP" → `LoginController` nhận sự kiện
3. `LoginController` gọi `AuthService.login(username, password)`
4. `AuthService` gọi `UserDAO.getUserByUsername(username)`
5. So sánh mật khẩu (dùng BCrypt)
6. Nếu đúng: Ghi nhật ký đăng nhập → Mở `MainForm` → Truyền thông tin user cho `MainController`
7. Nếu sai: Hiển thị thông báo lỗi

**Code minh họa**:

```java
public class LoginForm extends javax.swing.JFrame {
    // Components
    private javax.swing.JTextField txtUsername;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JCheckBox chkShowPassword;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnExit;
    
    public LoginForm() {
        initComponents();
        new LoginController(this);
        setLocationRelativeTo(null);
    }
    
    // Getter methods
    public javax.swing.JTextField getTxtUsername() { return txtUsername; }
    public javax.swing.JPasswordField getTxtPassword() { return txtPassword; }
    public javax.swing.JCheckBox getChkShowPassword() { return chkShowPassword; }
    public javax.swing.JButton getBtnLogin() { return btnLogin; }
    public javax.swing.JButton getBtnExit() { return btnExit; }
}
```

**Controller**:

```java
public class LoginController {
    private final LoginForm view;
    private final AuthService authService;
    
    public LoginController(LoginForm view) {
        this.view = view;
        this.authService = new AuthService();
        initController();
    }
    
    private void initController() {
        view.getBtnLogin().addActionListener(e -> handleLogin());
        view.getBtnExit().addActionListener(e -> System.exit(0));
        view.getChkShowPassword().addActionListener(e -> togglePasswordVisibility());
        view.getTxtUsername().addActionListener(e -> handleLogin());
        view.getTxtPassword().addActionListener(e -> handleLogin());
    }
    
    private void handleLogin() {
        String username = view.getTxtUsername().getText().trim();
        String password = new String(view.getTxtPassword().getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        model.User user = authService.login(username, password);
        
        if (user != null) {
            if (!user.isActive()) {
                JOptionPane.showMessageDialog(view, "Tài khoản đã bị vô hiệu hóa!");
                return;
            }
            
            // Log audit
            new AuditService().log(user.getId(), "ĐĂNG NHẬP", null, null, 
                                   "Đăng nhập thành công", null);
            
            // Open MainForm
            MainForm mainForm = new MainForm();
            mainForm.getController().setCurrentUser(user);
            mainForm.setVisible(true);
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Tên đăng nhập hoặc mật khẩu không chính xác!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void togglePasswordVisibility() {
        if (view.getChkShowPassword().isSelected()) {
            view.getTxtPassword().setEchoChar((char) 0);
        } else {
            view.getTxtPassword().setEchoChar('•');
        }
    }
}
```

**Service**:

```java
public class AuthService {
    private final UserDAO userDAO;
    
    public AuthService() {
        this.userDAO = new UserDAO();
    }
    
    public model.User login(String username, String password) {
        model.User user = userDAO.getUserByUsername(username);
        
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        
        return null;
    }
}
```

---

### 4.3. Lớp thứ 3: BookForm (Quản lý Sách)

**Mô tả**: `BookForm` là giao diện quản lý đầu sách và cuốn sách. Cho phép thêm, sửa, xóa, tìm kiếm thông tin sách.

**Chức năng**:
- Hiển thị danh sách đầu sách trong bảng (JTable)
- Thêm đầu sách mới và cuốn sách
- Sửa thông tin đầu sách
- Xóa đầu sách (nếu không còn cuốn sách nào)
- Tìm kiếm sách theo tựa đề, tác giả, nhà xuất bản, thể loại
- Hiển thị số lượng cuốn sách có sẵn

**Các thành phần chính**:
- **tblBook**: JTable - Hiển thị danh sách đầu sách
- **txtMaDauSach, txtTuaDe, txtTacGia, txtNXB, txtNamXB, txtGiaTien, txtSoLuong, txtMoTa**: Các trường nhập liệu
- **cboTheLoai**: JComboBox - Chọn thể loại
- **cboSearchType**: JComboBox - Loại tìm kiếm
- **txtSearch**: JTextField - Nhập từ khóa tìm kiếm
- **btnAdd, btnEdit, btnDelete, btnReset, btnSearch, btnViewAll**: Các nút chức năng

**Luồng hoạt động**:
1. Form khởi tạo → `BookController` load danh sách sách từ `BookDAO`
2. Hiển thị lên bảng
3. Người dùng click chọn 1 dòng → Hiển thị thông tin lên các trường input
4. Nhập thông tin → Click "THÊM" → `BookController` validate → Gọi `BookService.addBook()` → Refresh bảng
5. Tương tự cho Sửa, Xóa
6. Tìm kiếm: Nhập từ khóa → Click "TÌM KIẾM" → `BookController` gọi `BookDAO.search()` → Hiển thị kết quả

**Code minh họa**:

```java
public class BookForm extends javax.swing.JPanel {
    private controller.BookController controller;
    
    // Components
    private javax.swing.JTable tblBook;
    private javax.swing.JTextField txtMaDauSach;
    private javax.swing.JTextField txtTuaDe;
    private javax.swing.JTextField txtTacGia;
    private javax.swing.JTextField txtNXB;
    private javax.swing.JTextField txtNamXB;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JComboBox<String> cboTheLoai;
    private javax.swing.JComboBox<String> cboSearchType;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewAll;
    
    public BookForm() {
        initComponents();
        controller = new controller.BookController(this);
    }
    
    // Getter methods cho controller sử dụng
}
```

**Controller**:

```java
public class BookController {
    private final BookForm view;
    private final BookDAO bookDAO;
    private final CategoryDAO categoryDAO;
    private final BookService bookService;
    
    public BookController(BookForm view) {
        this.view = view;
        this.bookDAO = new BookDAO();
        this.categoryDAO = new CategoryDAO();
        this.bookService = new BookService();
        
        initView();
        initController();
    }
    
    private void initView() {
        loadCategories();
        loadTableData(bookDAO.getAllBooks());
        view.getTxtMaDauSach().setEditable(false);
        view.getTxtMaDauSach().setText("(Tự động)");
    }
    
    private void initController() {
        view.getBtnAdd().addActionListener(e -> addBook());
        view.getBtnEdit().addActionListener(e -> editBook());
        view.getBtnDelete().addActionListener(e -> deleteBook());
        view.getBtnReset().addActionListener(e -> resetForm());
        view.getBtnSearch().addActionListener(e -> searchBook());
        view.getBtnViewAll().addActionListener(e -> loadTableData(bookDAO.getAllBooks()));
        
        view.getTblBook().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    fillFormFromTable();
                }
            }
        });
    }
    
    private void addBook() {
        // Validate input
        if (!validateInput()) return;
        
        // Tạo đối tượng Book từ form
        model.Book book = getBookFromForm();
        int quantity = Integer.parseInt(view.getTxtSoLuong().getText());
        double price = Double.parseDouble(view.getTxtGiaTien().getText());
        
        // Gọi BookService để thêm sách (bao gồm cả đầu sách và cuốn sách)
        boolean success = bookService.addBookWithCopies(book, quantity, price);
        
        if (success) {
            JOptionPane.showMessageDialog(view, "Thêm sách thành công!");
            loadTableData(bookDAO.getAllBooks());
            resetForm();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm sách thất bại!", 
                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadTableData(List<model.Book> books) {
        DefaultTableModel model = (DefaultTableModel) view.getTblBook().getModel();
        model.setRowCount(0);
        
        for (model.Book book : books) {
            model.addRow(new Object[]{
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPublishYear(),
                book.getCategoryName(),
                book.getQuantity(),
                book.getPrice()
            });
        }
    }
    
    // Các phương thức khác...
}
```

---

### 4.4. Lớp thứ 4: ReaderForm (Quản lý Độc giả)

**Mô tả**: `ReaderForm` là giao diện quản lý thông tin độc giả, bao gồm thêm, sửa, xóa, tìm kiếm độc giả.

**Chức năng**:
- Hiển thị danh sách độc giả trong bảng
- Thêm độc giả mới (tự động tạo mã thẻ)
- Sửa thông tin độc giả
- Xóa độc giả (nếu không có phiếu mượn đang hoạt động)
- Tìm kiếm độc giả theo mã thẻ, họ tên, số điện thoại
- Khóa/Mở khóa thẻ độc giả
- Gia hạn thẻ

**Các thành phần chính**:
- **tblReader**: JTable - Bảng hiển thị độc giả
- **txtMaThe, txtHoTen, txtEmail, txtSDT, txtDiaChi, txtNgayHetHan**: Các trường nhập liệu
- **chkBiKhoa**: JCheckBox - Trạng thái khóa thẻ
- **btnAdd, btnEdit, btnDelete, btnReset, btnSearch, btnViewAll**: Các nút chức năng

**Luồng hoạt động**:
1. Form khởi tạo → `ReaderController` load danh sách độc giả từ `ReaderDAO`
2. Click chọn độc giả → Hiển thị thông tin
3. Thêm/Sửa/Xóa → Validate → Gọi `ReaderService` → Cập nhật bảng
4. Tìm kiếm → `ReaderDAO.search()` → Hiển thị kết quả

**Code minh họa**:

```java
public class ReaderForm extends javax.swing.JPanel {
    private controller.ReaderController controller;
    
    // Components
    private javax.swing.JTable tblReader;
    private javax.swing.JTextField txtMaThe;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtNgayHetHan; // hoặc JDateChooser
    private javax.swing.JCheckBox chkBiKhoa;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewAll;
    
    public ReaderForm() {
        initComponents();
        controller = new controller.ReaderController(this);
    }
    
    // Getter methods
}
```

**Controller**:

```java
public class ReaderController {
    private final ReaderForm view;
    private final ReaderDAO readerDAO;
    private final ReaderService readerService;
    
    public ReaderController(ReaderForm view) {
        this.view = view;
        this.readerDAO = new ReaderDAO();
        this.readerService = new ReaderService();
        
        initView();
        initController();
    }
    
    private void initView() {
        loadTableData(readerDAO.getAllReaders());
        view.getTxtMaThe().setEditable(false);
        view.getTxtMaThe().setText("(Tự động)");
    }
    
    private void addReader() {
        if (!validateInput()) return;
        
        model.Reader reader = getReaderFromForm();
        reader.setCardNumber(generateCardNumber()); // Tự động tạo mã thẻ
        
        boolean success = readerService.addReader(reader);
        
        if (success) {
            JOptionPane.showMessageDialog(view, "Thêm độc giả thành công!");
            loadTableData(readerDAO.getAllReaders());
            resetForm();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm độc giả thất bại!", 
                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String generateCardNumber() {
        // Tạo mã thẻ: DG + năm hiện tại + số tự tăng (4 chữ số)
        String year = String.valueOf(java.time.Year.now().getValue());
        int maxId = readerDAO.getMaxReaderId() + 1;
        return String.format("DG%s%04d", year, maxId);
    }
    
    // Các phương thức khác...
}
```

---

### 4.5. Lớp thứ 5: BorrowForm (Mượn/Trả Sách)

**Mô tả**: `BorrowForm` là giao diện xử lý mượn và trả sách. Đây là chức năng cốt lõi của hệ thống quản lý thư viện.

**Chức năng**:
- Kiểm tra mã độc giả (mã thẻ)
- Thêm các cuốn sách vào phiếu mượn (nhập mã vạch)
- Lập phiếu mượn
- Trả sách
- Ghi nhận tình trạng sách khi trả
- Tự động tính phạt nếu trễ hạn hoặc sách bị hư hỏng

**Các thành phần chính**:
- **txtMaThe**: JTextField - Nhập mã độc giả
- **btnCheckReader**: JButton - Kiểm tra độc giả
- **lblTenDocGia**: JLabel - Hiển thị tên độc giả
- **txtMaSach**: JTextField - Nhập mã vạch sách
- **btnAddBook**: JButton - Thêm sách vào danh sách
- **txtHanTra**: JTextField/JDateChooser - Hạn trả
- **tblBorrow**: JTable - Danh sách sách trong phiếu
- **btnBorrow**: JButton - Cho mượn
- **btnReturn**: JButton - Nhận trả
- **btnReset**: JButton - Làm mới

**Luồng hoạt động**:

**A. Quy trình mượn sách:**
1. Nhập mã độc giả → Click "Kiểm tra"
2. `BorrowController` gọi `ReaderDAO.getReaderByCardNumber()`
3. Kiểm tra: Thẻ còn hạn? Không bị khóa? Chưa quá giới hạn mượn?
4. Hiển thị tên độc giả
5. Nhập mã vạch sách → Click "Thêm sách"
6. Kiểm tra sách có sẵn? (TrangThai = 1)
7. Thêm vào bảng
8. Nhập hạn trả → Click "CHO MƯỢN"
9. `BorrowService.createBorrowSlip()`:
   - Tạo bản ghi PhieuMuon
   - Tạo các bản ghi ChiTietMuonTra
   - Cập nhật TrangThai CuonSach = 0 (đang mượn)
10. Thông báo thành công

**B. Quy trình trả sách:**
1. Nhập mã độc giả → Click "Kiểm tra"
2. Hiển thị danh sách sách đang mượn của độc giả
3. Chọn sách cần trả → Nhập tình trạng trả
4. Click "NHẬN TRẢ"
5. `BorrowService.returnBook()`:
   - Cập nhật NgayTra và TinhTrangTra trong ChiTietMuonTra
   - Cập nhật TrangThai CuonSach = 1 (có sẵn)
   - Kiểm tra trễ hạn → Tính phạt
   - Kiểm tra tình trạng sách → Tính phạt nếu hư hỏng
   - Tạo PhieuPhat nếu có
6. Thông báo số tiền phạt (nếu có)

**Code minh họa**:

```java
public class BorrowForm extends javax.swing.JPanel {
    private controller.BorrowController controller;
    
    // Components
    private javax.swing.JTextField txtMaThe;
    private javax.swing.JButton btnCheckReader;
    private javax.swing.JLabel lblTenDocGia;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JButton btnAddBook;
    private javax.swing.JTextField txtHanTra;
    private javax.swing.JTable tblBorrow;
    private javax.swing.JButton btnBorrow;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnReset;
    
    public BorrowForm() {
        initComponents();
        controller = new controller.BorrowController(this);
    }
    
    // Getter methods
}
```

**Controller**:

```java
public class BorrowController {
    private final BorrowForm view;
    private final BorrowService borrowService;
    private final ReaderDAO readerDAO;
    private final BookCopyDAO bookCopyDAO;
    
    private model.Reader currentReader;
    private List<model.BookCopy> borrowList = new ArrayList<>();
    
    public BorrowController(BorrowForm view) {
        this.view = view;
        this.borrowService = new BorrowService();
        this.readerDAO = new ReaderDAO();
        this.bookCopyDAO = new BookCopyDAO();
        
        initController();
    }
    
    private void initController() {
        view.getBtnCheckReader().addActionListener(e -> checkReader());
        view.getBtnAddBook().addActionListener(e -> addBookToList());
        view.getBtnBorrow().addActionListener(e -> processBorrow());
        view.getBtnReturn().addActionListener(e -> processReturn());
        view.getBtnReset().addActionListener(e -> resetForm());
    }
    
    private void checkReader() {
        String cardNumber = view.getTxtMaThe().getText().trim();
        
        if (cardNumber.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã độc giả!");
            return;
        }
        
        currentReader = readerDAO.getReaderByCardNumber(cardNumber);
        
        if (currentReader == null) {
            JOptionPane.showMessageDialog(view, "Không tìm thấy độc giả!");
            view.getLblTenDocGia().setText("...");
            return;
        }
        
        // Kiểm tra điều kiện
        if (currentReader.isLocked()) {
            JOptionPane.showMessageDialog(view, "Thẻ độc giả đã bị khóa!");
            return;
        }
        
        if (currentReader.getExpiryDate().before(new Date())) {
            JOptionPane.showMessageDialog(view, "Thẻ độc giả đã hết hạn!");
            return;
        }
        
        view.getLblTenDocGia().setText(currentReader.getFullName());
    }
    
    private void processBorrow() {
        if (currentReader == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng kiểm tra độc giả trước!");
            return;
        }
        
        if (borrowList.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng thêm sách vào danh sách!");
            return;
        }
        
        String hanTraStr = view.getTxtHanTra().getText().trim();
        if (hanTraStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập hạn trả!");
            return;
        }
        
        Date hanTra = DateUtil.parseDate(hanTraStr);
        
        // Tạo phiếu mượn
        boolean success = borrowService.createBorrowSlip(
            currentReader.getId(), 
            borrowList, 
            hanTra, 
            getCurrentUserId()
        );
        
        if (success) {
            JOptionPane.showMessageDialog(view, "Lập phiếu mượn thành công!");
            resetForm();
        } else {
            JOptionPane.showMessageDialog(view, "Lập phiếu mượn thất bại!", 
                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void processReturn() {
        // Xử lý trả sách tương tự
        // ...
    }
}
```

**Service**:

```java
public class BorrowService {
    private final BorrowSlipDAO borrowSlipDAO;
    private final BorrowDetailDAO borrowDetailDAO;
    private final BookCopyDAO bookCopyDAO;
    private final PenaltyService penaltyService;
    
    public boolean createBorrowSlip(int readerId, List<model.BookCopy> books, 
                                     Date dueDate, int userId) {
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            // 1. Tạo PhieuMuon
            model.BorrowSlip slip = new model.BorrowSlip();
            slip.setReaderId(readerId);
            slip.setUserId(userId);
            slip.setBorrowDate(new Date());
            slip.setDueDate(dueDate);
            slip.setStatus(0); // Đang mượn
            
            int slipId = borrowSlipDAO.insert(slip, conn);
            
            if (slipId == -1) {
                conn.rollback();
                return false;
            }
            
            // 2. Tạo ChiTietMuonTra và cập nhật TrangThai CuonSach
            for (model.BookCopy book : books) {
                model.BorrowDetail detail = new model.BorrowDetail();
                detail.setBorrowSlipId(slipId);
                detail.setBookCopyId(book.getId());
                
                borrowDetailDAO.insert(detail, conn);
                
                // Cập nhật trạng thái sách = 0 (đang mượn)
                bookCopyDAO.updateStatus(book.getId(), 0, conn);
            }
            
            conn.commit();
            return true;
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean returnBook(int detailId, String condition) {
        // Tương tự, xử lý trả sách và tính phạt
        // ...
    }
}
```

---

### 4.6. Lớp thứ 6: PenaltyForm (Quản lý Phạt)

**Mô tả**: `PenaltyForm` hiển thị danh sách phiếu phạt và cho phép ghi nhận đóng tiền phạt.

**Chức năng**:
- Hiển thị danh sách phiếu phạt
- Lọc phiếu phạt theo trạng thái (Đã đóng/Chưa đóng)
- Ghi nhận đóng tiền phạt
- Tìm kiếm phiếu phạt theo độc giả

**Các thành phần chính**:
- **tblPenalty**: JTable - Danh sách phiếu phạt
- **cboFilter**: JComboBox - Lọc theo trạng thái
- **btnMarkPaid**: JButton - Ghi nhận đã đóng tiền
- **lblTotalUnpaid**: JLabel - Tổng công nợ

**Luồng hoạt động**:
1. Load danh sách phiếu phạt từ `PenaltyDAO`
2. Chọn phiếu phạt → Click "Ghi nhận đã đóng"
3. `PenaltyService.markAsPaid()` → Cập nhật trạng thái
4. Refresh bảng

---

### 4.7. Lớp thứ 7: ReportForm (Báo cáo và Thống kê)

**Mô tả**: `ReportForm` cung cấp các báo cáo thống kê về hoạt động của thư viện.

**Chức năng**:
- Báo cáo top sách mượn nhiều
- Báo cáo độc giả tích cực
- Thống kê phạt theo thời gian
- Thống kê tổng quan

**Các thành phần chính**:
- **tblReport**: JTable - Hiển thị kết quả báo cáo
- **cboReportType**: JComboBox - Chọn loại báo cáo
- **txtFromDate, txtToDate**: JTextField - Chọn khoảng thời gian
- **btnGenerate**: JButton - Tạo báo cáo

**Luồng hoạt động**:
1. Chọn loại báo cáo
2. Nhập khoảng thời gian (nếu cần)
3. Click "Tạo báo cáo"
4. `ReportController` gọi `ReportDAO` với loại báo cáo tương ứng
5. Hiển thị kết quả lên bảng

---

### 4.8. Lớp thứ 8: CategoryForm (Quản lý Thể loại)

**Mô tả**: `CategoryForm` quản lý danh mục thể loại sách.

**Chức năng**:
- Thêm thể loại mới
- Sửa thông tin thể loại
- Xóa thể loại (nếu không có sách nào thuộc thể loại)
- Hiển thị danh sách thể loại

**Các thành phần chính**:
- **tblCategory**: JTable - Danh sách thể loại
- **txtTenTheLoai, txtMoTa**: Các trường nhập liệu
- **btnAdd, btnEdit, btnDelete, btnReset**: Các nút chức năng

**Luồng hoạt động**:
Tương tự như `BookForm` và `ReaderForm`, sử dụng `CategoryDAO` để thao tác với bảng `TheLoai`.

---

### 4.9. Lớp thứ 9: UserForm (Quản lý Người dùng)

**Mô tả**: `UserForm` cho phép quản lý (Admin) thêm, sửa, xóa tài khoản người dùng và phân quyền.

**Chức năng**:
- Thêm người dùng mới
- Sửa thông tin người dùng
- Phân quyền vai trò (Quản lý/Thủ thư)
- Kích hoạt/Vô hiệu hóa tài khoản
- Reset mật khẩu

**Các thành phần chính**:
- **tblUser**: JTable - Danh sách người dùng
- **txtTenDangNhap, txtMatKhau, txtHoTen, txtEmail, txtSDT**: Các trường nhập liệu
- **cboVaiTro**: JComboBox - Chọn vai trò
- **chkDangHoatDong**: JCheckBox - Trạng thái hoạt động
- **btnAdd, btnEdit, btnDelete, btnReset, btnResetPassword**: Các nút chức năng

**Luồng hoạt động**:
1. Chỉ Admin mới có quyền truy cập form này
2. Mật khẩu được mã hóa bằng BCrypt trước khi lưu
3. Khi reset mật khẩu → Mật khẩu mới = "123456" (hoặc tự động tạo)

---

### 4.10. Lớp thứ 10: AuditLogForm (Nhật ký Hoạt động)

**Mô tả**: `AuditLogForm` hiển thị nhật ký hoạt động của người dùng trong hệ thống.

**Chức năng**:
- Hiển thị danh sách nhật ký
- Lọc theo người dùng
- Lọc theo loại hành động
- Lọc theo khoảng thời gian
- Tìm kiếm

**Các thành phần chính**:
- **tblAuditLog**: JTable - Danh sách nhật ký
- **cboUser, cboAction**: JComboBox - Lọc
- **txtFromDate, txtToDate**: Khoảng thời gian
- **btnFilter**: JButton - Lọc

**Luồng hoạt động**:
1. Load danh sách nhật ký từ `AuditLogDAO`
2. Chọn điều kiện lọc → Click "Lọc"
3. Hiển thị kết quả

---

### 4.11. Lớp thứ 11: HomeForm (Trang chủ)

**Mô tả**: `HomeForm` là trang dashboard hiển thị thống kê tổng quan về hoạt động của thư viện.

**Chức năng**:
- Hiển thị tổng số đầu sách, cuốn sách, độc giả
- Hiển thị số phiếu mượn đang hoạt động
- Hiển thị số phiếu quá hạn
- Hiển thị tổng công nợ phạt chưa thu
- Biểu đồ thống kê (tùy chọn)

**Các thành phần chính**:
- **lblTotalBooks, lblTotalReaders, lblActiveBorrows, lblOverdue, lblUnpaidPenalty**: Các JLabel hiển thị số liệu

**Luồng hoạt động**:
1. Form khởi tạo → `HomeController` gọi các phương thức thống kê từ `ReportDAO`
2. Hiển thị các số liệu lên label

---

### 4.12. Lớp thứ 12: ChangePasswordForm (Đổi mật khẩu)

**Mô tả**: `ChangePasswordForm` cho phép người dùng thay đổi mật khẩu của mình.

**Chức năng**:
- Nhập mật khẩu cũ
- Nhập mật khẩu mới
- Xác nhận mật khẩu mới
- Kiểm tra và cập nhật mật khẩu

**Các thành phần chính**:
- **txtOldPassword, txtNewPassword, txtConfirmPassword**: JPasswordField
- **btnSave, btnCancel**: Nút lưu và hủy

**Luồng hoạt động**:
1. Nhập mật khẩu cũ
2. Nhập mật khẩu mới và xác nhận
3. `UserController` kiểm tra mật khẩu cũ đúng không
4. Kiểm tra mật khẩu mới khớp với xác nhận
5. Mã hóa mật khẩu mới → Cập nhật vào database

---

## 5. KẾT LUẬN

Hệ thống Quản lý Thư viện được xây dựng hoàn chỉnh với đầy đủ các chức năng cần thiết cho một thư viện hiện đại. Chương trình được thiết kế theo mô hình MVC, đảm bảo tính module hóa, dễ bảo trì và mở rộng.

### Ưu điểm:
- Giao diện trực quan, thân thiện với người dùng
- Phân quyền rõ ràng giữa các vai trò
- Tự động hóa các quy trình mượn trả, tính phạt
- Có hệ thống nhật ký hoạt động để kiểm toán
- Sử dụng mã hóa mật khẩu để bảo mật
- Database được thiết kế chuẩn hóa, tối ưu

### Hướng phát triển:
- Thêm chức năng in phiếu mượn, phiếu phạt
- Thêm biểu đồ thống kê trực quan
- Tích hợp máy quét mã vạch
- Gửi email thông báo sắp đến hạn trả sách
- Xây dựng web/mobile app để độc giả tra cứu sách online

---

**NGƯỜI THỰC HIỆN**  
[Tên sinh viên]  
[Mã sinh viên]  
[Lớp]

**GIẢNG VIÊN HƯỚNG DẪN**  
[Tên giảng viên]

---

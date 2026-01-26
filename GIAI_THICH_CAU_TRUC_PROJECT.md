# GIẢI THÍCH CẤU TRÚC PROJECT VÀ LUỒNG XỬ LÝ
# HỆ THỐNG QUẢN LÝ THƯ VIỆN

---

## 📁 1. CẤU TRÚC THƯ MỤC TỔNG QUAN

```
Library_Management/
│
├── librarymanagement_1/              # Thư mục gốc của project
│   ├── src/                          # Source code chính
│   │   ├── model/                    # Các lớp Model (Entity/POJO)
│   │   ├── view/                     # Các lớp View (Giao diện Swing)
│   │   ├── controller/               # Các lớp Controller (Xử lý logic điều khiển)
│   │   ├── dao/                      # Data Access Object (Truy xuất database)
│   │   ├── service/                  # Business Logic Layer
│   │   ├── util/                     # Các tiện ích (utility classes)
│   │   └── main/                     # Entry point của ứng dụng
│   │
│   ├── build/                        # Thư mục chứa file compiled (.class)
│   ├── database/                     # File SQL để tạo database
│   ├── lib/                          # Thư viện bên ngoài (MySQL Connector)
│   └── nbproject/                    # Cấu hình NetBeans project
│
├── QUI_TRINH_NGHIEP_VU.md           # Tài liệu quy trình nghiệp vụ
└── README.md                         # Hướng dẫn cài đặt và sử dụng
```

---

## 📂 2. GIẢI THÍCH CHI TIẾT TỪNG THƯ MỤC

### 2.1. 📦 Thư mục `model/` - Lớp Entity/POJO

**Mục đích**: Định nghĩa cấu trúc dữ liệu, ánh xạ với bảng trong database

**Các file và nhiệm vụ**:

#### 1. `User.java` - Người dùng hệ thống
```
┌─────────────────────────────────────┐
│ User (NguoiDung)                    │
├─────────────────────────────────────┤
│ - id: int                           │
│ - username: String                  │
│ - password: String (đã hash)        │
│ - fullName: String                  │
│ - email: String                     │
│ - phoneNumber: String               │
│ - roleId: int (1=Admin, 2=Thủ thư)  │
│ - isActive: boolean                 │
│ - createdAt: Timestamp              │
└─────────────────────────────────────┘
```
**Nhiệm vụ**: Lưu thông tin tài khoản người dùng, xác thực đăng nhập

#### 2. `Role.java` - Vai trò
```
┌─────────────────────────────────────┐
│ Role (VaiTro)                       │
├─────────────────────────────────────┤
│ - maVaiTro: int                     │
│ - tenVaiTro: String (Admin/Thủ thư) │
│ - moTa: String                      │
└─────────────────────────────────────┘
```
**Nhiệm vụ**: Phân quyền người dùng

#### 3. `Book.java` - Đầu sách
```
┌─────────────────────────────────────┐
│ Book (DauSach)                      │
├─────────────────────────────────────┤
│ - id: int                           │
│ - title: String (tựa đề)            │
│ - author: String (tác giả)          │
│ - publisher: String (NXB)           │
│ - publishYear: int                  │
│ - categoryId: int                   │
│ - description: String               │
│ - categoryName: String (transient)  │
│ - quantity: int (transient)         │
└─────────────────────────────────────┘
```
**Nhiệm vụ**: Lưu thông tin CHUNG về một đầu sách (metadata)

#### 4. `BookCopy.java` - Cuốn sách vật lý
```
┌─────────────────────────────────────┐
│ BookCopy (CuonSach)                 │
├─────────────────────────────────────┤
│ - maCuonSach: int                   │
│ - maDauSach: int (FK → Book)        │
│ - maVach: String (barcode)          │
│ - trangThai: int (1=Sẵn, 2=Mượn)    │
│ - tinhTrang: String (Mới/Cũ)        │
│ - viTriKe: String                   │
│ - giaTien: double                   │
│ - ngayNhap: Date                    │
└─────────────────────────────────────┘
```
**Nhiệm vụ**: Quản lý TỪNG CUỐN sách cụ thể (physical copy)

#### 5. `Reader.java` - Độc giả
```
┌─────────────────────────────────────┐
│ Reader (DocGia)                     │
├─────────────────────────────────────┤
│ - maDocGia: int                     │
│ - maThe: String (mã thẻ)            │
│ - hoTen: String                     │
│ - email: String                     │
│ - soDienThoai: String               │
│ - diaChi: String                    │
│ - gioHanMuon: int (mặc định 5)      │
│ - ngayHetHan: Date                  │
│ - diemViPham: int                   │
│ - biKhoa: boolean                   │
└─────────────────────────────────────┘
```
**Nhiệm vụ**: Quản lý thông tin độc giả, theo dõi vi phạm

#### 6. `BorrowSlip.java` - Phiếu mượn
```
┌─────────────────────────────────────┐
│ BorrowSlip (PhieuMuon)              │
├─────────────────────────────────────┤
│ - maPhieuMuon: int                  │
│ - maDocGia: int (FK)                │
│ - maNguoiDung: int (thủ thư lập)    │
│ - ngayMuon: Date                    │
│ - hanTra: Date                      │
│ - ghiChu: String                    │
│ - trangThai: int (0=Mượn, 1=Trả)    │
└─────────────────────────────────────┘
```
**Nhiệm vụ**: Header của một lần mượn sách

#### 7. `BorrowDetail.java` - Chi tiết mượn trả
```
┌─────────────────────────────────────┐
│ BorrowDetail (ChiTietMuonTra)       │
├─────────────────────────────────────┤
│ - maChiTiet: int                    │
│ - maPhieuMuon: int (FK)             │
│ - maCuonSach: int (FK)              │
│ - ngayTra: Date (NULL nếu chưa trả) │
│ - tinhTrangTra: String              │
│ - ghiChu: String                    │
└─────────────────────────────────────┘
```
**Nhiệm vụ**: Detail của từng cuốn sách trong phiếu mượn

#### 8. `Penalty.java` - Phiếu phạt
```
┌─────────────────────────────────────┐
│ Penalty (PhieuPhat)                 │
├─────────────────────────────────────┤
│ - maPhieuPhat: int                  │
│ - maChiTiet: int (FK, optional)     │
│ - maDocGia: int (FK)                │
│ - lyDo: String                      │
│ - soTien: double                    │
│ - daDongTien: boolean               │
│ - ngayTao: Date                     │
└─────────────────────────────────────┘
```
**Nhiệm vụ**: Quản lý các khoản phạt (trễ hạn, hư hỏng sách)

#### 9. `Category.java` - Thể loại sách
```
┌─────────────────────────────────────┐
│ Category (TheLoai)                  │
├─────────────────────────────────────┤
│ - id: int                           │
│ - name: String                      │
│ - description: String               │
└─────────────────────────────────────┘
```
**Nhiệm vụ**: Phân loại sách (Văn học, Khoa học, Lịch sử...)

#### 10. `AuditLog.java` - Nhật ký hoạt động
```
┌─────────────────────────────────────┐
│ AuditLog (NhatKyHoatDong)           │
├─────────────────────────────────────┤
│ - maNhatKy: int                     │
│ - maNguoiDung: int (FK)             │
│ - hanhDong: String                  │
│ - tenBang: String                   │
│ - maBanGhi: int                     │
│ - moTaChiTiet: String               │
│ - diaChiIP: String                  │
│ - thoiGian: Timestamp               │
└─────────────────────────────────────┘
```
**Nhiệm vụ**: Ghi lại mọi thao tác của người dùng để audit

---

### 2.2. 🎨 Thư mục `view/` - Giao diện người dùng

**Mục đích**: Hiển thị giao diện Swing, nhận input từ người dùng

**Các file và nhiệm vụ**:

#### 1. `LoginForm.java` + `LoginForm.form`
**Chức năng**: Màn hình đăng nhập
**Components**:
- `txtUsername` - Nhập tên đăng nhập
- `txtPassword` - Nhập mật khẩu (PasswordField)
- `chkShowPassword` - Hiện/ẩn mật khẩu
- `btnLogin` - Nút đăng nhập
- `btnExit` - Thoát ứng dụng

#### 2. `MainForm.java` + `MainForm.form`
**Chức năng**: Màn hình chính với sidebar menu
**Components**:
- `pnlSidebar` - Panel chứa menu dọc
- `btnHome, btnBook, btnReader, btnBorrow, btnPenalty, btnReport, btnCategory, btnUser, btnAuditLog` - Các nút điều hướng
- `lblUserInfo` - Hiển thị thông tin user đang đăng nhập
- `btnLogout` - Đăng xuất
- `pnlDesktop` - Khu vực hiển thị nội dung (CardLayout)

#### 3. `HomeForm.java` + `HomeForm.form`
**Chức năng**: Dashboard với thống kê tổng quan
**Components**:
- `lblTotalBooks, lblTotalReaders, lblActiveBorrows, lblOverdue` - Các label hiển thị số liệu
- `tblOverdueBooks` - Bảng sách quá hạn
- `tblDueSoonBooks` - Bảng sách sắp đến hạn
- `tblUnpaidPenalties` - Bảng phạt chưa đóng

#### 4. `BookForm.java` + `BookForm.form`
**Chức năng**: Quản lý sách
**Components**:
- **Input Panel**: `txtMaDauSach, txtTuaDe, txtTacGia, txtNXB, txtNamXB, cboTheLoai, txtMoTa, txtSoLuong, txtGiaTien`
- **Button Panel**: `btnAdd, btnEdit, btnDelete, btnReset`
- **Search Panel**: `txtSearch, btnSearch, btnViewAll`
- **Table**: `tblBook` - Hiển thị danh sách đầu sách

#### 5. `ReaderForm.java` + `ReaderForm.form`
**Chức năng**: Quản lý độc giả
**Components**:
- **Input Panel**: `txtMaThe, txtHoTen, txtEmail, txtSDT, txtDiaChi, txtNgayHetHan, chkBiKhoa`
- **Button Panel**: `btnAdd, btnEdit, btnDelete, btnReset, btnExtend`
- **Search Panel**: `txtSearch, btnSearch, btnViewAll`
- **Table**: `tblReader`

#### 6. `BorrowForm.java` + `BorrowForm.form`
**Chức năng**: Xử lý mượn/trả sách
**Components**:
- **Reader Panel**: `txtMaThe, btnCheckReader, lblTenDocGia, lblThongTinThe`
- **Book Panel**: `txtBarcode, btnAddBook, txtDueDate`
- **Cart Table**: `tblCart` - Danh sách sách đang chọn mượn
- **Action Buttons**: `btnBorrow, btnReturn, btnReset`

#### 7. `PenaltyForm.java` + `PenaltyForm.form`
**Chức năng**: Quản lý phạt
**Components**:
- **Reader Panel**: `txtMaThe, btnCheckReader`
- **Penalty Panel**: `txtLyDo, txtSoTien`
- **Buttons**: `btnAdd, btnMarkPaid, btnSearch`
- **Table**: `tblPenalty` - Danh sách phiếu phạt

#### 8. `ReportForm.java` + `ReportForm.form`
**Chức năng**: Báo cáo và thống kê
**Components**:
- **Stats Panel**: `lblTotalBooks, lblTotalReaders, lblBorrowedBooks`
- **Tables**: `tblTopReaders, tblRecentActivity, tblBorrowingList`
- **Buttons**: `btnRefresh, btnExport`

#### 9. `CategoryForm.java` + `CategoryForm.form`
**Chức năng**: Quản lý thể loại sách
**Components**:
- **Input**: `txtTenTheLoai, txtMoTa`
- **Buttons**: `btnAdd, btnEdit, btnDelete, btnReset`
- **Table**: `tblCategory`

#### 10. `UserForm.java` + `UserForm.form`
**Chức năng**: Quản lý người dùng (Admin only)
**Components**:
- **Input**: `txtTenDangNhap, txtMatKhau, txtHoTen, txtEmail, txtSDT, cboVaiTro, chkDangHoatDong`
- **Buttons**: `btnAdd, btnEdit, btnDelete, btnResetPassword, btnReset`
- **Search**: `txtSearch, btnSearch2, btnViewAll, lblResultCount`
- **Table**: `tblUser`

#### 11. `AuditLogForm.java` + `AuditLogForm.form`
**Chức năng**: Xem nhật ký hoạt động
**Components**:
- **Filter**: `cboUser, cboAction, cboTable, txtFromDate, txtToDate`
- **Buttons**: `btnFilter, btnRefresh, btnExport`
- **Table**: `tblAuditLog`

---

### 2.3. 🎮 Thư mục `controller/` - Xử lý logic điều khiển

**Mục đích**: Nhận event từ View, gọi Service/DAO, cập nhật View

**Các file và nhiệm vụ**:

#### 1. `LoginController.java`
**Nhiệm vụ**: Xử lý đăng nhập
**Methods chính**:
- `initController()` - Gắn event listeners
- `login()` - Xử lý đăng nhập:
  1. Lấy username/password từ form
  2. Gọi `AuthService.login()`
  3. Nếu thành công → mở `MainForm`, ghi log
  4. Nếu thất bại → hiển thị lỗi
- `exitApplication()` - Thoát ứng dụng

#### 2. `MainController.java`
**Nhiệm vụ**: Điều hướng giữa các màn hình
**Methods chính**:
- `initController()` - Gắn listeners cho các nút menu
- `showHome()` - Hiển thị HomeForm
- `showBook()` - Hiển thị BookForm
- `showReader()` - Hiển thị ReaderForm
- `showBorrow()` - Hiển thị BorrowForm
- `showPenalty()` - Hiển thị PenaltyForm
- `showReport()` - Hiển thị ReportForm
- `showCategory()` - Hiển thị CategoryForm
- `showUser()` - Hiển thị UserForm (kiểm tra quyền Admin)
- `showAuditLog()` - Hiển thị AuditLogForm (kiểm tra quyền Admin)
- `logout()` - Đăng xuất, quay về LoginForm

#### 3. `BookController.java`
**Nhiệm vụ**: Quản lý sách
**Methods chính**:
- `initView()` - Load categories, load data
- `initController()` - Gắn event listeners
- `loadTableData(List<Book>)` - Hiển thị sách lên JTable
- `addBook()` - Thêm sách mới:
  1. Validate input
  2. Gọi `BookService.addBook(book, quantity, price)`
  3. Refresh bảng
- `editBook()` - Sửa thông tin sách
- `deleteBook()` - Xóa sách (kiểm tra điều kiện)
- `searchBook()` - Tìm kiếm sách
- `fillForm()` - Điền thông tin từ bảng vào form

#### 4. `ReaderController.java`
**Nhiệm vụ**: Quản lý độc giả
**Methods chính**:
- `loadData()` - Load danh sách độc giả
- `addReader()` - Thêm độc giả:
  1. Validate input
  2. Tự động tạo mã thẻ
  3. Gọi `ReaderService.addReader()`
- `updateReader()` - Cập nhật thông tin
- `deleteReader()` - Xóa độc giả (kiểm tra điều kiện)
- `searchReader()` - Tìm kiếm độc giả
- `extendCard()` - Gia hạn thẻ

#### 5. `BorrowController.java`
**Nhiệm vụ**: Xử lý mượn/trả sách
**Methods chính**:
- `checkReader()` - Kiểm tra mã độc giả:
  1. Lấy thông tin từ `ReaderDAO`
  2. Kiểm tra: thẻ còn hạn? không bị khóa?
  3. Hiển thị thông tin độc giả
- `addBookToCart()` - Thêm sách vào giỏ:
  1. Kiểm tra mã vạch
  2. Kiểm tra sách có sẵn?
  3. Thêm vào ArrayList cart
  4. Cập nhật JTable
- `borrowBooks()` - Cho mượn:
  1. Validate: có độc giả? có sách? có hạn trả?
  2. Gọi `BorrowService.borrowBooks()`
  3. Service xử lý transaction
- `returnBook()` - Trả sách:
  1. Nhập mã vạch
  2. Gọi `BorrowService.returnBook()`
  3. Service tự động tính phạt trễ hạn
  4. Hiển thị thông báo (kèm tiền phạt nếu có)

#### 6. `PenaltyController.java`
**Nhiệm vụ**: Quản lý phạt
**Methods chính**:
- `loadData()` - Load danh sách phạt
- `addPenalty()` - Thêm phiếu phạt thủ công
- `markAsPaid()` - Ghi nhận đã đóng tiền
- `searchPenalty()` - Tìm kiếm phạt

#### 7. `ReportController.java`
**Nhiệm vụ**: Báo cáo thống kê
**Methods chính**:
- `loadDashboardStats()` - Load số liệu tổng quan
- `loadTables()` - Load các bảng thống kê
- `exportRecent()` - Xuất báo cáo hoạt động gần đây
- `exportTopReaders()` - Xuất top độc giả

#### 8. `CategoryController.java`
**Nhiệm vụ**: Quản lý thể loại
**Methods chính**:
- `loadData()` - Load thể loại
- `addCategory()` - Thêm thể loại
- `updateCategory()` - Sửa thể loại
- `deleteCategory()` - Xóa thể loại (kiểm tra có sách không)

#### 9. `UserController.java`
**Nhiệm vụ**: Quản lý người dùng
**Methods chính**:
- `loadData()` - Load danh sách user, cập nhật lblResultCount
- `searchUser()` - Tìm kiếm user
- `addUser()` - Thêm user:
  1. Validate email
  2. Kiểm tra username tồn tại
  3. Hash password (SHA-256)
  4. Gọi `UserDAO.insertUser()`
- `updateUser()` - Cập nhật thông tin (không update password)
- `deleteUser()` - Xóa user (soft delete)
- `resetPassword()` - Reset password về mặc định

#### 10. `HomeController.java`
**Nhiệm vụ**: Dashboard
**Methods chính**:
- `loadData()` - Load tất cả thống kê
- `loadStatistics()` - Tổng số sách, độc giả, mượn
- `loadOverdueBooks()` - Sách quá hạn
- `loadDueSoonBooks()` - Sách sắp đến hạn
- `loadUnpaidPenalties()` - Phạt chưa đóng

---

### 2.4. 🗄️ Thư mục `dao/` - Truy xuất Database

**Mục đích**: Thực hiện các câu SQL, CRUD với database

**Các file và nhiệm vụ**:

#### 1. `UserDAO.java`
**Methods**:
- `getUserByUsername(String)` → User - Lấy user theo username
- `getAllUsers()` → List<User> - Lấy tất cả user
- `searchUser(String)` → List<User> - Tìm kiếm user (LIKE %keyword%)
- `insertUser(User)` → boolean - INSERT INTO NguoiDung
- `updateUser(User)` → boolean - UPDATE NguoiDung
- `deleteUser(int)` → boolean - Soft delete (set DangHoatDong = 0)
- `resetPassword(int, String)` → boolean - UPDATE MatKhau
- `isUsernameExist(String)` → boolean - Kiểm tra trùng username

#### 2. `BookDAO.java`
**Methods**:
- `getAllBooks()` → List<Book> - SELECT với JOIN TheLoai, đếm số cuốn sách
- `insertBook(Book)` → int - INSERT INTO DauSach, return ID
- `insertCopies(int, int, double)` → boolean - INSERT nhiều CuonSach
- `updateBook(Book)` → boolean - UPDATE DauSach
- `deleteBook(int)` → boolean - DELETE FROM DauSach (kiểm tra constraint)
- `searchBooks(String)` → List<Book> - LIKE trên nhiều trường
- `getBarcodes(int)` → List<String> - Lấy danh sách mã vạch của đầu sách

#### 3. `BookCopyDAO.java`
**Methods**:
- `getBookCopyByBarcode(String)` → BookCopy - SELECT by MaVach
- `updateStatus(int, int)` → boolean - UPDATE TrangThai (1=Sẵn, 2=Mượn)

#### 4. `ReaderDAO.java`
**Methods**:
- `getAllReaders()` → List<Reader> - SELECT * FROM DocGia
- `getReaderByCardId(String)` → Reader - Tìm theo MaThe
- `addReader(Reader)` → boolean - INSERT INTO DocGia
- `updateReader(Reader)` → boolean - UPDATE DocGia
- `deleteReader(String)` → boolean - DELETE FROM DocGia
- `searchReader(String)` → List<Reader> - LIKE MaThe, HoTen, SoDienThoai
- `generateNextMaThe()` → String - Tạo mã thẻ: DG{year}{0001}
- `getCurrentBorrowCount(int)` → int - Đếm số sách đang mượn

#### 5. `BorrowSlipDAO.java`
**Methods**:
- `insert(BorrowSlip)` → int - INSERT INTO PhieuMuon, return ID
- `getBorrowSlip(int)` → BorrowSlip - SELECT by MaPhieuMuon

#### 6. `BorrowDetailDAO.java`
**Methods**:
- `insert(BorrowDetail)` → boolean - INSERT INTO ChiTietMuonTra
- `updateReturn(int, Date, String)` → boolean - UPDATE khi trả sách
- `isBookBorrowed(int)` → boolean - Kiểm tra sách có đang mượn không
- `getBorrowDetailByCopy(int)` → BorrowDetail - Lấy chi tiết theo MaCuonSach

#### 7. `PenaltyDAO.java`
**Methods**:
- `getAllPenalties()` → List<Penalty> - SELECT với JOIN DocGia
- `insert(Penalty)` → boolean - INSERT INTO PhieuPhat
- `updateStatus(int, boolean)` → boolean - Cập nhật DaDongTien
- `getTotalUnpaidPenalty(int)` → double - SUM(SoTien) WHERE DaDongTien = 0
- `searchPenalties(String)` → List<Penalty> - Tìm kiếm phạt

#### 8. `CategoryDAO.java`
**Methods**:
- `getAllCategories()` → List<Category> - SELECT * FROM TheLoai
- `insertCategory(Category)` → boolean - INSERT INTO TheLoai
- `updateCategory(Category)` → boolean - UPDATE TheLoai
- `deleteCategory(int)` → boolean - DELETE FROM TheLoai
- `isCategoryExist(String)` → boolean - Kiểm tra trùng tên

#### 9. `RoleDAO.java`
**Methods**:
- `getAllRoles()` → List<Role> - SELECT * FROM VaiTro
- `getRoleById(int)` → Role - Lấy vai trò theo ID

#### 10. `ReportDAO.java`
**Methods**:
- `countTotalBookTitles()` → int - COUNT đầu sách
- `countTotalBookCopies()` → int - COUNT cuốn sách
- `countTotalReaders()` → int - COUNT độc giả
- `countBorrowedBooks()` → int - COUNT sách đang mượn
- `countOverdueBorrows()` → int - COUNT phiếu quá hạn
- `getTopReaders(int)` → List<Object[]> - Top độc giả mượn nhiều
- `getRecentBorrows(int)` → List<Object[]> - Hoạt động mượn gần đây

#### 11. `AuditLogDAO.java`
**Methods**:
- `insertLog(...)` → boolean - INSERT INTO NhatKyHoatDong
- `getAllLogs()` → List<AuditLog> - SELECT * ORDER BY ThoiGian DESC
- `getLogsByUser(int)` → List<AuditLog> - Filter theo user

---

### 2.5. ⚙️ Thư mục `service/` - Business Logic

**Mục đích**: Xử lý logic nghiệp vụ phức tạp, kết hợp nhiều DAO

**Các file và nhiệm vụ**:

#### 1. `AuthService.java`
**Nhiệm vụ**: Xác thực và quản lý session
**Methods**:
- `login(String, String)` → boolean:
  1. Gọi `UserDAO.getUserByUsername()`
  2. So sánh password bằng `PasswordUtil.checkPassword()`
  3. Nếu đúng: lưu `currentUser` (static), ghi log
  4. Trả về true/false
- `logout()` - Ghi log, set currentUser = null
- `getCurrentUser()` → User - Lấy user đang đăng nhập
- `isLoggedIn()` → boolean - Kiểm tra có user đang đăng nhập

#### 2. `BookService.java`
**Nhiệm vụ**: Logic quản lý sách
**Methods**:
- `getAllBooks()` → List<Book> - Delegate đến BookDAO
- `addBook(Book, int, double)` → boolean:
  1. INSERT đầu sách (BookDAO.insertBook)
  2. INSERT nhiều cuốn sách (BookDAO.insertCopies)
  3. Ghi log audit
  4. Return true/false
- `updateBookInfo(Book)` → boolean - UPDATE + log
- `deleteBook(int)` → boolean:
  1. Kiểm tra còn cuốn sách nào không
  2. Nếu không → DELETE, ghi log
- `searchBooks(String)` → List<Book>

#### 3. `BorrowService.java`
**Nhiệm vụ**: Xử lý mượn/trả sách (transaction)
**Methods**:
- `borrowBooks(int, List<BookCopy>, Date)` → boolean:
  ```
  BEGIN TRANSACTION
    1. INSERT PhieuMuon → lấy maPhieuMuon
    2. FOR EACH book IN list:
       - INSERT ChiTietMuonTra(maPhieuMuon, maCuonSach)
       - UPDATE CuonSach SET TrangThai = 2 (đang mượn)
    3. Ghi log audit
  COMMIT / ROLLBACK
  ```
- `returnBook(String barcode)` → String:
  ```
  1. Lấy BookCopy by barcode
  2. Kiểm tra TrangThai = 2 (đang mượn)?
  3. Lấy BorrowDetail (chưa trả)
  4. UPDATE ChiTietMuonTra SET NgayTra = NOW()
  5. UPDATE CuonSach SET TrangThai = 1 (có sẵn)
  6. Tính số ngày trễ: ngayTra - hanTra
  7. Nếu trễ > 0:
     - Tạo PhieuPhat với SoTien = soNgayTre * 5000đ
  8. Ghi log
  9. Return message "Trả sách thành công. Tiền phạt: XXX"
  ```

#### 4. `ReaderService.java`
**Nhiệm vụ**: Logic quản lý độc giả
**Methods**:
- `getAllReaders()` → List<Reader>
- `addReader(Reader)` → boolean - INSERT + log
- `updateReader(Reader)` → boolean - UPDATE + log
- `deleteReader(String, String)` → boolean - Kiểm tra điều kiện + DELETE + log
- `generateNextMaThe()` → String - Tạo mã thẻ tự động
- `findReaderByCardId(String)` → Reader
- `getCurrentBorrowCount(int)` → int

#### 5. `PenaltyService.java`
**Nhiệm vụ**: Quản lý phạt
**Methods**:
- `getAllPenalties()` → List<Penalty>
- `createPenalty(int, String, double)` → boolean - INSERT + log
- `payPenalty(int)` → boolean - UPDATE DaDongTien = 1 + log
- `getTotalUnpaidPenalty(int)` → double - Tính tổng công nợ
- `searchPenalties(String)` → List<Penalty>

#### 6. `ReportService.java`
**Nhiệm vụ**: Cung cấp dữ liệu thống kê
**Methods**:
- `getTotalBooks()` → int
- `getTotalReaders()` → int
- `getBorrowedBooks()` → int
- `getOverdueCount()` → int
- `getTopReaders()` → List<Object[]>
- `getRecentActivity()` → List<Object[]>
- `getBorrowingList()` → List<Object[]>
- `getOverdueBooks()` → List<Object[]>
- `getDueSoonBooks()` → List<Object[]>
- `getUnpaidPenalties()` → List<Object[]>

#### 7. `AuditService.java`
**Nhiệm vụ**: Ghi nhật ký hoạt động
**Methods**:
- `logAction(int, String, String, int, String)` → boolean - Ghi log
- `getAllLogs()` → List<AuditLog>
- `getLogsByUser(int)` → List<AuditLog>

---

### 2.6. 🛠️ Thư mục `util/` - Các tiện ích

**Mục đích**: Cung cấp các hàm tiện ích dùng chung

#### 1. `DBConnection.java` - Singleton Database Connection
**Nhiệm vụ**: Quản lý kết nối MySQL
```java
// Singleton pattern
private static DBConnection instance;

public static DBConnection getInstance() {
    if (instance == null) {
        instance = new DBConnection();
    }
    return instance;
}

public Connection getConnection() {
    // jdbc:mysql://localhost:3306/db_quanlythuvien
    return DriverManager.getConnection(DB_URL, USER, PASSWORD);
}
```
**Config**:
- Server: `localhost:3306`
- Database: `db_quanlythuvien`
- User: `root`
- Password: `nhan123`

#### 2. `PasswordUtil.java` - Mã hóa mật khẩu
**Methods**:
- `hashPassword(String)` → String:
  ```java
  // Sử dụng SHA-256
  MessageDigest md = MessageDigest.getInstance("SHA-256");
  byte[] hash = md.digest(password.getBytes());
  // Convert byte[] sang hex string
  return hexString;
  ```
- `checkPassword(String plain, String hashed)` → boolean:
  ```java
  return hashPassword(plain).equals(hashed);
  ```

#### 3. `DateUtil.java` - Xử lý ngày tháng
**Methods**:
- `parseDate(String)` → Date - Parse "dd/MM/yyyy" → Date
- `formatDate(Date)` → String - Format Date → "dd/MM/yyyy"
- `formatDateTime(Date)` → String - Format Date → "dd/MM/yyyy HH:mm:ss"
- `addDays(Date, int)` → Date - Cộng số ngày
- `daysBetween(Date, Date)` → long - Tính số ngày giữa 2 date
- `isExpired(Date)` → boolean - Kiểm tra đã hết hạn chưa

#### 4. `ValidateUtil.java` - Validation
**Methods**:
- `isEmpty(String)` → boolean - Kiểm tra null hoặc rỗng
- `isEmail(String)` → boolean - Regex email
- `isPhone(String)` → boolean - Regex SĐT Việt Nam
- `isUsername(String)` → boolean - Regex username (3-20 ký tự)
- `isInteger(String)` → boolean - Kiểm tra số nguyên
- `isDouble(String)` → boolean - Kiểm tra số thực
- `isInRange(int, int, int)` → boolean - Kiểm tra trong khoảng

#### 5. `Constants.java` - Hằng số
**Nội dung**:
```java
// Trạng thái sách
public static final int BOOK_STATUS_AVAILABLE = 1;
public static final int BOOK_STATUS_BORROWED = 2;
public static final int BOOK_STATUS_LOST = 3;
public static final int BOOK_STATUS_DAMAGED = 4;

// Quy định mượn sách
public static final int DEFAULT_BORROW_DAYS = 14;
public static final int MAX_BOOKS_PER_BORROW = 5;
public static final int MAX_BORROW_LIMIT = 5;
public static final int VIOLATION_LOCK_THRESHOLD = 10;

// Phí phạt
public static final double PENALTY_PER_DAY = 5000.0;
public static final double PENALTY_DAMAGED_RATE = 0.5; // 50% giá sách
public static final double PENALTY_LOST_RATE = 1.0;    // 100% giá sách

// Vai trò
public static final int ROLE_ADMIN = 1;
public static final int ROLE_LIBRARIAN = 2;

// Format
public static final String DATE_FORMAT = "dd/MM/yyyy";
public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
```

---

### 2.7. 🚀 Thư mục `main/` - Entry Point

#### `Main.java`
**Nhiệm vụ**: Khởi chạy ứng dụng
```java
public static void main(String[] args) {
    // Set Look and Feel (Windows, Nimbus, Metal...)
    UIManager.setLookAndFeel(...);
    
    // Khởi chạy LoginForm
    SwingUtilities.invokeLater(() -> {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    });
}
```

---

## 🔄 3. LUỒNG XỬ LÝ CHI TIẾT

### 3.1. 🔐 LUỒNG ĐĂNG NHẬP

```
┌──────────────┐
│ Main.java    │ → new LoginForm() → setVisible(true)
└──────┬───────┘
       │
       ↓
┌────────────────────────────────────────────────────┐
│ LoginForm.java (VIEW)                              │
│ • User nhập username, password                     │
│ • Click nút "ĐĂNG NHẬP"                            │
└────────────┬───────────────────────────────────────┘
             │ ActionEvent
             ↓
┌────────────────────────────────────────────────────┐
│ LoginController.login()                            │
│ 1. String username = view.getTxtUsername().getText()│
│ 2. String password = view.getTxtPassword().getText()│
│ 3. Validate: không rỗng?                           │
└────────────┬───────────────────────────────────────┘
             │ Gọi Service
             ↓
┌────────────────────────────────────────────────────┐
│ AuthService.login(username, password)              │
│ 1. User user = userDAO.getUserByUsername(username) │
└────────────┬───────────────────────────────────────┘
             │ Truy vấn DB
             ↓
┌────────────────────────────────────────────────────┐
│ UserDAO.getUserByUsername(username)                │
│ SQL: SELECT * FROM NguoiDung                       │
│      WHERE TenDangNhap = ? AND DangHoatDong = 1    │
└────────────┬───────────────────────────────────────┘
             │ Return User object
             ↓
┌────────────────────────────────────────────────────┐
│ AuthService (tiếp)                                 │
│ 2. if (user != null) {                             │
│      boolean match = PasswordUtil.checkPassword(   │
│          password, user.getPassword()              │
│      )                                             │
│    }                                               │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ PasswordUtil.checkPassword(plain, hashed)          │
│ 1. String plainHashed = hashPassword(plain)        │
│ 2. return plainHashed.equals(hashed)               │
└────────────┬───────────────────────────────────────┘
             │ Return true/false
             ↓
┌────────────────────────────────────────────────────┐
│ AuthService (tiếp)                                 │
│ 3. if (match) {                                    │
│      currentUser = user  // Lưu session            │
│      auditService.logAction(...) // Ghi log        │
│      return true                                   │
│    }                                               │
└────────────┬───────────────────────────────────────┘
             │ Return boolean
             ↓
┌────────────────────────────────────────────────────┐
│ LoginController (tiếp)                             │
│ 4. if (success) {                                  │
│      // Kiểm tra user.isActive()                   │
│      // Mở MainForm                                │
│      MainForm mainForm = new MainForm()            │
│      mainForm.getController().setCurrentUser(user) │
│      mainForm.setVisible(true)                     │
│      view.dispose()  // Đóng LoginForm             │
│    } else {                                        │
│      JOptionPane.showMessageDialog("Sai mật khẩu") │
│    }                                               │
└────────────────────────────────────────────────────┘
```

---

### 3.2. ➕ LUỒNG THÊM NGƯỜI DÙNG

```
┌────────────────────────────────────────────────────┐
│ MainForm → Click "NGƯỜI DÙNG"                      │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ MainController.showUserForm()                      │
│ 1. Kiểm tra quyền: currentUser.getRoleId() == 1?  │
│ 2. if (Admin) {                                    │
│      userForm = new UserForm()                     │
│      pnlDesktop.add(userForm)                      │
│    }                                               │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ UserForm (VIEW)                                    │
│ • User nhập: username, password, fullName,         │
│   email, phone, chọn role, chọn active             │
│ • Click "THÊM"                                     │
└────────────┬───────────────────────────────────────┘
             │ ActionEvent
             ↓
┌────────────────────────────────────────────────────┐
│ UserController.addUser()                           │
│ 1. Lấy dữ liệu từ form:                            │
│    String username = view.getTxtUsername()         │
│    String password = view.getTxtPassword()         │
│    String fullName = view.getTxtHoTen()            │
│    String email = view.getTxtEmail()               │
│    int roleId = view.getCboVaiTro().getSelectedIndex() + 1│
│    boolean active = view.getChkDangHoatDong()      │
│                                                    │
│ 2. Validate:                                       │
│    if (username.isEmpty()) → error                 │
│    if (password.isEmpty()) → error                 │
│    if (!ValidateUtil.isEmail(email)) → error       │
└────────────┬───────────────────────────────────────┘
             │ Kiểm tra username
             ↓
┌────────────────────────────────────────────────────┐
│ UserDAO.isUsernameExist(username)                  │
│ SQL: SELECT COUNT(*) FROM NguoiDung                │
│      WHERE TenDangNhap = ?                         │
│ Return: true/false                                 │
└────────────┬───────────────────────────────────────┘
             │ Return boolean
             ↓
┌────────────────────────────────────────────────────┐
│ UserController (tiếp)                              │
│ 3. if (exist) {                                    │
│      JOptionPane.show("Username đã tồn tại")       │
│      return                                        │
│    }                                               │
│                                                    │
│ 4. Hash password:                                  │
│    String hashed = PasswordUtil.hashPassword(password)│
└────────────┬───────────────────────────────────────┘
             │ Gọi DAO
             ↓
┌────────────────────────────────────────────────────┐
│ PasswordUtil.hashPassword(password)                │
│ • Sử dụng MessageDigest SHA-256                    │
│ • Convert byte[] sang hex string                   │
│ Return: "a665a45920422f9d417e..." (64 chars)       │
└────────────┬───────────────────────────────────────┘
             │ Return hashed
             ↓
┌────────────────────────────────────────────────────┐
│ UserController (tiếp)                              │
│ 5. Tạo User object:                                │
│    User user = new User()                          │
│    user.setUsername(username)                      │
│    user.setPassword(hashed)  // Password đã hash   │
│    user.setFullName(fullName)                      │
│    user.setEmail(email)                            │
│    user.setPhoneNumber(phone)                      │
│    user.setRoleId(roleId)                          │
│    user.setActive(active)                          │
│                                                    │
│ 6. Gọi DAO:                                        │
│    boolean success = userDAO.insertUser(user)      │
└────────────┬───────────────────────────────────────┘
             │ INSERT
             ↓
┌────────────────────────────────────────────────────┐
│ UserDAO.insertUser(user)                           │
│ SQL: INSERT INTO NguoiDung                         │
│      (TenDangNhap, MatKhau, HoTen, Email,          │
│       SoDienThoai, MaVaiTro, DangHoatDong)         │
│      VALUES (?, ?, ?, ?, ?, ?, ?)                  │
│                                                    │
│ ps.setString(1, user.getUsername())                │
│ ps.setString(2, user.getPassword())  // Đã hash    │
│ ps.setString(3, user.getFullName())                │
│ ps.setString(4, user.getEmail())                   │
│ ps.setString(5, user.getPhoneNumber())             │
│ ps.setInt(6, user.getRoleId())                     │
│ ps.setBoolean(7, user.isActive())                  │
│                                                    │
│ return ps.executeUpdate() > 0                      │
└────────────┬───────────────────────────────────────┘
             │ Return true/false
             ↓
┌────────────────────────────────────────────────────┐
│ UserController (tiếp)                              │
│ 7. if (success) {                                  │
│      JOptionPane.show("Thêm thành công!")          │
│      loadData()  // Refresh JTable                 │
│      clearForm()                                   │
│    } else {                                        │
│      JOptionPane.show("Thêm thất bại!")            │
│    }                                               │
└────────────────────────────────────────────────────┘
```

---

### 3.3. 📚 LUỒNG MƯỢN SÁCH (TRANSACTION)

```
┌────────────────────────────────────────────────────┐
│ BorrowForm (VIEW)                                  │
│ BƯỚC 1: Kiểm tra độc giả                           │
│ • User nhập mã thẻ độc giả                         │
│ • Click "KIỂM TRA"                                 │
└────────────┬───────────────────────────────────────┘
             │ ActionEvent
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowController.checkReader()                     │
│ 1. String maThe = view.getTxtMaThe().getText()    │
│ 2. Reader reader = readerService.findReaderByCardId(maThe)│
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ ReaderDAO.getReaderByCardId(maThe)                 │
│ SQL: SELECT * FROM DocGia WHERE MaThe = ?          │
│ Return: Reader object hoặc null                    │
└────────────┬───────────────────────────────────────┘
             │ Return reader
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowController (tiếp)                            │
│ 3. if (reader == null) → "Không tìm thấy"         │
│ 4. if (reader.isBiKhoa()) → "Thẻ bị khóa"         │
│ 5. if (reader.getNgayHetHan() < today)             │
│      → "Thẻ đã hết hạn"                            │
│ 6. currentReader = reader                          │
│    view.getLblTenDocGia().setText(reader.getHoTen())│
└────────────────────────────────────────────────────┘
             │
             │
┌────────────────────────────────────────────────────┐
│ BorrowForm (VIEW)                                  │
│ BƯỚC 2: Thêm sách vào giỏ                          │
│ • User nhập mã vạch sách                           │
│ • Click "THÊM SÁCH"                                │
└────────────┬───────────────────────────────────────┘
             │ ActionEvent (có thể lặp nhiều lần)
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowController.addBookToCart()                   │
│ 1. String barcode = view.getTxtBarcode()           │
│ 2. BookCopy book = bookService.findBookCopyByBarcode(barcode)│
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ BookCopyDAO.getBookCopyByBarcode(barcode)          │
│ SQL: SELECT cs.*, ds.TuaDe, ds.TacGia              │
│      FROM CuonSach cs                              │
│      JOIN DauSach ds ON cs.MaDauSach = ds.MaDauSach│
│      WHERE cs.MaVach = ?                           │
│ Return: BookCopy với thông tin sách                │
└────────────┬───────────────────────────────────────┘
             │ Return book
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowController (tiếp)                            │
│ 3. if (book == null) → "Không tìm thấy sách"      │
│ 4. if (book.getTrangThai() != 1)                   │
│      → "Sách không có sẵn"                         │
│ 5. cart.add(book)  // ArrayList<BookCopy> cart     │
│ 6. Cập nhật JTable:                                │
│    tableModel.addRow([barcode, title, author])    │
└────────────────────────────────────────────────────┘
             │ Lặp lại BƯỚC 2 để thêm nhiều sách
             │
┌────────────────────────────────────────────────────┐
│ BorrowForm (VIEW)                                  │
│ BƯỚC 3: Lập phiếu mượn                             │
│ • User xác nhận hạn trả (mặc định = today + 14 ngày)│
│ • Click "CHO MƯỢN"                                 │
└────────────┬───────────────────────────────────────┘
             │ ActionEvent
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowController.borrowBooks()                     │
│ 1. Validate:                                       │
│    if (currentReader == null) → error              │
│    if (cart.isEmpty()) → error                     │
│    if (dueDate == null) → error                    │
│                                                    │
│ 2. Parse hạn trả:                                  │
│    Date dueDate = DateUtil.parseDate(              │
│        view.getTxtDueDate().getText()              │
│    )                                               │
│                                                    │
│ 3. Gọi Service:                                    │
│    boolean success = borrowService.borrowBooks(    │
│        currentReader.getMaDocGia(),                │
│        cart,                                       │
│        dueDate                                     │
│    )                                               │
└────────────┬───────────────────────────────────────┘
             │ TRANSACTION BẮT ĐẦU
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService.borrowBooks(maDocGia, books, hanTra)│
│                                                    │
│ Connection conn = DBConnection.getConnection()     │
│ conn.setAutoCommit(false)  // Bắt đầu transaction │
│                                                    │
│ TRY {                                              │
│   // STEP 1: Tạo PhieuMuon                         │
│   BorrowSlip slip = new BorrowSlip()               │
│   slip.setMaDocGia(maDocGia)                       │
│   slip.setMaNguoiDung(getCurrentUser().getId())    │
│   slip.setNgayMuon(new Date())                     │
│   slip.setHanTra(hanTra)                           │
│   slip.setTrangThai(0)  // 0 = Đang mượn           │
│                                                    │
│   int slipId = borrowSlipDAO.insert(slip, conn)    │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowSlipDAO.insert(slip, conn)                   │
│ SQL: INSERT INTO PhieuMuon                         │
│      (MaDocGia, MaNguoiDung, NgayMuon,             │
│       HanTra, GhiChu, TrangThai)                   │
│      VALUES (?, ?, ?, ?, ?, ?)                     │
│                                                    │
│ ps = conn.prepareStatement(sql, RETURN_GENERATED_KEYS)│
│ ps.executeUpdate()                                 │
│ rs = ps.getGeneratedKeys()                         │
│ if (rs.next()) return rs.getInt(1)  // MaPhieuMuon│
└────────────┬───────────────────────────────────────┘
             │ Return slipId
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService (tiếp)                               │
│                                                    │
│   // STEP 2: Tạo ChiTietMuonTra cho từng sách      │
│   FOR EACH book IN books {                         │
│     BorrowDetail detail = new BorrowDetail()       │
│     detail.setMaPhieuMuon(slipId)                  │
│     detail.setMaCuonSach(book.getMaCuonSach())     │
│                                                    │
│     borrowDetailDAO.insert(detail, conn)           │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowDetailDAO.insert(detail, conn)               │
│ SQL: INSERT INTO ChiTietMuonTra                    │
│      (MaPhieuMuon, MaCuonSach, TinhTrangTra, GhiChu)│
│      VALUES (?, ?, ?, ?)                           │
│                                                    │
│ ps.setInt(1, detail.getMaPhieuMuon())              │
│ ps.setInt(2, detail.getMaCuonSach())               │
│ ps.setString(3, null)  // Chưa trả                 │
│ ps.setString(4, null)                              │
│ ps.executeUpdate()                                 │
└────────────┬───────────────────────────────────────┘
             │ Return true
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService (tiếp)                               │
│                                                    │
│     // STEP 3: Cập nhật trạng thái sách            │
│     bookCopyDAO.updateStatus(                      │
│         book.getMaCuonSach(),                      │
│         2,  // 2 = Đang mượn                       │
│         conn                                       │
│     )                                              │
│   }  // END FOR                                    │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ BookCopyDAO.updateStatus(maCuonSach, 2, conn)     │
│ SQL: UPDATE CuonSach                               │
│      SET TrangThai = ?                             │
│      WHERE MaCuonSach = ?                          │
│                                                    │
│ ps.setInt(1, 2)  // TrangThai = 2 (Đang mượn)     │
│ ps.setInt(2, maCuonSach)                           │
│ ps.executeUpdate()                                 │
└────────────┬───────────────────────────────────────┘
             │ Return true
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService (tiếp)                               │
│                                                    │
│   // STEP 4: Ghi log audit                         │
│   auditService.logAction(                          │
│       getCurrentUser().getId(),                    │
│       "THÊM",                                      │
│       "PhieuMuon",                                 │
│       slipId,                                      │
│       "Lập phiếu mượn cho độc giả " + maDocGia     │
│   )                                                │
│                                                    │
│   conn.commit()  // COMMIT TRANSACTION             │
│   return true                                      │
│                                                    │
│ } CATCH (Exception e) {                            │
│   conn.rollback()  // ROLLBACK nếu có lỗi         │
│   e.printStackTrace()                              │
│   return false                                     │
│ } FINALLY {                                        │
│   conn.setAutoCommit(true)                         │
│ }                                                  │
└────────────┬───────────────────────────────────────┘
             │ Return success
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowController (tiếp)                            │
│ 4. if (success) {                                  │
│      JOptionPane.show("Lập phiếu mượn thành công!")│
│      cart.clear()                                  │
│      tableModel.setRowCount(0)                     │
│      clearForm()                                   │
│    } else {                                        │
│      JOptionPane.show("Lập phiếu thất bại!")       │
│    }                                               │
└────────────────────────────────────────────────────┘
```

---

### 3.4. 📤 LUỒNG TRẢ SÁCH VÀ TÍNH PHẠT

```
┌────────────────────────────────────────────────────┐
│ BorrowForm (VIEW)                                  │
│ • User nhập mã vạch sách cần trả                   │
│ • Click "NHẬN TRẢ"                                 │
└────────────┬───────────────────────────────────────┘
             │ ActionEvent
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowController.returnBook()                      │
│ 1. String barcode = view.getTxtBarcode().getText() │
│ 2. if (barcode.isEmpty()) → error                  │
│ 3. String result = borrowService.returnBook(barcode)│
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService.returnBook(barcode)                  │
│                                                    │
│ // STEP 1: Lấy thông tin cuốn sách                 │
│ BookCopy book = bookCopyDAO.getBookCopyByBarcode(barcode)│
│ if (book == null) return "Không tìm thấy sách"    │
│                                                    │
│ // STEP 2: Kiểm tra sách có đang mượn không        │
│ if (book.getTrangThai() != 2) {                    │
│   return "Sách không được mượn"                    │
│ }                                                  │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService (tiếp)                               │
│                                                    │
│ // STEP 3: Lấy thông tin chi tiết mượn             │
│ BorrowDetail detail = borrowDetailDAO.getBorrowDetailByCopy(│
│     book.getMaCuonSach()                           │
│ )                                                  │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowDetailDAO.getBorrowDetailByCopy(maCuonSach)  │
│ SQL: SELECT ct.*, pm.HanTra, pm.MaDocGia           │
│      FROM ChiTietMuonTra ct                        │
│      JOIN PhieuMuon pm ON ct.MaPhieuMuon = pm.MaPhieuMuon│
│      WHERE ct.MaCuonSach = ?                       │
│        AND ct.NgayTra IS NULL  -- Chưa trả         │
│ Return: BorrowDetail với thông tin đầy đủ          │
└────────────┬───────────────────────────────────────┘
             │ Return detail
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService (tiếp)                               │
│                                                    │
│ if (detail == null) return "Không tìm thấy phiếu" │
│                                                    │
│ // STEP 4: Cập nhật NgayTra trong ChiTietMuonTra   │
│ Date ngayTra = new Date()  // Hôm nay              │
│ borrowDetailDAO.updateReturn(                      │
│     book.getMaCuonSach(),                          │
│     ngayTra,                                       │
│     "Tốt"  // Tình trạng mặc định                  │
│ )                                                  │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowDetailDAO.updateReturn(maCuonSach, ngayTra, tinhTrang)│
│ SQL: UPDATE ChiTietMuonTra                         │
│      SET NgayTra = ?, TinhTrangTra = ?             │
│      WHERE MaCuonSach = ?                          │
│        AND NgayTra IS NULL  -- Chỉ update chưa trả │
│                                                    │
│ ps.setTimestamp(1, new Timestamp(ngayTra.getTime()))│
│ ps.setString(2, tinhTrang)                         │
│ ps.setInt(3, maCuonSach)                           │
│ ps.executeUpdate()                                 │
└────────────┬───────────────────────────────────────┘
             │ Return true
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService (tiếp)                               │
│                                                    │
│ // STEP 5: Cập nhật trạng thái sách = 1 (Có sẵn)  │
│ bookCopyDAO.updateStatus(book.getMaCuonSach(), 1)  │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ BookCopyDAO.updateStatus(maCuonSach, 1)            │
│ SQL: UPDATE CuonSach                               │
│      SET TrangThai = 1  -- 1 = Có sẵn             │
│      WHERE MaCuonSach = ?                          │
└────────────┬───────────────────────────────────────┘
             │ Return true
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService (tiếp)                               │
│                                                    │
│ // STEP 6: TÍNH PHẠT TRỄ HẠN                       │
│ Date hanTra = detail.getHanTra()  // Từ PhieuMuon  │
│ long soNgayTre = DateUtil.daysBetween(hanTra, ngayTra)│
│                                                    │
│ double tienPhat = 0                                │
│ String lyDoPhat = ""                               │
│                                                    │
│ if (soNgayTre > 0) {  // Trễ hạn                   │
│   tienPhat = soNgayTre * Constants.PENALTY_PER_DAY │
│   lyDoPhat = "Trả trễ " + soNgayTre + " ngày"      │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService (tiếp)                               │
│                                                    │
│   // STEP 7: Tạo PhieuPhat nếu có phạt             │
│   if (tienPhat > 0) {                              │
│     Penalty penalty = new Penalty()                │
│     penalty.setMaChiTiet(detail.getMaChiTiet())    │
│     penalty.setMaDocGia(detail.getMaDocGia())      │
│     penalty.setLyDo(lyDoPhat)                      │
│     penalty.setSoTien(tienPhat)                    │
│     penalty.setDaDongTien(false)                   │
│     penalty.setNgayTao(new Date())                 │
│                                                    │
│     penaltyDAO.insert(penalty)                     │
└────────────┬───────────────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────────────────┐
│ PenaltyDAO.insert(penalty)                         │
│ SQL: INSERT INTO PhieuPhat                         │
│      (MaChiTiet, MaDocGia, LyDo, SoTien,           │
│       DaDongTien, NgayTao)                         │
│      VALUES (?, ?, ?, ?, ?, ?)                     │
│                                                    │
│ ps.setInt(1, penalty.getMaChiTiet())               │
│ ps.setInt(2, penalty.getMaDocGia())                │
│ ps.setString(3, penalty.getLyDo())                 │
│ ps.setDouble(4, penalty.getSoTien())               │
│ ps.setBoolean(5, false)  // DaDongTien = 0         │
│ ps.setTimestamp(6, new Timestamp(...))             │
│ ps.executeUpdate()                                 │
└────────────┬───────────────────────────────────────┘
             │ Return true
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowService (tiếp)                               │
│                                                    │
│   }  // End if tienPhat > 0                        │
│                                                    │
│ // STEP 8: Ghi log audit                           │
│ auditService.logAction(                            │
│     getCurrentUser().getId(),                      │
│     "NHẬN TRẢ",                                    │
│     "ChiTietMuonTra",                              │
│     detail.getMaChiTiet(),                         │
│     "Nhận trả sách " + barcode                     │
│ )                                                  │
│                                                    │
│ // STEP 9: Tạo message trả về                      │
│ String message = "Trả sách thành công!";           │
│ if (tienPhat > 0) {                                │
│   message += "\nTiền phạt: " +                     │
│              String.format("%,.0f đồng", tienPhat) │
│ }                                                  │
│                                                    │
│ return message                                     │
└────────────┬───────────────────────────────────────┘
             │ Return message
             ↓
┌────────────────────────────────────────────────────┐
│ BorrowController (tiếp)                            │
│ 4. JOptionPane.showMessageDialog(result)           │
│    // Hiển thị:                                    │
│    // "Trả sách thành công!"                       │
│    // hoặc                                         │
│    // "Trả sách thành công!                        │
│    //  Tiền phạt: 25,000 đồng"                     │
│                                                    │
│ 5. clearForm()                                     │
└────────────────────────────────────────────────────┘
```

---

## 📊 4. SƠ ĐỒ KIẾN TRÚC MVC

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                    │
│                    (Giao diện người dùng)                │
│  ┌──────────────────────────────────────────────────┐   │
│  │ VIEW (view/)                                     │   │
│  │ - LoginForm.java                                 │   │
│  │ - MainForm.java                                  │   │
│  │ - BookForm.java, ReaderForm.java, ...           │   │
│  │                                                  │   │
│  │ Nhiệm vụ:                                        │   │
│  │ • Hiển thị UI (JFrame, JPanel, JTable)          │   │
│  │ • Nhận input từ user (button click, text input)  │   │
│  │ • Không chứa logic nghiệp vụ                     │   │
│  └──────────────────────────────────────────────────┘   │
└──────────────┬──────────────────────────────────────────┘
               │ Event (ActionListener)
               ↓
┌─────────────────────────────────────────────────────────┐
│                   CONTROLLER LAYER                       │
│                   (Xử lý logic điều khiển)               │
│  ┌──────────────────────────────────────────────────┐   │
│  │ CONTROLLER (controller/)                         │   │
│  │ - LoginController.java                           │   │
│  │ - MainController.java                            │   │
│  │ - BookController.java, ReaderController.java, ...│   │
│  │                                                  │   │
│  │ Nhiệm vụ:                                        │   │
│  │ • Nhận event từ View                             │   │
│  │ • Validate input                                 │   │
│  │ • Gọi Service/DAO                                │   │
│  │ • Cập nhật lại View                              │   │
│  └──────────────────────────────────────────────────┘   │
└──────────────┬──────────────────────────────────────────┘
               │ Gọi method
               ↓
┌─────────────────────────────────────────────────────────┐
│                   BUSINESS LOGIC LAYER                   │
│                   (Xử lý logic nghiệp vụ)                │
│  ┌──────────────────────────────────────────────────┐   │
│  │ SERVICE (service/)                               │   │
│  │ - AuthService.java                               │   │
│  │ - BookService.java                               │   │
│  │ - BorrowService.java                             │   │
│  │ - PenaltyService.java                            │   │
│  │ - ReportService.java                             │   │
│  │ - AuditService.java, ...                         │   │
│  │                                                  │   │
│  │ Nhiệm vụ:                                        │   │
│  │ • Xử lý logic nghiệp vụ phức tạp                 │   │
│  │ • Kết hợp nhiều DAO                              │   │
│  │ • Quản lý Transaction (BEGIN, COMMIT, ROLLBACK)  │   │
│  │ • Tính toán phạt, kiểm tra điều kiện             │   │
│  └──────────────────────────────────────────────────┘   │
└──────────────┬──────────────────────────────────────────┘
               │ Gọi method
               ↓
┌─────────────────────────────────────────────────────────┐
│                   DATA ACCESS LAYER                      │
│                   (Truy xuất database)                   │
│  ┌──────────────────────────────────────────────────┐   │
│  │ DAO (dao/)                                       │   │
│  │ - UserDAO.java                                   │   │
│  │ - BookDAO.java, BookCopyDAO.java                 │   │
│  │ - ReaderDAO.java                                 │   │
│  │ - BorrowSlipDAO.java, BorrowDetailDAO.java       │   │
│  │ - PenaltyDAO.java                                │   │
│  │ - CategoryDAO.java, RoleDAO.java, ...            │   │
│  │                                                  │   │
│  │ Nhiệm vụ:                                        │   │
│  │ • CRUD với database (INSERT, SELECT, UPDATE, DELETE)│
│  │ • Viết câu SQL                                   │   │
│  │ • Map ResultSet → Object                         │   │
│  │ • Một DAO quản lý một bảng                       │   │
│  └──────────────────────────────────────────────────┘   │
└──────────────┬──────────────────────────────────────────┘
               │ SQL Query
               ↓
┌─────────────────────────────────────────────────────────┐
│                      DATABASE                            │
│  ┌──────────────────────────────────────────────────┐   │
│  │ MySQL Database: db_quanlythuvien                │   │
│  │                                                  │   │
│  │ Tables:                                          │   │
│  │ • VaiTro                                         │   │
│  │ • NguoiDung                                      │   │
│  │ • TheLoai                                        │   │
│  │ • DauSach                                        │   │
│  │ • CuonSach                                       │   │
│  │ • DocGia                                         │   │
│  │ • PhieuMuon                                      │   │
│  │ • ChiTietMuonTra                                 │   │
│  │ • PhieuPhat                                      │   │
│  │ • NhatKyHoatDong                                 │   │
│  └──────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────┐
│                   SUPPORTING LAYERS                      │
│  ┌──────────────────────────────────────────────────┐   │
│  │ MODEL (model/)                                   │   │
│  │ - User, Role, Book, BookCopy, Reader,            │   │
│  │   BorrowSlip, BorrowDetail, Penalty, ...         │   │
│  │ Nhiệm vụ: Định nghĩa cấu trúc dữ liệu (POJO)    │   │
│  └──────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────┐   │
│  │ UTIL (util/)                                     │   │
│  │ - DBConnection (Singleton)                       │   │
│  │ - PasswordUtil (SHA-256)                         │   │
│  │ - DateUtil (parse, format, calculate)            │   │
│  │ - ValidateUtil (email, phone, ...)               │   │
│  │ - Constants (hằng số)                            │   │
│  │ Nhiệm vụ: Cung cấp các hàm tiện ích dùng chung  │   │
│  └──────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
```

---

## 🔑 5. CÁC PATTERN ĐƯỢC SỬ DỤNG

### 5.1. **MVC Pattern** (Model-View-Controller)
- **Model**: Các lớp entity (User, Book, Reader...)
- **View**: Các lớp UI Swing (LoginForm, BookForm...)
- **Controller**: Các lớp controller xử lý logic

### 5.2. **DAO Pattern** (Data Access Object)
- Tách biệt logic truy xuất database ra khỏi business logic
- Mỗi DAO quản lý một bảng

### 5.3. **Service Layer Pattern**
- Xử lý logic nghiệp vụ phức tạp
- Kết hợp nhiều DAO
- Quản lý transaction

### 5.4. **Singleton Pattern**
- `DBConnection.getInstance()` - Chỉ có 1 instance connection
- `AuthService.getCurrentUser()` - Quản lý session user

### 5.5. **Transaction Pattern**
- Sử dụng trong `BorrowService.borrowBooks()`
- Đảm bảo tính toàn vẹn dữ liệu (ACID)

---

## 📝 6. QUY ƯỚC NAMING

### 6.1. Package Naming
- Chữ thường: `model`, `view`, `controller`, `dao`, `service`, `util`

### 6.2. Class Naming
- PascalCase: `BookController`, `UserDAO`, `AuthService`
- Suffix theo layer:
  - DAO: `UserDAO`, `BookDAO`
  - Service: `AuthService`, `BorrowService`
  - Controller: `LoginController`, `BookController`
  - Form: `LoginForm`, `BookForm`

### 6.3. Method Naming
- camelCase: `getUserByUsername()`, `borrowBooks()`
- CRUD prefix:
  - `get...()` - SELECT
  - `getAll...()` - SELECT tất cả
  - `insert...()` - INSERT
  - `update...()` - UPDATE
  - `delete...()` - DELETE
  - `search...()` - Tìm kiếm

### 6.4. Variable Naming
- camelCase: `currentUser`, `borrowList`
- JComponent prefix:
  - `txt...` - JTextField
  - `btn...` - JButton
  - `tbl...` - JTable
  - `cbo...` - JComboBox
  - `chk...` - JCheckBox
  - `lbl...` - JLabel
  - `pnl...` - JPanel

---

## 🚀 7. HƯỚNG DẪN CHẠY ỨNG DỤNG

### 7.1. Yêu cầu hệ thống
- JDK 8 trở lên
- MySQL Server 5.7+
- NetBeans IDE 8.2+ (hoặc IDE khác hỗ trợ Java Swing)
- MySQL Connector/J 5.1.40 (có trong thư mục `lib/`)

### 7.2. Các bước chạy

#### **BƯỚC 1: Cài đặt Database**
```sql
-- 1. Tạo database
CREATE DATABASE db_quanlythuvien CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. Chạy file SQL
USE db_quanlythuvien;
SOURCE d:/path/to/database/quanlythuvien.sql;

-- 3. Tạo user mặc định (Admin)
INSERT INTO NguoiDung (TenDangNhap, MatKhau, HoTen, MaVaiTro, DangHoatDong)
VALUES ('admin', SHA2('admin123', 256), 'Administrator', 1, 1);
```

#### **BƯỚC 2: Cấu hình DBConnection**
Mở file `src/util/DBConnection.java`, kiểm tra:
```java
private static final String DB_SERVER = "localhost";
private static final String DB_PORT = "3306";
private static final String DB_NAME = "db_quanlythuvien";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "nhan123"; // Đổi password của bạn
```

#### **BƯỚC 3: Thêm Library**
- NetBeans: Right-click project → Properties → Libraries → Add JAR/Folder
- Chọn: `lib/mysql-connector-java-5.1.40/mysql-connector-java-5.1.40-bin.jar`

#### **BƯỚC 4: Build Project**
```
Clean and Build (Shift + F11)
```

#### **BƯỚC 5: Run**
```
Run Project (F6)
```

#### **BƯỚC 6: Đăng nhập**
- Username: `admin`
- Password: `admin123`

---

## 📚 8. TÀI LIỆU THAM KHẢO

- **Java Swing Tutorial**: https://docs.oracle.com/javase/tutorial/uiswing/
- **JDBC Tutorial**: https://docs.oracle.com/javase/tutorial/jdbc/
- **MySQL Documentation**: https://dev.mysql.com/doc/
- **MVC Pattern**: https://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
- **DAO Pattern**: https://www.baeldung.com/java-dao-pattern

---

**Người thực hiện**: [Tên sinh viên]  
**Ngày tạo**: 25/01/2026  
**Phiên bản**: 1.0

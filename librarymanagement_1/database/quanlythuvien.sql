CREATE TABLE `VaiTro` (
  `MaVaiTro` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `TenVaiTro` varchar(50) NOT NULL,
  `MoTa` varchar(255) DEFAULT null
);

CREATE TABLE `TheLoai` (
  `MaTheLoai` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `TenTheLoai` varchar(100) NOT NULL,
  `MoTa` varchar(500) DEFAULT null
);

CREATE TABLE `DauSach` (
  `MaDauSach` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `TuaDe` varchar(200) NOT NULL,
  `TacGia` varchar(150) DEFAULT null,
  `NhaXuatBan` varchar(150) DEFAULT null,
  `NamXuatBan` int DEFAULT null,
  `MaTheLoai` int DEFAULT null,
  `HinhAnh` varchar(500) DEFAULT null,
  `MoTa` TEXT
);

CREATE TABLE `CuonSach` (
  `MaCuonSach` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `MaDauSach` int NOT NULL,
  `MaVach` varchar(50) NOT NULL,
  `TrangThai` tinyint NOT NULL DEFAULT 1,
  `TinhTrang` varchar(100) DEFAULT 'Mới',
  `ViTriKe` varchar(50) DEFAULT null,
  `GiaTien` decimal(18,2) DEFAULT null,
  `NgayNhap` date DEFAULT (CURRENT_DATE)
);

CREATE TABLE `DocGia` (
  `MaDocGia` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `MaThe` varchar(20) NOT NULL,
  `HoTen` varchar(100) NOT NULL,
  `Email` varchar(100) DEFAULT null,
  `SoDienThoai` varchar(15) DEFAULT null,
  `DiaChi` varchar(255) DEFAULT null,
  `GioiHanMuon` int DEFAULT 5,
  `NgayHetHan` date NOT NULL,
  `DiemViPham` int DEFAULT 0,
  `BiKhoa` tinyint(1) DEFAULT 0
);

CREATE TABLE `NguoiDung` (
  `MaNguoiDung` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `TenDangNhap` varchar(50) NOT NULL,
  `MatKhau` varchar(255) NOT NULL,
  `HoTen` varchar(100) NOT NULL,
  `Email` varchar(100) DEFAULT null,
  `SoDienThoai` varchar(20) DEFAULT null,
  `MaVaiTro` int NOT NULL,
  `DangHoatDong` tinyint(1) DEFAULT 1,
  `NgayTao` datetime DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE `NhatKyHoatDong` (
  `MaNhatKy` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `MaNguoiDung` int DEFAULT null,
  `HanhDong` varchar(50) NOT NULL,
  `TenBang` varchar(50) DEFAULT null,
  `MaBanGhi` int DEFAULT null,
  `MoTaChiTiet` TEXT,
  `DiaChiIP` varchar(50) DEFAULT null,
  `ThoiGian` datetime DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE `PhieuMuon` (
  `MaPhieuMuon` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `MaDocGia` int NOT NULL,
  `MaNguoiDung` int DEFAULT null,
  `NgayMuon` datetime DEFAULT (CURRENT_TIMESTAMP),
  `HanTra` date NOT NULL,
  `GhiChu` varchar(255) DEFAULT null,
  `TrangThai` tinyint DEFAULT 0
);

CREATE TABLE `ChiTietMuonTra` (
  `MaChiTiet` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `MaPhieuMuon` int NOT NULL,
  `MaCuonSach` int NOT NULL,
  `NgayTra` datetime DEFAULT null,
  `TinhTrangTra` varchar(100) DEFAULT null,
  `GhiChu` varchar(255) DEFAULT null
);

CREATE TABLE `PhieuPhat` (
  `MaPhieuPhat` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `MaChiTiet` int DEFAULT null,
  `MaDocGia` int NOT NULL,
  `LyDo` varchar(200) DEFAULT null,
  `SoTien` decimal(18,2) NOT NULL DEFAULT 0,
  `DaDongTien` tinyint(1) DEFAULT 0,
  `NgayTao` datetime DEFAULT (CURRENT_TIMESTAMP)
);

CREATE UNIQUE INDEX `unique_vaitro` ON `VaiTro` (`TenVaiTro`);

CREATE UNIQUE INDEX `unique_theloai` ON `TheLoai` (`TenTheLoai`);

CREATE UNIQUE INDEX `unique_mavach` ON `CuonSach` (`MaVach`);

CREATE UNIQUE INDEX `unique_mathe` ON `DocGia` (`MaThe`);

CREATE UNIQUE INDEX `unique_tendangnhap` ON `NguoiDung` (`TenDangNhap`);

ALTER TABLE `DauSach` ADD CONSTRAINT `fk_DauSach_TheLoai` FOREIGN KEY (`MaTheLoai`) REFERENCES `TheLoai` (`MaTheLoai`);

ALTER TABLE `CuonSach` ADD CONSTRAINT `fk_CuonSach_DauSach` FOREIGN KEY (`MaDauSach`) REFERENCES `DauSach` (`MaDauSach`);

ALTER TABLE `NguoiDung` ADD CONSTRAINT `fk_NguoiDung_VaiTro` FOREIGN KEY (`MaVaiTro`) REFERENCES `VaiTro` (`MaVaiTro`);

ALTER TABLE `NhatKyHoatDong` ADD CONSTRAINT `fk_NhatKy_NguoiDung` FOREIGN KEY (`MaNguoiDung`) REFERENCES `NguoiDung` (`MaNguoiDung`);

ALTER TABLE `PhieuMuon` ADD CONSTRAINT `fk_PhieuMuon_DocGia` FOREIGN KEY (`MaDocGia`) REFERENCES `DocGia` (`MaDocGia`);

ALTER TABLE `PhieuMuon` ADD CONSTRAINT `fk_PhieuMuon_NguoiDung` FOREIGN KEY (`MaNguoiDung`) REFERENCES `NguoiDung` (`MaNguoiDung`);

ALTER TABLE `ChiTietMuonTra` ADD CONSTRAINT `fk_CTMT_PhieuMuon` FOREIGN KEY (`MaPhieuMuon`) REFERENCES `PhieuMuon` (`MaPhieuMuon`);

ALTER TABLE `ChiTietMuonTra` ADD CONSTRAINT `fk_CTMT_CuonSach` FOREIGN KEY (`MaCuonSach`) REFERENCES `CuonSach` (`MaCuonSach`);

ALTER TABLE `PhieuPhat` ADD CONSTRAINT `fk_PhieuPhat_ChiTiet` FOREIGN KEY (`MaChiTiet`) REFERENCES `ChiTietMuonTra` (`MaChiTiet`);

ALTER TABLE `PhieuPhat` ADD CONSTRAINT `fk_PhieuPhat_DocGia` FOREIGN KEY (`MaDocGia`) REFERENCES `DocGia` (`MaDocGia`);

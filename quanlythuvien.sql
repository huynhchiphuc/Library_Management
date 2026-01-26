-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_quanlythuvien
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chitietmuontra`
--

DROP TABLE IF EXISTS `chitietmuontra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitietmuontra` (
  `MaChiTiet` int NOT NULL AUTO_INCREMENT,
  `MaPhieuMuon` int NOT NULL,
  `MaCuonSach` int NOT NULL,
  `NgayTra` datetime DEFAULT NULL,
  `TinhTrangTra` varchar(100) DEFAULT NULL,
  `GhiChu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MaChiTiet`),
  KEY `fk_CTMT_PhieuMuon` (`MaPhieuMuon`),
  KEY `fk_CTMT_CuonSach` (`MaCuonSach`),
  CONSTRAINT `fk_CTMT_CuonSach` FOREIGN KEY (`MaCuonSach`) REFERENCES `cuonsach` (`MaCuonSach`),
  CONSTRAINT `fk_CTMT_PhieuMuon` FOREIGN KEY (`MaPhieuMuon`) REFERENCES `phieumuon` (`MaPhieuMuon`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitietmuontra`
--

LOCK TABLES `chitietmuontra` WRITE;
/*!40000 ALTER TABLE `chitietmuontra` DISABLE KEYS */;
INSERT INTO `chitietmuontra` VALUES (1,1,1,NULL,NULL,NULL),(2,1,2,NULL,NULL,NULL),(3,2,3,NULL,NULL,NULL),(4,3,4,NULL,NULL,NULL),(5,4,5,NULL,NULL,NULL),(6,6,1,NULL,'Dang muon',NULL),(7,6,3,NULL,'Dang muon',NULL),(8,7,5,NULL,'Dang muon',NULL);
/*!40000 ALTER TABLE `chitietmuontra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuonsach`
--

DROP TABLE IF EXISTS `cuonsach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuonsach` (
  `MaCuonSach` int NOT NULL AUTO_INCREMENT,
  `MaDauSach` int NOT NULL,
  `MaVach` varchar(50) NOT NULL,
  `TrangThai` tinyint NOT NULL DEFAULT '1',
  `TinhTrang` varchar(100) DEFAULT 'Mới',
  `ViTriKe` varchar(50) DEFAULT NULL,
  `GiaTien` decimal(18,2) DEFAULT NULL,
  `NgayNhap` date DEFAULT (curdate()),
  PRIMARY KEY (`MaCuonSach`),
  UNIQUE KEY `unique_mavach` (`MaVach`),
  KEY `fk_CuonSach_DauSach` (`MaDauSach`),
  CONSTRAINT `fk_CuonSach_DauSach` FOREIGN KEY (`MaDauSach`) REFERENCES `dausach` (`MaDauSach`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuonsach`
--

LOCK TABLES `cuonsach` WRITE;
/*!40000 ALTER TABLE `cuonsach` DISABLE KEYS */;
INSERT INTO `cuonsach` VALUES (1,1,'MS001',2,'Mới','Kệ A1',120000.00,'2026-01-19'),(2,1,'MS002',1,'Mới','Kệ A1',120000.00,'2026-01-19'),(3,2,'MS003',2,'Tốt','Kệ A2',150000.00,'2026-01-19'),(4,3,'MS004',1,'Cũ','Kệ B1',80000.00,'2026-01-19'),(5,4,'MS005',2,'Mới','Kệ C1',200000.00,'2026-01-19'),(6,6,'B6-17690213458740',1,'Mới',NULL,600000.00,'2026-01-22'),(7,6,'B6-17690213458801',1,'Mới',NULL,600000.00,'2026-01-22'),(8,6,'B6-17690213458802',1,'Mới',NULL,600000.00,'2026-01-22'),(9,6,'B6-17690213458803',1,'Mới',NULL,600000.00,'2026-01-22'),(10,7,'B7-17690452899090',1,'Mới',NULL,925102.00,'2026-01-22'),(11,7,'B7-17690452899151',1,'Mới',NULL,925102.00,'2026-01-22'),(12,7,'B7-17690452899152',1,'Mới',NULL,925102.00,'2026-01-22'),(13,7,'B7-17690452899153',1,'Mới',NULL,925102.00,'2026-01-22'),(14,7,'B7-17690452899154',1,'Mới',NULL,925102.00,'2026-01-22'),(15,7,'B7-17690452899155',1,'Mới',NULL,925102.00,'2026-01-22'),(16,7,'B7-17690452899156',1,'Mới',NULL,925102.00,'2026-01-22'),(17,7,'B7-17690452899157',1,'Mới',NULL,925102.00,'2026-01-22'),(18,7,'B7-17690452899158',1,'Mới',NULL,925102.00,'2026-01-22');
/*!40000 ALTER TABLE `cuonsach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dausach`
--

DROP TABLE IF EXISTS `dausach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dausach` (
  `MaDauSach` int NOT NULL AUTO_INCREMENT,
  `TuaDe` varchar(200) NOT NULL,
  `TacGia` varchar(150) DEFAULT NULL,
  `NhaXuatBan` varchar(150) DEFAULT NULL,
  `NamXuatBan` int DEFAULT NULL,
  `MaTheLoai` int DEFAULT NULL,
  `HinhAnh` varchar(500) DEFAULT NULL,
  `MoTa` text,
  PRIMARY KEY (`MaDauSach`),
  KEY `fk_DauSach_TheLoai` (`MaTheLoai`),
  CONSTRAINT `fk_DauSach_TheLoai` FOREIGN KEY (`MaTheLoai`) REFERENCES `theloai` (`MaTheLoai`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dausach`
--

LOCK TABLES `dausach` WRITE;
/*!40000 ALTER TABLE `dausach` DISABLE KEYS */;
INSERT INTO `dausach` VALUES (1,'Lập trình Java','Nguyễn Văn A','NXB Giáo Dục',2022,1,NULL,'Sách Java cơ bản'),(2,'Cấu trúc dữ liệu','Trần Văn B','NXB Khoa Học',2021,1,NULL,'CTDL & GT'),(3,'Dế Mèn Phiêu Lưu Ký','Tô Hoài','NXB Kim Đồng',2019,2,NULL,'Văn học thiếu nhi'),(4,'Kinh tế vi mô','Paul Krugman','NXB Kinh Tế',2020,4,NULL,'Kinh tế học'),(5,'Vũ trụ trong vỏ hạt dẻ đó','Stephen Hawking','NXB Trẻ',2018,3,NULL,'Khoa học vũ trụ'),(6,'Lập trình mobile','Hồ Thị Ngọc','NXB Tuoi',1999,1,NULL,'Sách hướng dẫn lập trình mobile'),(7,'Hệ thống thông tin','Trần Văn D','NXB Công nghệ',2022,1,NULL,'Sách hướng dẫn học hệ thống thông tin');
/*!40000 ALTER TABLE `dausach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docgia`
--

DROP TABLE IF EXISTS `docgia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docgia` (
  `MaDocGia` int NOT NULL AUTO_INCREMENT,
  `MaThe` varchar(20) NOT NULL,
  `HoTen` varchar(100) NOT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `SoDienThoai` varchar(15) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `GioiHanMuon` int DEFAULT '5',
  `NgayHetHan` date NOT NULL,
  `DiemViPham` int DEFAULT '0',
  `BiKhoa` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`MaDocGia`),
  UNIQUE KEY `unique_mathe` (`MaThe`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docgia`
--

LOCK TABLES `docgia` WRITE;
/*!40000 ALTER TABLE `docgia` DISABLE KEYS */;
INSERT INTO `docgia` VALUES (1,'DG001','Nguyễn Văn Nam','nhan220852@student.nctu.edu.vn','0901111111','Hà Nội',5,'2026-12-31',0,0),(2,'DG002','Trần Thị Hoa','hoa@gmail.com','0902222222','Đà Nẵng',5,'2026-12-31',0,1),(3,'DG003','Lê Văn Minh','minh@gmail.com','0903333333','TP HCM',5,'2026-12-31',0,0),(4,'DG004','Phạm Thu Trang s','trang@gmail.com','0904444444','Huế',5,'2026-12-31',0,0),(5,'DG005','Đặng Quốc Bảo','bao@gmail.com','0905555555','Cần Thơ',5,'2026-12-31',0,0);
/*!40000 ALTER TABLE `docgia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nguoidung`
--

DROP TABLE IF EXISTS `nguoidung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguoidung` (
  `MaNguoiDung` int NOT NULL AUTO_INCREMENT,
  `TenDangNhap` varchar(50) NOT NULL,
  `MatKhau` varchar(255) NOT NULL,
  `HoTen` varchar(100) NOT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `MaVaiTro` int NOT NULL,
  `DangHoatDong` tinyint(1) DEFAULT '1',
  `NgayTao` datetime DEFAULT (now()),
  PRIMARY KEY (`MaNguoiDung`),
  UNIQUE KEY `unique_tendangnhap` (`TenDangNhap`),
  KEY `fk_NguoiDung_VaiTro` (`MaVaiTro`),
  CONSTRAINT `fk_NguoiDung_VaiTro` FOREIGN KEY (`MaVaiTro`) REFERENCES `vaitro` (`MaVaiTro`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguoidung`
--

LOCK TABLES `nguoidung` WRITE;
/*!40000 ALTER TABLE `nguoidung` DISABLE KEYS */;
INSERT INTO `nguoidung` VALUES (1,'admin','240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9','Quản Trị','admin@gmail.com',NULL,1,1,'2026-01-19 23:34:52'),(2,'thuthu1','7ff3f7c47abac528b4cdceb334e9d20f67e44e8c82ab9587a4f0ae685ba16b77','Nguyễn Thủ Thư','thuthu@gmail.com',NULL,2,1,'2026-01-19 23:34:52'),(3,'qltv','123456','Quản Lý TV','ql@gmail.com',NULL,4,1,'2026-01-19 23:34:52'),(4,'nv01','123456','Nhân Viên A','nv@gmail.com','0868237229',2,1,'2026-01-19 23:34:52'),(5,'docgia','123456','User Demo','user@gmail.com',NULL,3,1,'2026-01-19 23:34:52');
/*!40000 ALTER TABLE `nguoidung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhatkyhoatdong`
--

DROP TABLE IF EXISTS `nhatkyhoatdong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhatkyhoatdong` (
  `MaNhatKy` int NOT NULL AUTO_INCREMENT,
  `MaNguoiDung` int DEFAULT NULL,
  `HanhDong` varchar(50) NOT NULL,
  `TenBang` varchar(50) DEFAULT NULL,
  `MaBanGhi` int DEFAULT NULL,
  `MoTaChiTiet` text,
  `DiaChiIP` varchar(50) DEFAULT NULL,
  `ThoiGian` datetime DEFAULT (now()),
  PRIMARY KEY (`MaNhatKy`),
  KEY `fk_NhatKy_NguoiDung` (`MaNguoiDung`),
  CONSTRAINT `fk_NhatKy_NguoiDung` FOREIGN KEY (`MaNguoiDung`) REFERENCES `nguoidung` (`MaNguoiDung`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhatkyhoatdong`
--

LOCK TABLES `nhatkyhoatdong` WRITE;
/*!40000 ALTER TABLE `nhatkyhoatdong` DISABLE KEYS */;
INSERT INTO `nhatkyhoatdong` VALUES (1,1,'INSERT','DauSach',1,'Thêm đầu sách mới',NULL,'2026-01-19 23:35:20'),(2,2,'UPDATE','CuonSach',3,'Cập nhật tình trạng',NULL,'2026-01-19 23:35:20'),(3,1,'DELETE','DocGia',5,'Xóa độc giả',NULL,'2026-01-19 23:35:20'),(4,3,'INSERT','PhieuMuon',2,'Tạo phiếu mượn',NULL,'2026-01-19 23:35:20'),(5,4,'LOGIN',NULL,NULL,'Đăng nhập hệ thống',NULL,'2026-01-19 23:35:20'),(6,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','172.21.15.185','2026-01-22 08:36:44'),(7,1,'UPDATE','DauSach',5,'Cập nhật thông tin sách: Vũ trụ trong vỏ hạt dẻ đó (ID: 5)','172.21.15.185','2026-01-22 08:37:38'),(8,1,'UPDATE','DocGia',0,'Cập nhật độc giả: Phạm Thu Trang s (Mã thẻ: DG004)','172.21.15.185','2026-01-22 08:38:20'),(9,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','172.21.15.185','2026-01-22 08:39:37'),(10,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','172.21.15.185','2026-01-22 08:47:14'),(11,1,'UPDATE','DauSach',6,'Cập nhật thông tin sách: Lập trình mobile (ID: 6)','172.21.15.185','2026-01-22 08:48:20'),(12,1,'UPDATE','DocGia',0,'Cập nhật độc giả: Nguyễn Văn Nam (Mã thẻ: DG001)','172.21.15.185','2026-01-22 08:50:06'),(13,1,'LOGOUT','NguoiDung',1,'Đăng xuất - User: admin','172.21.15.185','2026-01-22 08:53:38'),(14,2,'LOGIN','NguoiDung',2,'Đăng nhập thành công - User: thuthu1','172.21.15.185','2026-01-22 08:53:46'),(15,2,'LOGOUT','NguoiDung',2,'Đăng xuất - User: thuthu1','172.21.15.185','2026-01-22 08:54:19'),(16,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','172.21.15.185','2026-01-22 08:56:53'),(17,1,'LOGOUT','NguoiDung',1,'Đăng xuất - User: admin','172.21.15.185','2026-01-22 08:57:08'),(18,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','172.21.15.185','2026-01-22 08:57:51'),(19,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','192.168.1.105','2026-01-23 23:13:19'),(20,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','192.168.1.105','2026-01-23 23:33:25'),(21,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','192.168.1.105','2026-01-24 00:48:08'),(22,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','192.168.1.105','2026-01-24 00:50:36'),(23,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','192.168.1.105','2026-01-24 00:52:37'),(24,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','192.168.1.105','2026-01-24 00:58:39'),(25,1,'LOGOUT','NguoiDung',1,'Đăng xuất - User: admin','192.168.1.105','2026-01-24 01:00:16'),(26,2,'LOGIN','NguoiDung',2,'Đăng nhập thành công - User: thuthu1','192.168.1.105','2026-01-24 01:00:24'),(27,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','192.168.1.129','2026-01-24 15:44:33'),(28,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','172.0.10.59','2026-01-26 07:46:17'),(29,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','172.0.10.59','2026-01-26 07:50:19'),(30,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','172.0.10.59','2026-01-26 07:55:55'),(31,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','172.0.10.59','2026-01-26 07:57:07'),(32,1,'LOGIN','NguoiDung',1,'Đăng nhập thành công - User: admin','172.0.10.59','2026-01-26 07:59:41');
/*!40000 ALTER TABLE `nhatkyhoatdong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieumuon`
--

DROP TABLE IF EXISTS `phieumuon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieumuon` (
  `MaPhieuMuon` int NOT NULL AUTO_INCREMENT,
  `MaDocGia` int NOT NULL,
  `MaNguoiDung` int DEFAULT NULL,
  `NgayMuon` datetime DEFAULT (now()),
  `HanTra` date NOT NULL,
  `GhiChu` varchar(255) DEFAULT NULL,
  `TrangThai` tinyint DEFAULT '0',
  PRIMARY KEY (`MaPhieuMuon`),
  KEY `fk_PhieuMuon_DocGia` (`MaDocGia`),
  KEY `fk_PhieuMuon_NguoiDung` (`MaNguoiDung`),
  CONSTRAINT `fk_PhieuMuon_DocGia` FOREIGN KEY (`MaDocGia`) REFERENCES `docgia` (`MaDocGia`),
  CONSTRAINT `fk_PhieuMuon_NguoiDung` FOREIGN KEY (`MaNguoiDung`) REFERENCES `nguoidung` (`MaNguoiDung`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieumuon`
--

LOCK TABLES `phieumuon` WRITE;
/*!40000 ALTER TABLE `phieumuon` DISABLE KEYS */;
INSERT INTO `phieumuon` VALUES (1,1,2,'2026-01-19 23:34:58','2026-01-20','Mượn lần đầu',0),(2,2,2,'2026-01-19 23:34:58','2026-01-22',NULL,0),(3,3,2,'2026-01-19 23:34:58','2026-01-25',NULL,0),(4,4,3,'2026-01-19 23:34:58','2026-01-28','Cẩn thận sách',0),(5,5,4,'2026-01-19 23:34:58','2026-01-30',NULL,0),(6,1,NULL,'2026-01-20 09:34:55','2026-02-17',NULL,0),(7,1,NULL,'2026-01-22 08:24:04','2026-02-05',NULL,0);
/*!40000 ALTER TABLE `phieumuon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieuphat`
--

DROP TABLE IF EXISTS `phieuphat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieuphat` (
  `MaPhieuPhat` int NOT NULL AUTO_INCREMENT,
  `MaChiTiet` int DEFAULT NULL,
  `MaDocGia` int NOT NULL,
  `LyDo` varchar(200) DEFAULT NULL,
  `SoTien` decimal(18,2) NOT NULL DEFAULT '0.00',
  `DaDongTien` tinyint(1) DEFAULT '0',
  `NgayTao` datetime DEFAULT (now()),
  PRIMARY KEY (`MaPhieuPhat`),
  KEY `fk_PhieuPhat_ChiTiet` (`MaChiTiet`),
  KEY `fk_PhieuPhat_DocGia` (`MaDocGia`),
  CONSTRAINT `fk_PhieuPhat_ChiTiet` FOREIGN KEY (`MaChiTiet`) REFERENCES `chitietmuontra` (`MaChiTiet`),
  CONSTRAINT `fk_PhieuPhat_DocGia` FOREIGN KEY (`MaDocGia`) REFERENCES `docgia` (`MaDocGia`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieuphat`
--

LOCK TABLES `phieuphat` WRITE;
/*!40000 ALTER TABLE `phieuphat` DISABLE KEYS */;
INSERT INTO `phieuphat` VALUES (1,4,3,'Trả sách trễ hạn',50000.00,0,'2026-01-19 23:35:13'),(2,5,4,'Sách hư hỏng',100000.00,0,'2026-01-19 23:35:13'),(3,NULL,2,'Mất thẻ',30000.00,0,'2026-01-19 23:35:13'),(4,NULL,1,'Vi phạm nội quy',20000.00,0,'2026-01-19 23:35:13'),(5,NULL,5,'Làm rách sách',70000.00,1,'2026-01-19 23:35:13');
/*!40000 ALTER TABLE `phieuphat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `theloai`
--

DROP TABLE IF EXISTS `theloai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `theloai` (
  `MaTheLoai` int NOT NULL AUTO_INCREMENT,
  `TenTheLoai` varchar(100) NOT NULL,
  `MoTa` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`MaTheLoai`),
  UNIQUE KEY `unique_theloai` (`TenTheLoai`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `theloai`
--

LOCK TABLES `theloai` WRITE;
/*!40000 ALTER TABLE `theloai` DISABLE KEYS */;
INSERT INTO `theloai` VALUES (1,'Công nghệ thông tin','Sách CNTT'),(2,'Văn học','Tiểu thuyết, truyện'),(3,'Khoa học','Khoa học tự nhiên'),(4,'Kinh tế','Kinh doanh, tài chính'),(5,'Thiếu nhi','Sách cho trẻ em');
/*!40000 ALTER TABLE `theloai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vaitro`
--

DROP TABLE IF EXISTS `vaitro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vaitro` (
  `MaVaiTro` int NOT NULL AUTO_INCREMENT,
  `TenVaiTro` varchar(50) NOT NULL,
  `MoTa` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MaVaiTro`),
  UNIQUE KEY `unique_vaitro` (`TenVaiTro`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vaitro`
--

LOCK TABLES `vaitro` WRITE;
/*!40000 ALTER TABLE `vaitro` DISABLE KEYS */;
INSERT INTO `vaitro` VALUES (1,'Admin','Quản trị hệ thống'),(2,'ThuThu','Nhân viên thư viện'),(3,'DocGia','Người đọc sách'),(4,'QuanLy','Quản lý thư viện'),(5,'NhanVien','Nhân viên hỗ trợ');
/*!40000 ALTER TABLE `vaitro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-26  8:23:05

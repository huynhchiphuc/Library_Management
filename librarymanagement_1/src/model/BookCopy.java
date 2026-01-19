/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 * Represents a physical copy of a book (CuonSach).
 * @author ASUS
 */
public class BookCopy {
    private int maCuonSach;
    private int maDauSach;
    private String maVach; // Barcode
    private int trangThai; // 1: Available, 2: Borrowed, 3: Lost, etc.
    private String tinhTrang; // Physical condition (New, Worn, etc.)
    private String viTriKe;
    private double giaTien;
    private Date ngayNhap;

    // Transient fields (fetched via JOIN)
    private String tuaDe;
    private String tacGia;
    private String nhaXuatBan;

    public BookCopy() {
    }

    public BookCopy(int maCuonSach, int maDauSach, String maVach, int trangThai, String tinhTrang, String viTriKe, double giaTien, Date ngayNhap) {
        this.maCuonSach = maCuonSach;
        this.maDauSach = maDauSach;
        this.maVach = maVach;
        this.trangThai = trangThai;
        this.tinhTrang = tinhTrang;
        this.viTriKe = viTriKe;
        this.giaTien = giaTien;
        this.ngayNhap = ngayNhap;
    }

    // Getters and Setters
    public int getMaCuonSach() { return maCuonSach; }
    public void setMaCuonSach(int maCuonSach) { this.maCuonSach = maCuonSach; }

    public int getMaDauSach() { return maDauSach; }
    public void setMaDauSach(int maDauSach) { this.maDauSach = maDauSach; }

    public String getMaVach() { return maVach; }
    public void setMaVach(String maVach) { this.maVach = maVach; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }

    public String getViTriKe() { return viTriKe; }
    public void setViTriKe(String viTriKe) { this.viTriKe = viTriKe; }

    public double getGiaTien() { return giaTien; }
    public void setGiaTien(double giaTien) { this.giaTien = giaTien; }

    public Date getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(Date ngayNhap) { this.ngayNhap = ngayNhap; }

    public String getTuaDe() { return tuaDe; }
    public void setTuaDe(String tuaDe) { this.tuaDe = tuaDe; }
    
    public String getTacGia() { return tacGia; }
    public void setTacGia(String tacGia) { this.tacGia = tacGia; }
    
    public String getNhaXuatBan() { return nhaXuatBan; }
    public void setNhaXuatBan(String nhaXuatBan) { this.nhaXuatBan = nhaXuatBan; }
}

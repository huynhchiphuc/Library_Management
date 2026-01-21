/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 * Model đại diện cho nhật ký hoạt động
 * @author ASUS
 */
public class AuditLog {
    private int maNhatKy;
    private int maNguoiDung;
    private String hanhDong;
    private String tenBang;
    private int maBanGhi;
    private String moTaChiTiet;
    private String diaChiIP;
    private Date thoiGian;
    
    // Transient fields
    private String tenNguoiDung;

    public AuditLog() {
    }

    public AuditLog(int maNhatKy, int maNguoiDung, String hanhDong, String tenBang, int maBanGhi, String moTaChiTiet, String diaChiIP, Date thoiGian) {
        this.maNhatKy = maNhatKy;
        this.maNguoiDung = maNguoiDung;
        this.hanhDong = hanhDong;
        this.tenBang = tenBang;
        this.maBanGhi = maBanGhi;
        this.moTaChiTiet = moTaChiTiet;
        this.diaChiIP = diaChiIP;
        this.thoiGian = thoiGian;
    }

    public int getMaNhatKy() {
        return maNhatKy;
    }

    public void setMaNhatKy(int maNhatKy) {
        this.maNhatKy = maNhatKy;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public String getTenBang() {
        return tenBang;
    }

    public void setTenBang(String tenBang) {
        this.tenBang = tenBang;
    }

    public int getMaBanGhi() {
        return maBanGhi;
    }

    public void setMaBanGhi(int maBanGhi) {
        this.maBanGhi = maBanGhi;
    }

    public String getMoTaChiTiet() {
        return moTaChiTiet;
    }

    public void setMoTaChiTiet(String moTaChiTiet) {
        this.moTaChiTiet = moTaChiTiet;
    }

    public String getDiaChiIP() {
        return diaChiIP;
    }

    public void setDiaChiIP(String diaChiIP) {
        this.diaChiIP = diaChiIP;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }
}

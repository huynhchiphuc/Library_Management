/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class Reader {
    private int maDocGia;
    private String maThe;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private int gioHanMuon;
    private Date ngayHetHan;
    private int diemViPham;
    private boolean biKhoa;

    public Reader() {
    }

    public Reader(int maDocGia, String maThe, String hoTen, String email, String soDienThoai, String diaChi, int gioHanMuon, Date ngayHetHan, int diemViPham, boolean biKhoa) {
        this.maDocGia = maDocGia;
        this.maThe = maThe;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.gioHanMuon = gioHanMuon;
        this.ngayHetHan = ngayHetHan;
        this.diemViPham = diemViPham;
        this.biKhoa = biKhoa;
    }

    public int getMaDocGia() {
        return maDocGia;
    }

    public void setMaDocGia(int maDocGia) {
        this.maDocGia = maDocGia;
    }

    public String getMaThe() {
        return maThe;
    }

    public void setMaThe(String maThe) {
        this.maThe = maThe;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getGioHanMuon() {
        return gioHanMuon;
    }

    public void setGioHanMuon(int gioHanMuon) {
        this.gioHanMuon = gioHanMuon;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public int getDiemViPham() {
        return diemViPham;
    }

    public void setDiemViPham(int diemViPham) {
        this.diemViPham = diemViPham;
    }

    public boolean isBiKhoa() {
        return biKhoa;
    }

    public void setBiKhoa(boolean biKhoa) {
        this.biKhoa = biKhoa;
    }
    
    @Override
    public String toString() {
        return hoTen;
    }
}

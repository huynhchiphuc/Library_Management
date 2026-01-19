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
public class BorrowSlip {
    private int maPhieuMuon;
    private int maDocGia;
    private int maNguoiDung; // Created by User (Librarian)
    private Date ngayMuon;
    private Date hanTra;
    private String ghiChu;
    private int trangThai; // 0: Active, 1: Completed (Returned all)

    public BorrowSlip() {
    }

    public BorrowSlip(int maPhieuMuon, int maDocGia, int maNguoiDung, Date ngayMuon, Date hanTra, String ghiChu, int trangThai) {
        this.maPhieuMuon = maPhieuMuon;
        this.maDocGia = maDocGia;
        this.maNguoiDung = maNguoiDung;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public int getMaPhieuMuon() { return maPhieuMuon; }
    public void setMaPhieuMuon(int maPhieuMuon) { this.maPhieuMuon = maPhieuMuon; }

    public int getMaDocGia() { return maDocGia; }
    public void setMaDocGia(int maDocGia) { this.maDocGia = maDocGia; }

    public int getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(int maNguoiDung) { this.maNguoiDung = maNguoiDung; }

    public Date getNgayMuon() { return ngayMuon; }
    public void setNgayMuon(Date ngayMuon) { this.ngayMuon = ngayMuon; }

    public Date getHanTra() { return hanTra; }
    public void setHanTra(Date hanTra) { this.hanTra = hanTra; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
}

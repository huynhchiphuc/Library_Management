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
public class BorrowDetail {
    private int maChiTiet;
    private int maPhieuMuon;
    private int maCuonSach;
    private Date ngayTra;
    private String tinhTrangTra;
    private String ghiChu;

    public BorrowDetail() {
    }

    public BorrowDetail(int maChiTiet, int maPhieuMuon, int maCuonSach, Date ngayTra, String tinhTrangTra, String ghiChu) {
        this.maChiTiet = maChiTiet;
        this.maPhieuMuon = maPhieuMuon;
        this.maCuonSach = maCuonSach;
        this.ngayTra = ngayTra;
        this.tinhTrangTra = tinhTrangTra;
        this.ghiChu = ghiChu;
    }

    public int getMaChiTiet() { return maChiTiet; }
    public void setMaChiTiet(int maChiTiet) { this.maChiTiet = maChiTiet; }

    public int getMaPhieuMuon() { return maPhieuMuon; }
    public void setMaPhieuMuon(int maPhieuMuon) { this.maPhieuMuon = maPhieuMuon; }

    public int getMaCuonSach() { return maCuonSach; }
    public void setMaCuonSach(int maCuonSach) { this.maCuonSach = maCuonSach; }

    public Date getNgayTra() { return ngayTra; }
    public void setNgayTra(Date ngayTra) { this.ngayTra = ngayTra; }

    public String getTinhTrangTra() { return tinhTrangTra; }
    public void setTinhTrangTra(String tinhTrangTra) { this.tinhTrangTra = tinhTrangTra; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}

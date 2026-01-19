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
public class Penalty {
    private int maPhieuPhat;
    private int maChiTiet; // Can be 0 or null if not linked to specific borrow detail
    private int maDocGia;
    private String lyDo;
    private double soTien;
    private boolean daDongTien;
    private Date ngayTao;
    
    // Transient field for display
    private String tenDocGia;

    public Penalty() {
    }

    public Penalty(int maPhieuPhat, int maChiTiet, int maDocGia, String lyDo, double soTien, boolean daDongTien, Date ngayTao) {
        this.maPhieuPhat = maPhieuPhat;
        this.maChiTiet = maChiTiet;
        this.maDocGia = maDocGia;
        this.lyDo = lyDo;
        this.soTien = soTien;
        this.daDongTien = daDongTien;
        this.ngayTao = ngayTao;
    }

    public int getMaPhieuPhat() { return maPhieuPhat; }
    public void setMaPhieuPhat(int maPhieuPhat) { this.maPhieuPhat = maPhieuPhat; }

    public int getMaChiTiet() { return maChiTiet; }
    public void setMaChiTiet(int maChiTiet) { this.maChiTiet = maChiTiet; }

    public int getMaDocGia() { return maDocGia; }
    public void setMaDocGia(int maDocGia) { this.maDocGia = maDocGia; }

    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }

    public double getSoTien() { return soTien; }
    public void setSoTien(double soTien) { this.soTien = soTien; }

    public boolean isDaDongTien() { return daDongTien; }
    public void setDaDongTien(boolean daDongTien) { this.daDongTien = daDongTien; }

    public Date getNgayTao() { return ngayTao; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }

    public String getTenDocGia() { return tenDocGia; }
    public void setTenDocGia(String tenDocGia) { this.tenDocGia = tenDocGia; }
}

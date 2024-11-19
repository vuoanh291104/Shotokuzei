package model;

public class ThongBao {
    private int id;
    private int idNhanVien;
    private int newNPT;

    public ThongBao(int id, int idNhanVien, int newNPT) {
        this.id = id;
        this.idNhanVien = idNhanVien;
        this.newNPT = newNPT;
    }

    public void XacNhan(){
        // TODO: Truy xuat nhan vien qua idNhanVien, thay doi so nguoi phu thuoc cua nhan vien
        XoaThongBao();
    }

    public void TuCHoi(){
        XoaThongBao();
    }

    private void XoaThongBao(){
        // TODO: Xoa thong bao nay khoi database
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public int getNewNPT() {
        return newNPT;
    }

    public void setNewNPT(int newNPT) {
        this.newNPT = newNPT;
    }
}

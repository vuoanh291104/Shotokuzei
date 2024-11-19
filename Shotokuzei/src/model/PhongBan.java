package model;

public class PhongBan {
    private int id;
    private int idTruongPhong;
    private String tenPhongBan;

    public PhongBan() {
    }

    public PhongBan(int id, int idTruongPhong, String tenPhongBan) {
        this.id = id;
        this.idTruongPhong = idTruongPhong;
        this.tenPhongBan = tenPhongBan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTruongPhong() {
        return idTruongPhong;
    }

    public void setIdTruongPhong(int idTruongPhong) {
        this.idTruongPhong = idTruongPhong;
    }

    public String getTenPhongBan() {
        return tenPhongBan;
    }

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }
}

package model;

public class NhanVien extends Person{
    public NhanVien() {
    }

    public NhanVien(int uid, String name, String gmail, String numberPhone, int idPhongBan, String dob, String address, int soNguoiPhuThuoc) {
        super(ERole.NhanVien, uid, name, gmail, numberPhone, idPhongBan, dob, address, soNguoiPhuThuoc);
    }
}

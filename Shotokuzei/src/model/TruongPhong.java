package model;

public class TruongPhong extends Person{
    public TruongPhong() {
        super.setRole(ERole.TruongPhong);
    }

    public TruongPhong(String uid, String name, String gmail, String numberPhone, int idPhongBan, String dob, String address, int soNguoiPhuThuoc) {
        super(ERole.TruongPhong, uid, name, gmail, numberPhone, idPhongBan, dob, address, soNguoiPhuThuoc);
    }
}

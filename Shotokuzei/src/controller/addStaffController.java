package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.NhanVien;
import model.User;

public class addStaffController {
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDob;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtNumberPhone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSoNguoiPhuThuoc;

    @FXML
    private Button addNewStaff;

    public void initialize(){
        addEvent();
    }

    private void addEvent() {
        addNewStaff.setOnAction(actionEvent -> {
            NhanVien nv = new NhanVien();
            nv.setName(txtName.getText());
            nv.setDob(txtDob.getText());
            nv.setAddress(txtAddress.getText());
            nv.setGmail(txtEmail.getText());
            nv.setNumberPhone(txtNumberPhone.getText());
            nv.setSoNguoiPhuThuoc(Integer.parseInt(txtSoNguoiPhuThuoc.getText()));
            nv.setIdPhongBan(navBarController.phongBan.getId());
            nv.setUid(QueryController.getInstance().getNextID("taxdb.employees","employee_id"));

            User user = new User("Nhan Vien");

            QueryController.getInstance().InsertValue("Insert into taxdb.users (user_role, user_id, username, password) Values ('Nhan Vien','"+user.getUserId()+"','"+user.getUserId().toLowerCase()+"','"+user.getUserId().toLowerCase()+"');");
            QueryController.getInstance().InsertValue("Insert into taxdb.employees (employee_id, user_id, department_id, fullname, phone, email, birth, hometown, dependents) Values ('"+nv.getUid()+"','"+user.getUserId()+"','"+navBarController.phongBan.getId()+"','"+nv.getName()+"','"+nv.getNumberPhone()+"','"+nv.getGmail()+"','"+nv.getDob()+"','"+nv.getAddress()+"',"+nv.getSoNguoiPhuThuoc()+");");
        });
    }
}

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.NhanVien;
import model.Person;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class infoController {
    @FXML
    private Spinner<Integer> dependent;
    @FXML
    private Button submitBtn;
    @FXML
    private Text fullName;
    @FXML
    private Text birthDay;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextField address;
    public void initialize() {

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0, 1);
        dependent.setValueFactory(valueFactory);
        getInfo();

    }
    public  void EditOnClick(){
        submitBtn.setDisable(false);
    }

    public void getInfo(){
        String table ="";
        if(User.getInstance().getRole().equals("Nhan Vien")){
            table ="taxdb.employees";
        }else if(User.getInstance().getRole().equals("Truong phong")){
            table="taxdb.managers";
        }
        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();

        String query = "select fullname, phone, email, birth, hometown, dependents from " + table +" where user_id=?";
        PreparedStatement pstm = null;
        Person person = new Person();
        try {
            pstm = conn.prepareStatement(query);
            pstm.setString(1, User.getInstance().getUserId());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                person.setName(rs.getString("fullname"));
                person.setNumberPhone(rs.getString("phone"));
                person.setGmail(rs.getString("email"));
                person.setDob(rs.getString("birth"));
                person.setAddress(rs.getString("hometown"));
                person.setSoNguoiPhuThuoc(rs.getInt("dependents"));
            }
            fullName.setText(person.getName());
            birthDay.setText(person.getDob());
            phone.setText(person.getNumberPhone());
            email.setText(person.getGmail());
            address.setText(person.getAddress());
            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, person.getSoNguoiPhuThuoc(), 1);
            dependent.setValueFactory(valueFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

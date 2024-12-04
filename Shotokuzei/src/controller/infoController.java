package controller;

import javafx.event.ActionEvent;
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
import java.sql.Statement;

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
    @FXML
    private Text changeDependentTxt;

    private int currentDependents = 0;
    private  int tempDependent = 0;


    public void initialize() {

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0, 1);
        dependent.setValueFactory(valueFactory);
        getInfo();
        readNoti();

    }
    public  void EditOnClick(){
        phone.setDisable(false);
        dependent.setDisable(false);
        email.setDisable(false);
        address.setDisable(false);
        submitBtn.setDisable(false);
    }

    public void HandleSubmit(ActionEvent event){
        checkHasChangeDependents();
        editInfo();
        email.setDisable(true);
        dependent.setDisable(true);
        phone.setDisable(true);
        address.setDisable(true);
        getInfo();
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
                currentDependents = rs.getInt("dependents");
            }
            fullName.setText(person.getName());
            birthDay.setText(person.getDob());
            phone.setText(person.getNumberPhone());
            email.setText(person.getGmail());
            address.setText(person.getAddress());
            int displayDependent = readNoti()!=0 ? tempDependent : currentDependents;
            changeDependentTxt.setText(readNoti()!=0?"*Đang chờ phê duyệt":"");
            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, displayDependent, 1);
            dependent.setValueFactory(valueFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTableOfRole(){
        return User.getInstance().getRole().equals("Nhan Vien")?"taxdb.employees":"taxdb.managers";
    }

    public void editInfo(){

        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();

        String query = "UPDATE "+getTableOfRole()+ "\n" +
                "SET phone=?, email =?, hometown=?\n" +
                "WHERE user_id=?;";

//        System.out.println(query);
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(query);
            pstm.setString(1, phone.getText());
            pstm.setString(2,email.getText());
            pstm.setString(3, address.getText());
            pstm.setString(4,User.getInstance().getUserId());

            int row = pstm.executeUpdate();
            if(row != 0){
//                System.out.println("Cập nhật thành công " + row);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean checkHasChangeDependents(){
        int newDependent = dependent.getValue();
        if (currentDependents == newDependent) {
            return false;
        } else {
            createNoti(newDependent);

        }
        return true;
    }

    public void createNoti(int newDependents){
        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();

        String checkQuery = "SELECT COUNT(*) FROM notifications WHERE "+getNameid()+" = ?";
        String updateQuery = "UPDATE notifications SET accounting_id = 'AC01', change_dependents = ? WHERE "+getNameid()+" = ?";
        String insertQuery = "INSERT INTO notifications("+getNameid()+", accounting_id, change_dependents) VALUES (?, 'AC01', ?)";

        PreparedStatement pstmCheck = null;
        PreparedStatement pstmUpdate = null;
        PreparedStatement pstmInsert = null;

        try {
            // Kiểm tra xem employee_id đã tồn tại hay chưa
            pstmCheck = conn.prepareStatement(checkQuery);
            pstmCheck.setString(1, getPersonId());
            ResultSet rs = pstmCheck.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1); // Lấy số lượng dòng khớp với `employee_id`
            }

            if (count > 0) {
                // Thực hiện UPDATE nếu đã tồn tại
                pstmUpdate = conn.prepareStatement(updateQuery);
                pstmUpdate.setInt(1, newDependents);
                pstmUpdate.setString(2, getPersonId());
                int rowsUpdated = pstmUpdate.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Cập nhật thành công: " + rowsUpdated);
                }
            } else {
                // Thực hiện INSERT nếu chưa tồn tại
                pstmInsert = conn.prepareStatement(insertQuery);
                pstmInsert.setString(1, getPersonId());
                pstmInsert.setInt(2, newDependents);
                int rowsInserted = pstmInsert.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Thêm thành công: " + rowsInserted);
                }
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int readNoti(){
        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();

        String query = "select change_dependents from notifications where "+getNameid()+" = '"+getPersonId()+"'";
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()){
                tempDependent = rs.getInt("change_dependents");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempDependent;
    }

    public String getNameid(){
        return User.getInstance().getRole().equals("Nhan Vien")?"employee_id":"manager_id";
    }
    public String getPersonId(){

        String personId="";
        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();
        String query= "select "+getNameid()+ " from "+getTableOfRole() + " where user_id='" + User.getInstance().getUserId()+ "'";
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()){
                personId = rs.getString(getNameid());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return personId;
    }


}

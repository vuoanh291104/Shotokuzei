package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.NhanVien;
import model.Person;
import model.User;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class navBarController {

    @FXML
    private AnchorPane generalScene;
    private Button selectedButton;
    @FXML
    private Text nameDepartment;
    @FXML
    private Text fullName;
    private simpleDepartmentController simpleDepartmentController;

    public void initialize(){
        getNameUser();
    }
    public void getNameUser(){
        String table ="";
        if(User.getInstance().getRole().equals("Nhan Vien")){
            table ="taxdb.employees";
        }else if(User.getInstance().getRole().equals("Truong phong")){
            table="taxdb.managers";
        }
        System.out.print(table);
        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();
        String query = "select fullname from "+table+" where user_id=?";
        PreparedStatement pstm = null;

        Person person = new Person();
        try{
            pstm = conn.prepareStatement(query);
            pstm.setString(1,User.getInstance().getUserId());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                person.setName(rs.getString("fullname"));
            }

            fullName.setText(person.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SetDepartmentName(String nd){
        if(nameDepartment!=null){
            if(nd!=null)
                nameDepartment.setText(nd);
            else System.out.print("check ten");
        }else
            System.out.print("errr");

    }

    public void LoadView(String idScene, String folder) throws IOException {
        try {
            Parent newScene = FXMLLoader.load(getClass().getResource("/view/"+folder+"/" + idScene + ".fxml"));
            newScene.getStylesheets().add(getClass().getResource("/view/css/style.css").toExternalForm());
            generalScene.getChildren().clear();
            generalScene.getChildren().add(newScene);
            AnchorPane.setTopAnchor(newScene, 0.0);
            AnchorPane.setBottomAnchor(newScene, 0.0);
            AnchorPane.setRightAnchor(newScene, 0.0);
            AnchorPane.setLeftAnchor(newScene, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HandleButtonOnClick(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String idBtn = clickedButton.getId();
        List<String> classBtn = clickedButton.getStyleClass();

        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("selectedBtn");
            selectedButton.getStyleClass().add("primary-btn");
        }

        selectedButton = clickedButton;
        selectedButton.getStyleClass().remove("primary-btn");
        selectedButton.getStyleClass().add("selectedBtn");
        LoadView(idBtn,classBtn.get(1));
    }

    public void ChangePassOnClick(){
        try{
            LoadView("changePass","");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void MoveOnNotification(){
        try{
            LoadView("message","accountant");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

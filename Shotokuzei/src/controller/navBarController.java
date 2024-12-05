package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.NhanVien;
import model.Person;
import model.PhongBan;
import model.User;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class navBarController {

    public static PhongBan phongBan;

    @FXML
    private AnchorPane generalScene;
    private Button selectedButton;
    @FXML
    private Text nameDepartment;
    @FXML
    private Text fullName;
    private simpleDepartmentController simpleDepartmentController;

    public void initialize(){
        if(phongBan!=null && nameDepartment!=null){
            nameDepartment.setText(phongBan.getTenPhongBan());
        }
        if(AppController.getInstance().getUser().getRole().equals("Nhan Vien") || AppController.getInstance().getUser().getRole().equals("Truong phong")){
            getNameUser();
        }

    }
    public void getNameUser(){
        String table ="";
        if(AppController.getInstance().getUser().getRole().equals("Nhan Vien")){
            table ="taxdb.employees";
        }else if(AppController.getInstance().getUser().getRole().equals("Truong phong")){
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
            pstm.setString(1,AppController.getInstance().getUser().getUserId());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                person.setName(rs.getString("fullname"));
            }

            fullName.setText(person.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            if (selectedButton != null) {
                selectedButton.getStyleClass().remove("selectedBtn");
                selectedButton.getStyleClass().add("primary-btn");
                selectedButton = null; // Xóa trạng thái nút được chọn
            }
            LoadView("changePass", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MoveOnNotification(){
        try {
            if (selectedButton != null) {
                selectedButton.getStyleClass().remove("selectedBtn");
                selectedButton.getStyleClass().add("primary-btn");
                selectedButton = null; // Xóa trạng thái nút được chọn
            }
            LoadView("message", "accountant");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HandleLogout(MouseEvent event){
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận đăng xuất");
            alert.setHeaderText("Bạn có muốn đăng xuất không?");



            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppController.getInstance().setUser(null);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);


                String css = getClass().getResource("/view/css/style.css").toExternalForm();
                scene.getStylesheets().add(css);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoListDepartment(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accountant/listDepartments.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        String css = getClass().getResource("/view/css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

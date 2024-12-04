package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.PhongBan;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class simpleDepartmentController {
    @FXML
    private AnchorPane lsButton;

    public void initialize() throws SQLException {
        LoadDepartments();
    }

    public void LoadView(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accountant/general.fxml"));
            Parent root = loader.load();

            // Lấy controller của giao diện mới
            navBarController controller = loader.getController();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/view/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadDepartments() throws SQLException {
        int i = 0;
        ResultSet rs = QueryController.getInstance().Query("select department_id,department_name,manager_id from taxdb.departments");
        while(rs.next()){
            PhongBan phongBan = new PhongBan(rs.getString("department_id"),rs.getString("department_name"),rs.getString("manager_id"));
            lsButton.getChildren().add(createDepartmentButton(phongBan,i));
            i++;
        }

    }

    public Button createDepartmentButton(PhongBan phongBan, int index) {
        double layoutY = calculateLayoutY(index); // Tự động tính toán layoutY

        Button button = new Button(phongBan.getTenPhongBan());
        button.setPrefWidth(468.0);
        button.setPrefHeight(128.0);
        button.setLayoutX(52.0);
        button.setLayoutY(layoutY);
        button.getStyleClass().add("department");
        button.setFont(Font.font("Arial Bold", 32.0));

        // Thêm sự kiện khi nhấn nút
        button.setOnAction(e -> {
            navBarController.phongBan = phongBan;
            try {
                LoadView(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        return button;
    }

    // Hàm tính toán layoutY
    public double calculateLayoutY(int index) {
        return 50 + (index * 183.0);
    }
}

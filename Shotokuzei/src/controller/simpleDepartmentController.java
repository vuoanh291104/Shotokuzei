package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class simpleDepartmentController {
    private String nameDep;

    public void setNameDep(String nameDep){
        this.nameDep=nameDep;
    }
    public  String getNameDep(){
        return nameDep;
    }

    public void LoadView(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accountant/general.fxml"));
            Parent root = loader.load();

            // Lấy controller của giao diện mới
            navBarController controller = loader.getController();

            // Truyền tên phòng ban vào giao diện mới
            controller.SetDepartmentName(getNameDep());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/view/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goDepartment (ActionEvent event) throws IOException {
        Button clickBtn= (Button)event.getSource();
        setNameDep(clickBtn.getId());
        LoadView(event);

    }
}

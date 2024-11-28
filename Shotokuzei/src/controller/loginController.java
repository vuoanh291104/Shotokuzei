package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class loginController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void HandleLogin (ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/view/accountant/listDepartments.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}

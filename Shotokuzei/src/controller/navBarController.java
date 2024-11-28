package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


import java.io.IOException;
import java.util.List;

public class navBarController {

    @FXML
    private AnchorPane generalScene;
    private Button selectedButton;
    @FXML
    private Text nameDepartment;
    private simpleDepartmentController simpleDepartmentController;

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

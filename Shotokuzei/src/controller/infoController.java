package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class infoController {
    @FXML
    private Spinner<Integer> dependent;
    @FXML
    private Button submitBtn;
    public void initialize() {

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0, 1);
        dependent.setValueFactory(valueFactory);
    }
    public  void EditOnClick(){
        submitBtn.setDisable(false);
    }
}

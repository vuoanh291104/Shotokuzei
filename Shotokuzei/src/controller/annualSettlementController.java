package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import java.time.LocalDate;

public class annualSettlementController {
    @FXML
    ComboBox<Integer> chooseYear;

    public void initialize(){
        ViewChooseYear();
    }

    public void ViewChooseYear(){
        int currentYear = LocalDate.now().getYear();
        ObservableList<Integer> years = FXCollections.observableArrayList();
        for(int i = 2022 ; i <= currentYear; i++){
            years.add(i);
        }
        chooseYear.setItems(years);
        chooseYear.setValue(currentYear);
        chooseYear.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                    setStyle("-fx-text-fill: white;");
                } else {
                    setText(item.toString());
                    setStyle("-fx-text-fill: white;");
                }
            }
        });

        // Tùy chỉnh màu sắc cho các mục trong danh sách
        chooseYear.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setStyle("-fx-text-fill: #3382FE;");
                }
            }
        });
    }

}

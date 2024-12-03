package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class listStaffOfAccountant {
    @FXML
    private ComboBox<Integer> chooseYear;
    @FXML
    private ComboBox<Integer> chooseMonth;
    @FXML
    public void initialize() {
        ViewComboYear();
        ViewComboMonth();

    }


    public void ViewComboYear(){
        int currentYear = LocalDate.now().getYear();
        ObservableList<Integer> years = FXCollections.observableArrayList();

        for (int i = 2022; i <= currentYear; i++) {
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
    public void  ViewComboMonth(){
        int currentMonth = LocalDate.now().getMonthValue();
        ObservableList<Integer> months = FXCollections.observableArrayList();
        for(int i =1; i<=12; i++){
            months.add(i);
        }
        chooseMonth.setItems(months);
        chooseMonth.setValue(currentMonth);

        chooseMonth.setButtonCell(new ListCell<>() {
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
        chooseMonth.setCellFactory(listView -> new ListCell<>() {
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

    public int getYearSelected(){return chooseYear.getValue();}
    public int getMonthSelected(){return  chooseMonth.getValue();}
}

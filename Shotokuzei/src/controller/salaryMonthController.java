package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import java.time.LocalDate;

public class salaryMonthController {

    @FXML
    private ComboBox<Integer> chooseYear; // Khai báo kiểu dữ liệu rõ ràng

    @FXML
    public void initialize() {
        // Lấy năm hiện tại
        int currentYear = LocalDate.now().getYear();
        ObservableList<Integer> years = FXCollections.observableArrayList();

        // Thêm các năm từ 2020 đến năm hiện tại
        for (int i = 2020; i <= currentYear; i++) {
            years.add(i);
        }

        // Gán danh sách các năm vào ComboBox
        chooseYear.setItems(years);
        chooseYear.setValue(currentYear); // Đặt giá trị mặc định là năm hiện tại

        // Tùy chỉnh màu sắc cho promptText
        chooseYear.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                    setStyle("-fx-text-fill: white;"); // Màu trắng cho promptText
                } else {
                    setText(item.toString());
                    setStyle("-fx-text-fill: white;"); //
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
                    setStyle("-fx-text-fill: #3382FE;"); // Màu xanh cho các mục trong danh sách
                }
            }
        });
    }
}

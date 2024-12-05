package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import model.SalaryData;
import model.YearFee;

import java.sql.ResultSet;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class listStaffOfAccountant {
    @FXML
    private ComboBox<Integer> chooseYear;
    @FXML
    private ComboBox<Integer> chooseMonth;

    @FXML
    private TableView<SalaryData> listStaff;
    @FXML
    private TableColumn<SalaryData, Integer> sttColumn;
    @FXML
    private TableColumn<SalaryData, String> nameStaffColumn;
    @FXML
    private TableColumn<SalaryData, Integer> salaryColumn;
    @FXML
    private TableColumn<SalaryData, Integer> dependentsColumn;
    @FXML
    private TableColumn<SalaryData, Integer> totalDeductionsColumn;
    @FXML
    private TableColumn<SalaryData, Integer> taxMonthColumn;
    @FXML
    private TableColumn<SalaryData, String> phoneColumn;
    @FXML
    private TableColumn<SalaryData, String> emailColumn;
    @FXML
    private TableColumn<SalaryData, String> addressColumn;

    private ObservableList<SalaryData> listSalaryStaff = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ViewComboYear();
        ViewComboMonth();
        setupTableColumns();
        getListStaff(LocalDate.now().getYear(),LocalDate.now().getMonthValue());
    }

    public String formatNumber(int number) {
        if (number < 0) return "0";
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        return numberFormat.format(number);
    }

    private void setupTableColumns() {

        nameStaffColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        dependentsColumn.setCellValueFactory(new PropertyValueFactory<>("dependents"));
        totalDeductionsColumn.setCellValueFactory(new PropertyValueFactory<>("totalDeductions"));
        taxMonthColumn.setCellValueFactory(new PropertyValueFactory<>("tax"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("numberPhone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("gmail"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        sttColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getIndex() >= listSalaryStaff.size()) {
                    setText(null);
                } else {
                    setText(String.valueOf(getTableRow().getIndex() + 1));
                }
            }
        });

        // Đặt TextFieldTableCell cho cột salaryColumn
        salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // Xử lý khi người dùng chỉnh sửa giá trị
        salaryColumn.setCellFactory(column -> {
            return new TextFieldTableCell<>(new IntegerStringConverter()) {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(formatNumber(item)); // Hiển thị số đã được định dạng
                    }
                }
            };
        });

// Xử lý sự kiện chỉnh sửa
        salaryColumn.setOnEditCommit(event -> {
            SalaryData salaryData = event.getRowValue(); // Lấy đối tượng dòng hiện tại
            System.out.println(salaryData.getName());
            Integer newSalary = event.getNewValue(); // Lấy giá trị mới từ người dùng nhập
            if (newSalary != null) {
                salaryData.setSalary(newSalary); // Cập nhật giá trị salary vào đối tượng
                listSalaryStaff.set(event.getTablePosition().getRow(), salaryData); // Cập nhật lại ObservableList
            }
            listStaff.refresh(); // Làm mới bảng để hiển thị giá trị đã cập nhật
        });


        dependentsColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatNumber(item));
                }
            }
        });

        totalDeductionsColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatNumber(item));
                }
            }
        });

        taxMonthColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatNumber(item));
                }
            }
        });

        // Gán danh sách vào TableView
        listStaff.setItems(listSalaryStaff);

        // Đặt TableView ở chế độ có thể chỉnh sửa
        listStaff.setEditable(true);
    }

    public void ViewComboYear() {
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
        chooseYear.setOnAction(event -> getListStaff(getYearSelected(), getMonthSelected()));

    }

    public void ViewComboMonth() {
        int currentMonth = LocalDate.now().getMonthValue();
        ObservableList<Integer> months = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
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
        chooseMonth.setOnAction(event -> getListStaff(getYearSelected(), getMonthSelected()));
    }

    public int getYearSelected() {
        return chooseYear.getValue();
    }

    public int getMonthSelected() {
        return chooseMonth.getValue();
    }

    public void getListStaff(int year, int month) {
        listSalaryStaff.clear();
        String queryE = "SELECT e.fullname, e.dependents, e.hometown, e.phone, e.email, " +
                "p.salary, p.tax " +
                "FROM employees e " +
                "LEFT JOIN payroll p ON e.employee_id = p.employee_id " +
                "AND YEAR(p.time_pay) = " + year + " AND MONTH(p.time_pay) = " + month + " " +
                "WHERE e.department_id = '" + navBarController.phongBan.getId() + "'";
        String queryM = "SELECT m.fullname, m.dependents, m.hometown, m.phone, m.email, " +
                "p.salary, p.tax " +
                "FROM managers m " +
                "LEFT JOIN payroll p ON m.manager_id = p.manager_id " +
                "AND YEAR(p.time_pay) = " + year + " AND MONTH(p.time_pay) = " + month + " " +
                "JOIN departments d ON d.manager_id = m.manager_id " +
                "WHERE d.department_id = '" + navBarController.phongBan.getId() + "'";

        try {
            ResultSet rsE = QueryController.getInstance().Query(queryE);
            while (rsE.next()) {
                SalaryData salaryData1 = new SalaryData();
                salaryData1.setName(rsE.getString("fullname"));
                salaryData1.setDependents(rsE.getInt("dependents"));
                salaryData1.setTax(rsE.getObject("tax") != null ? rsE.getInt("tax") : 0); // Nếu null thì mặc định là 0
                salaryData1.setSalary(rsE.getObject("salary") != null ? rsE.getInt("salary") : 0); // Nếu null thì mặc định là 0
                salaryData1.setTotalDeductions(calculateDeductions(getYearSelected(), salaryData1.getDependents()));
                salaryData1.setNumberPhone(rsE.getString("phone"));
                salaryData1.setGmail(rsE.getString("email"));
                salaryData1.setAddress(rsE.getString("hometown"));
                listSalaryStaff.add(salaryData1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ResultSet rsM = QueryController.getInstance().Query(queryM);
            while (rsM.next()) {
                SalaryData salaryData2 = new SalaryData();
                salaryData2.setName(rsM.getString("m.fullname"));
                salaryData2.setDependents(rsM.getInt("m.dependents"));
                salaryData2.setTax(rsM.getObject("tax") != null ? rsM.getInt("tax") : 0); // Nếu null thì mặc định là 0
                salaryData2.setSalary(rsM.getObject("salary") != null ? rsM.getInt("salary") : 0);
                salaryData2.setTotalDeductions(calculateDeductions(getYearSelected(), salaryData2.getDependents()));
                salaryData2.setNumberPhone(rsM.getString("m.phone"));
                salaryData2.setGmail(rsM.getString("m.email"));
                salaryData2.setAddress(rsM.getString("m.hometown"));
                listSalaryStaff.add(salaryData2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int calculateDeductions ( int year, int dependents){
        YearFee.getFee(year);
        int selfFee = YearFee.getSelfFee();
        int dependentFee = YearFee.getDependentFee();
        int totalFee = selfFee + dependents * dependentFee;
        return totalFee;
    }
}
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Person;
import model.SalaryData;
import model.User;
import model.YearFee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

public class listStaffController {
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
        getListStaff(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    public String formatNumber(int number) {
        if (number < 0) return "0";
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        return numberFormat.format(number);
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
        chooseYear.setOnAction(event -> getListStaff(getMonthSelected(),getYearSelected()) );
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
        chooseMonth.setOnAction(event -> getListStaff(getMonthSelected(),getYearSelected()));
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

        salaryColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatNumber(item)); // Hàm định dạng số
                }
            }
        });

        // Định dạng cột dependents
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

        // Định dạng cột totalDeductions
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

        // Định dạng cột taxMonth
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

    }

    public int getYearSelected(){return chooseYear.getValue();}
    public int getMonthSelected(){return  chooseMonth.getValue();}

    public void getListStaff(int month, int year){
        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();

        String query = "select e.fullname, e.dependents, p.employee_id, p.time_pay, p.salary, p.tax, e.phone , e.email, e.hometown " +
                " FROM employees e join payroll p on e.employee_id = p.employee_id " +
                "where e.department_id='"+getIdDepartment()+"' and year(p.time_pay)="+year +" and month(p.time_pay)="+month+";";


        listSalaryStaff.clear();
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            boolean hasData=false;
            while (rs.next()){
                hasData=true;
                SalaryData salaryData = new SalaryData();

                salaryData.setName(rs.getString("e.fullname"));
                salaryData.setDependents(rs.getInt("e.dependents"));
                salaryData.setTax(rs.getInt("p.tax"));
                salaryData.setSalary(rs.getInt("p.salary"));
                salaryData.setTotalDeductions(calculateDeductions(getYearSelected(),salaryData.getDependents()));
                salaryData.setNumberPhone(rs.getString("e.phone"));
                salaryData.setGmail(rs.getString("e.email"));
                salaryData.setAddress(rs.getString("e.hometown"));
                listSalaryStaff.add(salaryData);

            }
            if (!hasData) {

                listStaff.setPlaceholder(new Label("Lương " + month + "/" + year +" đang được cập nhật"));
            }
            listStaff.setItems(listSalaryStaff);
            listStaff.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getIdDepartment(){
        String idDep="";
        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();

        String query = "select d.department_id from departments d join managers m on d.manager_id = m.manager_id where user_id='"+ AppController.getInstance().getUser().getUserId()+"'";

        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet rs =stm.executeQuery(query);
            while (rs.next()){
                idDep = rs.getString("d.department_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idDep;
    }

    private int calculateDeductions(int year,int dependents) {
        YearFee.getFee(year);
        int selfFee = YearFee.getSelfFee();
        int dependentFee = YearFee.getDependentFee();
        int totalFee = selfFee + dependents*dependentFee;
        return totalFee;
    }
}



package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.TaxCalculator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class tryCacuTaxController {
    @FXML
    private Text giamTruCaNhan;
    @FXML
    private Text giamTruNPT;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField dependentField;
    @FXML
    private Text totalGiamtru;
    @FXML
    private Text taxTry;

    private int gtCaNhan;
    private int gtNPT;

    public void initialize() {
        getGTru();
    }

    public String formatNumber(int number) {
        if (number < 0) return "0";
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        return numberFormat.format(number);
    }


    @FXML
    public void formatSalaryField(KeyEvent event) {
        try {
            String text = salaryField.getText().replace(".", "").trim();
            int salary = Integer.parseInt(text);
            salaryField.setText(formatNumber(salary));
            salaryField.end(); // Di chuyển con trỏ về cuối trường
        } catch (NumberFormatException e) {

        }
    }


    public void OnclickTryCacuBtn() {
        try {
            // Lấy dữ liệu từ các trường nhập liệu
            String salaryText = salaryField.getText().replace(".", "").trim();
            int salary = Integer.parseInt(salaryText);

            int dependent = 0;
            if (!dependentField.getText().isEmpty()) {
                String dependentText = dependentField.getText().replace(".", "").trim();
                dependent = Integer.parseInt(dependentText);
            }


            TaxCalculator taxCalculator = new TaxCalculator();
            int tax = (int)taxCalculator.taxMonthly(salary,dependent,LocalDate.now().getYear());
            taxTry.setText(formatNumber(tax) + " đ");

            // Tính tổng giảm trừ
            int total = gtCaNhan + gtNPT * dependent;
            totalGiamtru.setText("Giảm trừ/ tháng: " + formatNumber(total) + " đ");

        } catch (NumberFormatException e) {
            taxTry.setText("Vui lòng nhập số hợp lệ.");
        }
    }

   

    public void getGTru() {
        int currentYear = LocalDate.now().getYear();
        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();

        String query = "SELECT * FROM taxdb.deductions WHERE year_apply='" + currentYear + "'";
        try (Statement stm = conn.createStatement(); ResultSet rs = stm.executeQuery(query)) {
            while (rs.next()) {
                gtCaNhan = rs.getInt("self_fee");
                gtNPT = rs.getInt("dependents_fee");
            }
            giamTruCaNhan.setText("*Giảm trừ thu nhập cá nhân/ tháng: " + formatNumber(gtCaNhan) + " đ");
            giamTruNPT.setText("*Giảm trừ người phụ thuộc: " + formatNumber(gtNPT) + " đ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

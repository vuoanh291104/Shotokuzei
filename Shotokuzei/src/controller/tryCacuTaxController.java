package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

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

            // Tính thuế
            int tax = (int) tax(salary, dependent);
            taxTry.setText(formatNumber(tax) + " đ");

            // Tính tổng giảm trừ
            int total = gtCaNhan + gtNPT * dependent;
            totalGiamtru.setText("Giảm trừ/ tháng: " + formatNumber(total) + " đ");

        } catch (NumberFormatException e) {
            taxTry.setText("Vui lòng nhập số hợp lệ.");
        }
    }

   
    public double tax(int salary, int dependent) {
        double tax = 0;
        int TNTT = salary - gtCaNhan - (gtNPT * dependent);
        if (TNTT > 0) {
            if (TNTT <= 5000000) { // Bậc 1
                tax = TNTT * 0.05;
            } else if (TNTT <= 10000000) { // Bậc 2
                tax = 250000 + (TNTT - 5000000) * 0.1;
            } else if (TNTT <= 18000000) { // Bậc 3
                tax = 750000 + (TNTT - 10000000) * 0.15;
            } else if (TNTT <= 32000000) { // Bậc 4
                tax = 1950000 + (TNTT - 18000000) * 0.2;
            } else if (TNTT <= 52000000) { // Bậc 5
                tax = 4750000 + (TNTT - 32000000) * 0.25;
            } else if (TNTT <= 80000000) { // Bậc 6
                tax = 9750000 + (TNTT - 52000000) * 0.3;
            } else { // Bậc 7
                tax = 18150000 + (TNTT - 80000000) * 0.35;
            }
        }
        return tax;
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

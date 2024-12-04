package controller;

import controller.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class changePassController {
    @FXML
    private PasswordField currentPass;
    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField newPassAgain;
    @FXML
    private Text errorCurrentPass;
    @FXML
    private Text errorNewPass;

    public void HandleChangePass(ActionEvent event) {
        errorCurrentPass.setText("");
        errorNewPass.setText("");

        // Kiểm tra mật khẩu hiện tại
        if (!checkCurrentPass()) {
            errorCurrentPass.setText("Mật khẩu hiện tại không đúng");
            return; // Thoát nếu kiểm tra thất bại
        }

        // Kiểm tra mật khẩu mới
        if (!checkNewPassAgain()) {
            errorNewPass.setText("Mật khẩu mới không khớp");
            return; // Thoát nếu kiểm tra thất bại
        }

        // Nếu mọi kiểm tra đều thành công, cập nhật mật khẩu
        if (updatePass(newPass.getText())) {
            showSuccessAndRedirect(event);
        }
    }

    public String getPassOfUser() {
        String curPass = "";
        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();

        String query = "SELECT password FROM users WHERE user_id='" + User.getInstance().getUserId() + "'";
        try (Statement stm = conn.createStatement(); ResultSet rs = stm.executeQuery(query)) {
            if (rs.next()) {
                curPass = rs.getString("password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curPass;
    }

    public boolean checkCurrentPass() {
        if (currentPass.getText().trim().isEmpty()) {
            return false;
        }
        return currentPass.getText().equals(getPassOfUser());
    }

    public boolean checkNewPassAgain() {
        if (newPass.getText().trim().isEmpty() || newPassAgain.getText().trim().isEmpty()) {
            return false;
        }
        return newPass.getText().equals(newPassAgain.getText());
    }

    public boolean updatePass(String newPass) {
        ConnectDB connectDB = new ConnectDB();
        Connection conn = connectDB.connect();

        String query = "UPDATE users SET password=? WHERE user_id='" + User.getInstance().getUserId() + "'";
        try (PreparedStatement pstm = conn.prepareStatement(query)) {
            pstm.setString(1, newPass);
            int row = pstm.executeUpdate();
            return row != 0; // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showSuccessAndRedirect(ActionEvent event) {
        // Hiển thị thông báo thành công
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Cập nhật mật khẩu thành công!");
        alert.showAndWait();

        // Chuyển sang scene đăng nhập
        try {
            User.setInstance(null);

            // Tải FXML của màn hình đăng nhập
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();

            // Tạo Scene và gắn stylesheet
            Scene scene = new Scene(root);
            String css = getClass().getResource("/view/css/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            // Lấy Stage hiện tại và chuyển Scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

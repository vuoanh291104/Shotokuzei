package model;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Logic kiểm tra đăng nhập
        if (username.equals("admin") && password.equals("password")) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

}

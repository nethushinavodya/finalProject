package lk.gdse.hotelmanagement.crystalwave.controller.HomeControllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;
import lk.gdse.hotelmanagement.crystalwave.dto.UserDTO;
import lk.gdse.hotelmanagement.crystalwave.model.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class LoginController {

    @FXML private Label errorMsg;
    UserDTO userDTO;

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        emailField.setBorder(null);
        passwordField.setBorder(null);
        errorMsg.setText("");

        if (email.isEmpty() || password.isEmpty()) {
            errorMsg.setText("Please enter both email and password.");
            if (email.isEmpty()) {
                emailField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
            if (password.isEmpty()) {
                passwordField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
            return;
        }

        if (validateUserCredentials(email, password)) {
            errorMsg.setText("");
            if (userDTO.getRole().equals("Admin")) {
                navigateToAdminDashboard();
            } else {
                navigateToReceptionistDashboard();
            }
        } else {
            errorMsg.setText("Incorrect email or password.");
            if (userDTO == null || !BCrypt.checkpw(password, userDTO.getPassword())) {
                passwordField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
            if (userDTO == null || !userDTO.getEmail().equals(email)) {
                emailField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
    }
    public boolean isValid(){
        if(
                emailField.getText().matches("^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")&&
                passwordField.getText() != null
        ){
            return true;
        }else {
            return false;
        }
    }
    private boolean validateUserCredentials(String email, String password) {
        try {
            userDTO = UserModel.getUserByEmailAndRole(email);
            if (userDTO != null && BCrypt.checkpw(password, userDTO.getPassword())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            errorMsg.setText("Database error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    private void navigateToAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminView/AdminDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.close();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setTitle("Admin Dashboard");

            TranslateTransition translateTransition = new TranslateTransition(javafx.util.Duration.seconds(0.4), scene.getRoot());
            translateTransition.setFromX(600);
            translateTransition.setToX(0);
            translateTransition.play();

            newStage.show();
            newStage.centerOnScreen();

        } catch (Exception e) {
            errorMsg.setText("Failed to load Admin Dashboard.");
            e.printStackTrace();
        }
    }

    private void navigateToReceptionistDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReceptionistView/ReceptionistDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.close();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setTitle("Receptionist Dashboard");

            TranslateTransition translateTransition = new TranslateTransition(javafx.util.Duration.seconds(0.5), scene.getRoot());
            translateTransition.setFromX(600);
            translateTransition.setToX(0);
            translateTransition.play();

            newStage.show();
            newStage.centerOnScreen();

        } catch (Exception e) {
            errorMsg.setText("Failed to load Receptionist Dashboard.");
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeView/SignUpPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            errorMsg.setText("Failed to load Sign Up page.");
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToForgotPassword() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeView/ForgotPassword.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            errorMsg.setText("Failed to load Forgot Password page.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEmailField() {
        emailField.setBorder(null);
        errorMsg.setText(" ");
        emailField.clear();
    }

    @FXML
    private void handlePasswordField() {
        passwordField.setBorder(null);
        errorMsg.setText(" ");
        passwordField.clear();
    }

    public void emailOnKeyRelease(KeyEvent keyEvent) {
        if(emailField.getText().matches("^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
            emailField.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }else{
            emailField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }
}

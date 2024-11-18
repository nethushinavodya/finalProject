package lk.gdse.hotelmanagement.crystalwave.controller.HomeControllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
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
import lk.gdse.hotelmanagement.crystalwave.Database.Database;
import lk.gdse.hotelmanagement.crystalwave.model.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class LoginController {

    @FXML private Label errorMsg;
    UserModel userModel;

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
            if (userModel.getRole().equals("Admin")) {
                navigateToAdminDashboard();
            } else {
                navigateToReceptionistDashboard();
            }
        } else {
            errorMsg.setText("Incorrect email or password.");
            if (userModel == null || !BCrypt.checkpw(password, userModel.getPassword())) {
                passwordField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
            if (userModel == null || !userModel.getEmail().equals(email)) {
                emailField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
    }

    private boolean validateUserCredentials(String email, String password) {
        try {
            userModel = Database.getUserByEmailAndRole(email);
            if (userModel != null && BCrypt.checkpw(password, userModel.getPassword())) {
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
}

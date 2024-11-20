package lk.gdse.hotelmanagement.crystalwave.controller.HomeControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lk.gdse.hotelmanagement.crystalwave.dto.UserDTO;
import lk.gdse.hotelmanagement.crystalwave.model.UserModel;
import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class  ForgotPasswordController {

    @FXML private TextField emailField;
    @FXML private PasswordField newPasswordField;
    @FXML private Label emailError;

    @FXML
    private void handleResetPassword() throws SQLException {
       String email = emailField.getText().trim();
        String newPassword = newPasswordField.getText().trim();

        if (email.isEmpty() || newPassword.isEmpty()) {
            showAlert(AlertType.WARNING, "Input Error", "Please fill in both email and password fields.");
            return;
        }

        if (newPassword.length() < 4) {
            showAlert(AlertType.WARNING, "Weak Password", "Password is too short");
            return;
        }

        UserDTO userModel = findUserByEmail(email);
        if (userModel == null) {
            emailField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            emailError.setText("Entered email is invalid.");
            emailError.setTextFill(Color.RED);
            return;
        }

        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        if (updatePassword(userModel, hashedPassword)) {
            showAlert(AlertType.INFORMATION, "Password Reset", "Your password has been successfully reset.");
            navigateToLogin();
        } else {
            showAlert(AlertType.ERROR, "Update Failed", "Failed to update the password. Please try again.");
        }
    }

    private UserDTO findUserByEmail(String email) throws SQLException {
        return UserModel.getUserByEmail(email);
    }

    private boolean updatePassword(UserDTO userModel, String newPassword) throws SQLException {
        return UserModel.updateUserPassword(userModel, newPassword);
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void navigateToLogin() {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeView/LoginPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Navigation Error", "Could not navigate to the login page.");
        }
    }

    @FXML
    public void emailClear(MouseEvent mouseEvent) {
        emailField.clear();
        emailField.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        emailError.setText("");
    }

    public void emailOnKeyRelease(KeyEvent keyEvent) {
        if(emailField.getText().matches("^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")){
            emailField.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }else {
            emailField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }
}

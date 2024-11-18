package lk.gdse.hotelmanagement.crystalwave.controller.HomeControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.gdse.hotelmanagement.crystalwave.model.UserModel;
import lk.gdse.hotelmanagement.crystalwave.Database.Database;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class SignUpController {

    @FXML private TextField passwordField;
    @FXML private TextField confirmPasswordField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField addressField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> roleComboBox;

    @FXML
    private void handleSignUp() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String address = addressField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        String role = roleComboBox.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() ||
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role == null) {
            showAlert(AlertType.WARNING, "Form Error", "Please fill in all fields.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showAlert(AlertType.WARNING, "Password Error", "Passwords do not match.");
            return;
        }
        if (!isValidEmail(email)) {
            showAlert(AlertType.WARNING, "Email Error", "Please enter a valid email address.");
            return;
        }

        try {
            if (Database.getUserByEmailAndRole(email) != null) {
                showAlert(AlertType.WARNING, "Email Exists", "This email is already registered.");
                return;
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Error checking email in database.");
            e.printStackTrace();
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        UserModel userModel = new UserModel(firstName, lastName, phoneNumber, address, email, hashedPassword, role);

        try {
            if (Database.saveUser(userModel)) {
                showAlert(AlertType.INFORMATION, "Sign Up Successful", "You have successfully signed up!");
                navigateToLogin();
            } else {
                showAlert(AlertType.ERROR, "Sign Up Failed", "An error occurred while signing up.");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Unable to save user data.");
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeView/LoginPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) firstNameField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Failed to load the Login page.");
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}

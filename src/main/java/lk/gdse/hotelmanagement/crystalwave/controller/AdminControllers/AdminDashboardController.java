package lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private AnchorPane mainContentArea;

    public void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeView/LoginPage.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) mainContentArea.getScene().getWindow();
            currentStage.close();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            TranslateTransition translateTransition = new TranslateTransition(javafx.util.Duration.seconds(0.5), scene.getRoot());
            translateTransition.setFromX(600);
            translateTransition.setToX(0);
            translateTransition.play();

            newStage.show();
            newStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Logout Error", "Failed to log out and navigate to the login page.");
        }
    }
    public void navigateToRoomManagement() {
        loadPage("/AdminView/RoomManagement.fxml");
    }

    public void navigateToEmployeeManagement() {
        loadPage("/AdminView/EmployeeManagement.fxml");
    }

    public void navigateToInventory() {
        loadPage("/AdminView/InventoryManagement.fxml");
    }

    public void navigateToReports() {
        loadPage("/AdminView/Reports.fxml");
    }

    public void navigateToDiscounts(ActionEvent actionEvent) {
        loadPage("/AdminView/AddDiscount.fxml");
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node page = loader.load();

            mainContentArea.getChildren().clear();
            mainContentArea.getChildren().add(page);

            AnchorPane.setTopAnchor(page, 0.0);
            AnchorPane.setBottomAnchor(page, 0.0);
            AnchorPane.setLeftAnchor(page, 0.0);
            AnchorPane.setRightAnchor(page, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Page Loading Error", "Failed to load the page: " + fxmlPath + "\nError: " + e.getMessage());
        }
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


}

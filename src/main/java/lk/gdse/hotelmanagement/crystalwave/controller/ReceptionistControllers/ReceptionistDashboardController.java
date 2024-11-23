package lk.gdse.hotelmanagement.crystalwave.controller.ReceptionistControllers;

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

public class ReceptionistDashboardController {

    @FXML
    private AnchorPane mainContentArea;
    public void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeView/LoginPage.fxml"));
            Parent loginPage = loader.load();

            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(loginPage));
            loginStage.setTitle("Login");
            loginStage.centerOnScreen();

            loginStage.show();
            Stage currentStage = (Stage) mainContentArea.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Logout Error", "Failed to log out and navigate to the login page.");
        }
    }
    public void navigateToCheckIn() {
        loadPage("/ReceptionistView/CheckInPage.fxml");
    }
    public void navigateToCheckOut() {
        loadPage("/ReceptionistView/CheckOutPage.fxml");
    }
    public void navigateToGuestInfo() {
        loadPage("/ReceptionistView/CheckInDetail.fxml");
    }
    public void navigateToRoom() {
        loadPage("/ReceptionistView/RoomDetailPage.fxml");
    }
    public void navigateToService(ActionEvent actionEvent) {
        loadPage("/ReceptionistView/ServiceReservation.fxml");
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

package lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReportsController {

    public TableColumn reportTypeColumn;
    public TableColumn reportDateRangeColumn;
    public TableColumn totalAmountColumn;
    public TableColumn totalEmployeesColumn;
    public TableColumn totalRoomsColumn;
    @FXML
    private TableView<?> reportTable;

    @FXML
    private void initialize() {

    }

    @FXML
    private void generateReport() {
        showAlert("Report Generated", "The report has been generated successfully.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleGenerateReport(ActionEvent actionEvent) {
    }

    public void handleGenerateEmployeeReport(ActionEvent actionEvent) {
    }

    public void handleGenerateRoomStatusReport(ActionEvent actionEvent) {
    }

    public void handleGenerateBillingReport(ActionEvent actionEvent) {
    }
}
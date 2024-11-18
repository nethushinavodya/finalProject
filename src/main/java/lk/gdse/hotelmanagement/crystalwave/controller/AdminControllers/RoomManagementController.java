package lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.gdse.hotelmanagement.crystalwave.dto.RoomTypeDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.RoomTypeTm;
import lk.gdse.hotelmanagement.crystalwave.model.RoomTypeManagementModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RoomManagementController {

    public TableColumn <?,?> roomNumberColumn;
    public TableColumn <?,?>roomTypeColumn;
    public TableColumn <?,?>roomPriceColumn;
    public TableColumn <?,?>coldesc;
    public TextField addRoomTypeField;
    public TextField addRoomTypeRateField;
    public TextField addRoomNumberField;
    public TextField removeRoomNumberField;
    public TextField addRoomTypeDescField;
    public AnchorPane roomContent;

    @FXML
    private TableView<RoomTypeTm> roomTable;

    @FXML
    private void initialize() throws SQLException {
        setCellvalue();
        setAll();
    }

    private void setCellvalue() {
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomTypeName"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("roomTypeDescription"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("roomTypePrice"));
    }

    private void setAll() throws SQLException {
        ObservableList<RoomTypeTm> roomTypeTms = FXCollections.observableArrayList();

        List<RoomTypeTm> list = RoomTypeManagementModel.getAll();

        for (RoomTypeTm roomTypeTm : list) {
            roomTypeTms.add(roomTypeTm);
        }
        roomTable.setItems(roomTypeTms);

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();


    }

    public void handleRemoveRoom(ActionEvent actionEvent) throws SQLException {
        String roomNumber = removeRoomNumberField.getText();

        boolean isDeleted = RoomTypeManagementModel.delete(roomNumber);

        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Room Deleted").show();
            setAll();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR, "Room Not Deleted").show();
        }
    }

    public void handleAddRoomType(ActionEvent actionEvent) throws SQLException {
        String roomType = addRoomTypeField.getText();
        double price = Double.parseDouble(addRoomTypeRateField.getText());
        String roomNumber = addRoomNumberField.getText();
        String description = addRoomTypeDescField.getText();

        RoomTypeDTO roomTypeDTO = new RoomTypeDTO(roomNumber,roomType,description,price);
        boolean isSave = RoomTypeManagementModel.save(roomTypeDTO);

        if (isSave) {
            new Alert(Alert.AlertType.INFORMATION, "Room added successfully").show();
            setAll();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR, "Room not added successfully").show();
        }
    }

    public void roomTable(MouseEvent mouseEvent) {
        TableView <RoomTypeTm> tableView= (TableView<RoomTypeTm>) mouseEvent.getSource();
        RoomTypeTm selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        addRoomNumberField.setText(selectedEmployee.getRoomTypeId());
        addRoomTypeField.setText(selectedEmployee.getRoomTypeName());
        addRoomTypeDescField.setText(selectedEmployee.getRoomTypeDescription());
        addRoomTypeRateField.setText(String.valueOf(selectedEmployee.getRoomTypePrice()));
    }

    public void handleUpdate(ActionEvent actionEvent) throws SQLException {
        String roomNumber = addRoomNumberField.getText();
        String roomType = addRoomTypeField.getText();
        String roomTypeDescription = addRoomTypeDescField.getText();
        double roomTypePrice = Double.parseDouble(addRoomTypeRateField.getText());

        RoomTypeDTO roomTypeDTO = new RoomTypeDTO(roomNumber,roomType,roomTypeDescription,roomTypePrice);
        boolean isUpdate = RoomTypeManagementModel.update(roomTypeDTO);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Room updated successfully").show();
            setAll();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR, "Room not updated successfully").show();
        }
    }

    public void handleRoom(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminView/AddRoomPage.fxml"));
            Parent rootNode = loader.load();
            roomContent.getChildren().clear();
            roomContent.getChildren().add(rootNode);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Page Loading Error", "Failed to load the page: " + "\nError: " + e.getMessage());
        }
    }
    public void clear(){
        addRoomNumberField.clear();
        addRoomTypeField.clear();
        addRoomTypeDescField.clear();
        addRoomTypeRateField.clear();
        removeRoomNumberField.clear();
    }
}

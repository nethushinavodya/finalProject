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
import javafx.scene.input.KeyEvent;
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
        getCurrentrTId();
    }
    private void getCurrentrTId() {
        try {
            String currentrTypeId = RoomTypeManagementModel.getCurrentTypeId();

            String nextrTypeId = generateNextRTypeId(currentrTypeId);
            addRoomNumberField.setText(nextrTypeId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextRTypeId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("T");
            int idNum = Integer.parseInt(split[1]);
            return "T" + ++idNum;
        }
        return "T1";
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
            new Alert(Alert.AlertType.CONFIRMATION, "Room Deleted").show();
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
        if (isValid()){
            RoomTypeDTO roomTypeDTO = new RoomTypeDTO(roomNumber,roomType,description,price);
            boolean isSave = RoomTypeManagementModel.save(roomTypeDTO);

            if (isSave) {
                new Alert(Alert.AlertType.CONFIRMATION, "Room added successfully").show();
                setAll();
                getCurrentrTId();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR, "Room not added successfully").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Input Valid Data").show();
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
            new Alert(Alert.AlertType.CONFIRMATION, "Room updated successfully").show();
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

    public void roomTypeOnKeyRelease(KeyEvent keyEvent) {
        if (addRoomTypeField.getText().matches("[a-zA-Z ]+")){
            addRoomTypeField.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }else{
            addRoomTypeField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }

    public void DescriptionOnKeyRelease(KeyEvent keyEvent) {
        if (addRoomTypeDescField.getText().matches("[a-zA-Z ]+")){
            addRoomTypeDescField.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px; ");
        }else {
            addRoomTypeDescField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }

    public void priceOnKeyRelease(KeyEvent keyEvent) {
        if(addRoomTypeRateField.getText().matches("\\d{4,}")){
            addRoomTypeRateField.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }else {
            addRoomTypeRateField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }

    public boolean removeOnKeyRelease(KeyEvent keyEvent) {
        if (removeRoomNumberField.getText().matches("\\d{1,}")){
            removeRoomNumberField.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
            return true;
        }else {
            removeRoomNumberField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
            return false;
        }
    }
    public boolean isValid() {
        if(addRoomTypeField.getText().matches("[a-zA-Z ]+")&&
                addRoomTypeDescField.getText().matches("[a-zA-Z ]+")&&
                addRoomTypeRateField.getText().matches("\\d{4,}")){
            return true;
        }else{
            return false;
        }
    }
}

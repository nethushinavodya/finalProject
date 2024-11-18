package lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.gdse.hotelmanagement.crystalwave.dto.AddItemDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.AddRoomDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.AddRoomTM;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.InventoryTm;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.RoomTypeTm;
import lk.gdse.hotelmanagement.crystalwave.model.AddItemModel;
import lk.gdse.hotelmanagement.crystalwave.model.AddRoomModel;
import lk.gdse.hotelmanagement.crystalwave.model.InventoryManagementModel;
import lk.gdse.hotelmanagement.crystalwave.model.RoomTypeManagementModel;

import java.sql.SQLException;
import java.util.List;

public class AddRoomController {

    public TextField addRoomId;
    public TextField addRoomNumber;
    public ComboBox <String> addRoomType;
    public TextField addRoomStatus;
    public TableView tableView;
    public ComboBox <String>roomStCmb;
    public ComboBox <String> roomIdCmb;
    public ComboBox<String> inventoryIdCmb;
    @FXML
    private TableColumn<?, ?> roomId;

    @FXML
    private TableColumn<?, ?> roomNumber;

    @FXML
    private TableColumn<?, ?> roomStatus;



    @FXML
    private TableColumn<?, ?> roomTypeId;

    public void initialize() throws SQLException {
        setComboBox();
        setAll();
        setCellValue();
        roomStCmb.getItems().addAll("Available","Occupied" );
        setRoomIdCmb();
        setInventoryCmb();
    }

    private void setInventoryCmb() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try{
            List<InventoryTm> idList = InventoryManagementModel.getAll();
            for (InventoryTm addRoomTM : idList) {
                obList.add(addRoomTM.getItemId());
            }
            inventoryIdCmb.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRoomIdCmb() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try{
            List<AddRoomDTO> idList = AddRoomModel.getAll();
            for (AddRoomDTO addRoomTM : idList) {
                obList.add(addRoomTM.getRoomId());
            }
            roomIdCmb.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setAll() throws SQLException {
        ObservableList<AddRoomTM> addRoom = FXCollections.observableArrayList();

        List<AddRoomDTO> list = AddRoomModel.getAll();

        for (AddRoomDTO addRoomDTO : list) {
            AddRoomTM addRoomTM = new AddRoomTM();
            addRoomTM.setRoomId(addRoomDTO.getRoomId());
            addRoomTM.setRoomNumber(addRoomDTO.getRoomNumber());
            addRoomTM.setRoomStatus(addRoomDTO.getRoomStatus());
            addRoomTM.setRoomTypeId(addRoomDTO.getRoomTypeId());
            addRoom.add(addRoomTM);

        }
        tableView.setItems(addRoom);
    }


    private void setCellValue() {
        roomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomStatus.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));
        roomTypeId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
    }
    private void setComboBox() {
        ObservableList<String> obList = FXCollections.observableArrayList();
         try{
             List<RoomTypeTm> idList = RoomTypeManagementModel.getAll();
             for (RoomTypeTm addRoomTM : idList) {
                 obList.add(addRoomTM.getRoomTypeId());
             }
             addRoomType.setItems(obList);

         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
    }

    @FXML
    void handleDelete(ActionEvent event) throws SQLException {
        String roomId = addRoomId.getText();

        try {
            boolean isDelete = AddRoomModel.delete(Integer.parseInt(roomId));
            if (isDelete) {
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully").show();
                setAll();
            }else {
                new Alert(Alert.AlertType.ERROR, "Employee not deleted successfully").show();
            }
        }catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid room number").show();
        }

    }

    @FXML
    void handleSave(ActionEvent event) throws SQLException {
        String roomId = addRoomId.getText();
        String roomNumber = addRoomNumber.getText();
        String roomStatus = roomStCmb.getValue();
        String roomTypeId = addRoomType.getValue();

        try {
            AddRoomDTO addRoomDTO = new AddRoomDTO(roomId,roomNumber,roomStatus,roomTypeId);
            boolean isSave = AddRoomModel.save(addRoomDTO);

            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Room added successfully").show();
                setAll();
            }else {
                new Alert(Alert.AlertType.ERROR, "Room added successfully").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }


    }

    @FXML
    void handleUpdate(ActionEvent event) throws SQLException {
        String roomId = addRoomId.getText();
        String roomNumber = addRoomNumber.getText();
        String roomStatus = roomStCmb.getValue();
        String roomTypeId = addRoomType.getValue();

        try {
            AddRoomDTO addRoomDTO = new AddRoomDTO(roomId,roomNumber,roomStatus,roomTypeId);
            boolean isUpdate = AddRoomModel.update(addRoomDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Room added successfully").show();
                setAll();
            }else {
                new Alert(Alert.AlertType.ERROR, "Room added successfully").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void tableRoom(MouseEvent event) {
        TableView <AddRoomTM> tableView= (TableView<AddRoomTM>) event.getSource();
        AddRoomTM addRoomTM = tableView.getSelectionModel().getSelectedItem();
       addRoomId.setText(addRoomTM.getRoomId());
       addRoomNumber.setText(addRoomTM.getRoomNumber());
       roomStCmb.setValue(addRoomTM.getRoomStatus());
       addRoomType.setValue(addRoomTM.getRoomTypeId());


    }

    public void handleInventory(ActionEvent actionEvent) throws SQLException {
        String RoomId = roomIdCmb.getValue();
        String Inventory = inventoryIdCmb.getValue();

        AddItemDTO addItemDTO = new AddItemDTO(RoomId, Inventory);
        boolean isAdd = AddItemModel.isAdd(addItemDTO);

        if (isAdd) {
            new Alert(Alert.AlertType.INFORMATION, "Added successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Not added successfully").show();
        }
    }
}

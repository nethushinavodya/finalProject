package lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
        getCurrentRoomId();
        addRoomId.setDisable(true);
    }

    private void getCurrentRoomId() throws SQLException {
        try {
            String currentRoomId = AddRoomModel.getCurrentRoomId();

            String nextRoomId = generateNextRoomId(currentRoomId);
            addRoomId.setText(nextRoomId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextRoomId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("R");
            int idNum = Integer.parseInt(split[1]);
            return "R" + ++idNum;
        }
        return "R1";
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

        if (isValid()) {
            try {
                boolean isDelete = AddRoomModel.delete(Integer.parseInt(roomId));
                if (isDelete) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted successfully").show();
                    setAll();
                    clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee not deleted successfully").show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid room number").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Input Valid Data").show();
        }
    }

    @FXML
    void handleSave(ActionEvent event) throws SQLException {
        String roomId = addRoomId.getText();
        String roomNumber = addRoomNumber.getText();
        String roomStatus = roomStCmb.getValue();
        String roomTypeId = addRoomType.getValue();

        if(isValid()) {
            try {
                AddRoomDTO addRoomDTO = new AddRoomDTO(roomId, roomNumber, roomStatus, roomTypeId);
                boolean isSave = AddRoomModel.save(addRoomDTO);

                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Room added successfully").show();
                    setAll();
                    clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Room not added successfully").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Input Valid Data").show();
        }

    }

    @FXML
    void handleUpdate(ActionEvent event) throws SQLException {
        String roomId = addRoomId.getText();
        String roomNumber = addRoomNumber.getText();
        String roomStatus = roomStCmb.getValue();
        String roomTypeId = addRoomType.getValue();

        if(isValid()) {
            try {
                AddRoomDTO addRoomDTO = new AddRoomDTO(roomId, roomNumber, roomStatus, roomTypeId);
                boolean isUpdate = AddRoomModel.update(addRoomDTO);

                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Room added successfully").show();
                    setAll();
                    clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Room not added successfully").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Input Valid Data").show();
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
            new Alert(Alert.AlertType.CONFIRMATION, "Added successfully").show();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR, "Not added successfully").show();
        }
    }

    public void rNumberOnKeyReleased(KeyEvent keyEvent) {
        if(addRoomNumber.getText().matches("\\d{2,}")){
            addRoomNumber.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-height: 2px;");
        }else {
            addRoomNumber.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-height: 2px;");
        }
    }
    public boolean isValid(){
        if (addRoomNumber.getText().matches("\\d{2,}")){
            return true;
        }else {
            return false;
        }
    }
    public void clear(){
        addRoomNumber.clear();
        addRoomType.setValue(null);
        roomIdCmb.setValue(null);
        roomStCmb.setValue(null);
        inventoryIdCmb.setValue(null);

    }
}

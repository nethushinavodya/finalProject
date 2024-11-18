package lk.gdse.hotelmanagement.crystalwave.controller.ReceptionistControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.gdse.hotelmanagement.crystalwave.dto.AddRoomDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.AddRoomTM;
import lk.gdse.hotelmanagement.crystalwave.model.AddRoomModel;

import java.sql.SQLException;
import java.util.List;

public class RoomController {
    public TableView <AddRoomTM>roomTable;
    public TableColumn <?,?>roomIdColumn;
    public TableColumn  <?,?>roomStatusColumn;
    public TableColumn <?,?> roomNumber;
    public TableColumn  <?,?>roomTypeId;

    public void initialize() throws SQLException {
        setCell();
        setAll();
    }

    private void setCell() {
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));
        roomTypeId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
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
        roomTable.setItems(addRoom);
    }
}

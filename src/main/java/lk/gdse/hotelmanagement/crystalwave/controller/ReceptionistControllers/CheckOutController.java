package lk.gdse.hotelmanagement.crystalwave.controller.ReceptionistControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.gdse.hotelmanagement.crystalwave.dto.AddRoomDTO;
import lk.gdse.hotelmanagement.crystalwave.model.AddRoomModel;

import java.sql.SQLException;
import java.util.List;

public class CheckOutController {

    public ComboBox roomIdCmb;
    @FXML
    private Button checkoutButton;
    @FXML
    private Button generateBillButton;
    @FXML

    public void initialize() throws SQLException {
        selectRoomIdCmb();
    }

    private void selectRoomIdCmb() throws SQLException {
        ObservableList<String> room = FXCollections.observableArrayList();
        List<String > roomId = AddRoomModel.getDeactiveRooms();

        room.addAll(roomId);

        roomIdCmb.setItems(room);
    }

    @FXML
    void handleCheckout(ActionEvent event) throws SQLException {
        String roomId = (String) roomIdCmb.getValue();

        boolean isCheckout = AddRoomModel.checkOut(roomId);
        if (isCheckout) {
            new Alert(Alert.AlertType.INFORMATION, "Checkout Successful").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Checkout Failed").show();
        }

    }

    @FXML
    void handleGenerateBill(ActionEvent event) {

    }

    public void roomIDCmb(ActionEvent actionEvent) {
    }
}

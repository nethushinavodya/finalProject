package lk.gdse.hotelmanagement.crystalwave.controller.ReceptionistControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.CheckInDetailTM;
import lk.gdse.hotelmanagement.crystalwave.model.QueryModel;

import java.sql.SQLException;
import java.util.List;

public class CheckInDetailController {

    @FXML
    private TableColumn<?, ?> GuestId;

    @FXML
    private TableColumn<?, ?> amountColumn;

    @FXML
    private TableColumn<?, ?> checkIn;

    @FXML
    private TableColumn<?, ?> checkOut;

    @FXML
    private TableView<CheckInDetailTM> guestTable;

    @FXML
    private TableColumn<?, ?> paymentStatusColumn;

    @FXML
    private TableColumn<?, ?> reservationId;

    @FXML
    private TableColumn<?, ?> roomId;
    
    
    
    public void initialize() throws SQLException {
        setCellValue();
        setCtable();
    }

    private void setCtable() throws SQLException {
        ObservableList<CheckInDetailTM> data = FXCollections.observableArrayList();

        List<CheckInDetailTM> list = QueryModel.getAll();
        for (CheckInDetailTM checkInDetailTM : list) {
            data.add(checkInDetailTM);
        }
        guestTable.setItems(data);
    }

    private void setCellValue() {
        GuestId.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        roomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        reservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        checkIn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOut.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

}

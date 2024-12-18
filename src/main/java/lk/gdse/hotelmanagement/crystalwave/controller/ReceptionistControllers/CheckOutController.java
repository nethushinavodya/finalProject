package lk.gdse.hotelmanagement.crystalwave.controller.ReceptionistControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.AddRoomDTO;
import lk.gdse.hotelmanagement.crystalwave.model.AddRoomModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOutController {

    public ComboBox roomIdCmb;
    public TextField reservation_id;
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
    void handleGenerateBill(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/nethushi.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("rid", reservation_id.getText());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);

    }

    public void roomIDCmb(ActionEvent actionEvent) {
    }
}

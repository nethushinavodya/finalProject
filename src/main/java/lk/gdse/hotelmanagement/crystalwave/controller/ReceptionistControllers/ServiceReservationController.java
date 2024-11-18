package lk.gdse.hotelmanagement.crystalwave.controller.ReceptionistControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.gdse.hotelmanagement.crystalwave.dto.AddBilDto;
import lk.gdse.hotelmanagement.crystalwave.dto.AddServiceDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.ServiceCartTM;
import lk.gdse.hotelmanagement.crystalwave.model.AddBillModel;
import lk.gdse.hotelmanagement.crystalwave.model.AddServiceModel;
import lk.gdse.hotelmanagement.crystalwave.model.ReservationModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservationController {
    public ComboBox<String> reservationIdCmb;
    public ComboBox <String>serviceIdCmb;
    public TableView <ServiceCartTM>tableView;
    public TableColumn  <?,?> remove;
    public TableColumn <?,?> reservationId;
    public TableColumn<?,?>  serviceId;
    public Label Bill_lbl;
    public TableColumn  <?,?> servicePricecol;

    Double total=0.0;

    ObservableList<ServiceCartTM> cartTMS = FXCollections.observableArrayList();


    public void initialize() throws SQLException {
        getbillId();
        setCellValue();
        setRCmb();
        setScmb();
    }


    private void getbillId() {
        try {
            String currentId = AddBillModel.getCurrentId();

            String billId = generateNextReservationId(currentId);
            Bill_lbl.setText(billId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextReservationId(String currentId) {
        try {
            if (currentId != null) {
                int id = Integer.parseInt(currentId);
                id++;

                return String.valueOf(id);
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return "1";
    }

    private void setCellValue() {
        reservationId.setCellValueFactory(new PropertyValueFactory<>("rid"));
        serviceId.setCellValueFactory(new PropertyValueFactory<>("sid"));
        servicePricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
        remove.setCellValueFactory(new PropertyValueFactory<>("cancelBtn"));
        tableView.setItems(cartTMS);

    }

    private void setScmb() throws SQLException {
        ObservableList<String> items = FXCollections.observableArrayList();

        List<AddServiceDTO>list = AddServiceModel.getAll();

        for (AddServiceDTO addServiceDTO : list) {
            items.add(addServiceDTO.getServiceId());

        }
        serviceIdCmb.setItems(items);
    }

    private void setRCmb() throws SQLException {
        ObservableList<String> rlist = ReservationModel.getAll();
        reservationIdCmb.setItems(rlist);

    }

    public void addToCart(ActionEvent actionEvent) throws SQLException {
        String reservationId = reservationIdCmb.getSelectionModel().getSelectedItem();
        String serviceId = serviceIdCmb.getSelectionModel().getSelectedItem();


        for(ServiceCartTM cartTM : cartTMS) {
            if (cartTM.getSid().equals(serviceId)) {
                new Alert(Alert.AlertType.INFORMATION, "service already exists").show();
                return;
            }
        }

        Button button =new Button("remove");

        String price = AddServiceModel.getprice(serviceId);
        double pp = Double.parseDouble(price);

        ServiceCartTM serviceCartTM = new ServiceCartTM(reservationId, serviceId, pp, button);
        tableView.refresh();
        button.setOnAction(event -> {

            cartTMS.remove(serviceCartTM);

            tableView.refresh();
        });

        cartTMS.add(serviceCartTM);

    }

    public void tableView(MouseEvent mouseEvent) {
    }

    public void addServicesOnAction(ActionEvent actionEvent) throws SQLException {
        String billId  = Bill_lbl.getText();
        String rid = reservationIdCmb.getSelectionModel().getSelectedItem();

        List<String> sidList =new ArrayList<>();

        for (ServiceCartTM serviceCartTM : cartTMS) {
            String id = serviceCartTM.getSid();
            sidList.add(id);
            double price = serviceCartTM.getPrice();
            System.out.println(price);
            total += price;
        }
        String date = LocalDate.now().toString();

        AddBilDto addBilDto = new AddBilDto(billId, rid, total, date, sidList);

        boolean isSaved = AddBillModel.save(addBilDto);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Service added").show();
            total =0.0;
            getbillId();

        }

    }
}

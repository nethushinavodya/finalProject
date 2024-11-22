package lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.AddRoomDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.AddServiceDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.AddServiceEmployeeDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.EmployeeDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.AddRoomTM;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.AddServiceTM;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.EmployeeTm;
import lk.gdse.hotelmanagement.crystalwave.model.AddRoomModel;
import lk.gdse.hotelmanagement.crystalwave.model.AddServiceEmployeeModel;
import lk.gdse.hotelmanagement.crystalwave.model.AddServiceModel;
import lk.gdse.hotelmanagement.crystalwave.model.EmployeeManagementModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AddServiceController {
    public TableColumn <?,?>sPrice;
    public TableColumn<?,?>  sId;
    public TableColumn <?,?>sName;
    public TableColumn <?,?>desc;
    public TableView <AddServiceTM>tableView;
    public TextField serviceId;
    public TextField ServiceName;
    public TextField servicePrice;
    public TextField serviceDesc;
    public ComboBox <String> empIdCmb;
    public ComboBox<String> empIdCmb1;
    public ComboBox<String> srviceIdCmb;

    public void initialize() throws SQLException {
        setEidCmb();
        setCell();
        setAll();
        setempIdCmb1();
        setsrviceIdCmb();
        getCurrentSId();
        serviceId.setDisable(true);
    }

    private void getCurrentSId() {
        try {
            String currentServiceId = AddServiceModel.getCurrentSId();

            String nextServiceId = generateNextServiceId(currentServiceId);
            serviceId.setText(nextServiceId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextServiceId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("S");
            int idNum = Integer.parseInt(split[1]);
            return "S" + ++idNum;
        }
        return "S1";
    }

    private void setsrviceIdCmb() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try{
            List<AddServiceDTO> idList = AddServiceModel.getAll();
            for (AddServiceDTO addServiceTM : idList) {
                obList.add(addServiceTM.getServiceId());
            }
            srviceIdCmb.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setempIdCmb1() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try{
            List<EmployeeDTO> idList = EmployeeManagementModel.getAll();
            for (EmployeeDTO addEmployeeTM : idList) {
                obList.add(addEmployeeTM.getEmployeeId());
            }
            empIdCmb1.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCell() {
        sId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        sName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        desc.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
        sPrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));

    }

    private void setAll() throws SQLException {
        ObservableList<AddServiceTM> addService = FXCollections.observableArrayList();

        List<AddServiceDTO> list = AddServiceModel.getAll();

        for (AddServiceDTO addServiceDTO : list) {
            String serviceId = addServiceDTO.getServiceId();
            String serviceName = addServiceDTO.getServiceName();
            String serviceDescription = addServiceDTO.getServiceDescription();
            String servicePrice = addServiceDTO.getServicePrice();

            System.out.println(serviceId+serviceName+serviceDescription+servicePrice);

            AddServiceTM addServiceDTO1 = new AddServiceTM(serviceId, serviceName, serviceDescription, servicePrice);

            addService.add(addServiceDTO1);

        }
        tableView.setItems(addService);
    }

    private void setEidCmb() throws SQLException {
        ObservableList<String> oblist = FXCollections.observableArrayList();
        List<EmployeeDTO> idlist = EmployeeManagementModel.getAll();

        for (EmployeeDTO employeeDTO : idlist) {
            oblist.add(String.valueOf(employeeDTO.getEmployeeId()));
        }
        empIdCmb.setItems(oblist);
    }

    public void handleDelete(ActionEvent actionEvent) throws SQLException {
        String ServiceId = serviceId.getText();

            boolean isDelete = AddServiceModel.delete(ServiceId);

            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted successfully").show();
                setAll();
                clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee not deleted successfully").show();
            }
    }

    public void handleSave(ActionEvent actionEvent) throws SQLException {
        String sId =serviceId.getText();
        String sName = ServiceName.getText();
        String desc = serviceDesc.getText();
        String sPrice = servicePrice.getText();
        String empId = empIdCmb.getValue();

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        if(isValid()) {
            try {
                AddServiceDTO addServiceDTO = new AddServiceDTO(sId, sName, desc, sPrice);
                boolean isSave = AddServiceModel.save(addServiceDTO);

                if (isSave) {
                    AddServiceEmployeeDTO addServiceEmployeeDTO = new AddServiceEmployeeDTO(empId, sId);
                    boolean isSave2 = AddServiceEmployeeModel.save(addServiceEmployeeDTO);

                    if (isSave2) {
                        connection.commit();
                        setAll();
                        getCurrentSId();
                        clear();
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Input Valid Data").show();
        }
    }

    public void handleUpdate(ActionEvent actionEvent) throws SQLException {
        String sId = (serviceId.getText());
        String sName = ServiceName.getText();
        String desc = serviceDesc.getText();
        String sPrice = servicePrice.getText();
        String empId = (empIdCmb.getValue());

        if(isValid()) {
            AddServiceDTO addServiceDTO = new AddServiceDTO(sId, sName, desc, sPrice);
            boolean isUpdate = AddServiceModel.update(addServiceDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Service updated successfully").show();
                setAll();
                clear();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Service not updated successfully").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Input Valid Data").show();
        }
    }

    public void tableView(MouseEvent mouseEvent) {
        TableView<AddServiceTM>tableView = (TableView<AddServiceTM>) mouseEvent.getSource();
        AddServiceTM addServiceTM =tableView.getSelectionModel().getSelectedItem();
        serviceId.setText(String.valueOf(addServiceTM.getServiceId()));
        ServiceName.setText(addServiceTM.getServiceName());
        serviceDesc.setText(addServiceTM.getServiceDescription());
        servicePrice.setText(addServiceTM.getServicePrice());
    }

    public void handleSave1(ActionEvent actionEvent) throws SQLException {
        String  employeeId = empIdCmb1.getValue();
        String serviceId = srviceIdCmb.getValue();

        AddServiceEmployeeDTO addServiceEmployeeDTO = new AddServiceEmployeeDTO(employeeId,serviceId);
        boolean isAdd = AddServiceEmployeeModel.isAdd(addServiceEmployeeDTO);

        if (isAdd) {
            new Alert(Alert.AlertType.INFORMATION, "Employee added successfully").show();
            setAll();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR, "Employee not added successfully").show();
        }
    }
    public void clear(){
        ServiceName.clear();
        serviceDesc.clear();
        servicePrice.clear();
        empIdCmb.setValue(null);
    }

    public void sNameOnKeyRelease(KeyEvent keyEvent) {
        if(ServiceName.getText().matches("[a-zA-Z ]+")){
            ServiceName.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-height: 5px;");
        }else {
            ServiceName.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-height: 5px;");
        }
    }

    public void descOnKeyRelease(KeyEvent keyEvent) {
        if(serviceDesc.getText().matches("[a-zA-Z ]+")){
            serviceDesc.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-height: 5px;");
        }else {
            serviceDesc.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-height: 5px;");
        }
    }

    public void priceOnKeyRelease(KeyEvent keyEvent) {
        if(servicePrice.getText().matches("\\d{1,}")){
            servicePrice.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-height: 5px;");
        }else{
            servicePrice.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-height: 5px;");
        }
    }
    public boolean isValid() {
        if(ServiceName.getText().matches("[a-zA-Z ]+")&&

                    servicePrice.getText().matches("\\d{4,}")
        ){
            return true;
        }else {
            System.out.println("ffff");
            return false;
        }
    }
}

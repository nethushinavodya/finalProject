package lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
            new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully").show();
            setAll();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR, "Employee not deleted successfully").show();
        }
    }

    public void handleSave(ActionEvent actionEvent) throws SQLException {
        String sId = String.valueOf(Integer.parseInt(serviceId.getText()));
        String sName = ServiceName.getText();
        String desc = serviceDesc.getText();
        String sPrice = servicePrice.getText();
        String empId = String.valueOf(Integer.parseInt(empIdCmb.getValue()));

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try{
            AddServiceDTO addServiceDTO = new AddServiceDTO(sId,sName,desc,sPrice);
            boolean isSave = AddServiceModel.save(addServiceDTO);

            if(isSave){
                AddServiceEmployeeDTO addServiceEmployeeDTO = new AddServiceEmployeeDTO(empId,sId);
                boolean isSave2 = AddServiceEmployeeModel.save(addServiceEmployeeDTO);

                if(isSave2){
                    connection.commit();
                    setAll();
                    clear();
                }else {
                    connection.rollback();
                }
               }else {
                connection.rollback();
            }
        }catch (Exception e){
        e.printStackTrace();
        }finally{
            connection.setAutoCommit(true);
        }

    }

    public void handleUpdate(ActionEvent actionEvent) throws SQLException {
        String sId = String.valueOf(Integer.parseInt(serviceId.getText()));
        String sName = ServiceName.getText();
        String desc = serviceDesc.getText();
        String sPrice = servicePrice.getText();
        String empId = String.valueOf(Integer.parseInt(empIdCmb.getValue()));

        AddServiceDTO addServiceDTO = new AddServiceDTO(sId,sName,desc,sPrice);
        boolean isUpdate = AddServiceModel.update(addServiceDTO);

        if(isUpdate){
            new Alert(Alert.AlertType.INFORMATION, "Service updated successfully").show();
            setAll();
            clear();
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Service not updated successfully").show();
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
        serviceId.clear();
        ServiceName.clear();
        serviceDesc.clear();
        servicePrice.clear();
    }
}
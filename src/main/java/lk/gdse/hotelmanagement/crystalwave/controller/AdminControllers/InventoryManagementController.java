package lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.gdse.hotelmanagement.crystalwave.dto.InventoryDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.InventoryTm;
import lk.gdse.hotelmanagement.crystalwave.model.InventoryManagementModel;

import java.sql.SQLException;
import java.util.List;

public class InventoryManagementController {

    public TableColumn itemIdColumn;
    public TableColumn itemNameColumn;
    public TableColumn quantityColumn;
    public TableColumn roomTypeIdColumn;
    public TableColumn priceColumn;
    public TextField itemIdField;
    public TextField quantityField;
    public TextField priceField;
    @FXML
    private TextField itemNameField;
    @FXML
    private TableView<InventoryTm> inventoryTable;

    @FXML
    private void initialize() throws SQLException {
        setCellvalue();
        setAll();
    }
    private void setCellvalue() {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        roomTypeIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
    }

    private void setAll() throws SQLException {
        ObservableList<InventoryTm> inventoryTypeTms = FXCollections.observableArrayList();

        List<InventoryTm> list = InventoryManagementModel.getAll();

        for (InventoryTm InventoryTm : list) {
            inventoryTypeTms.add(InventoryTm);
        }
        inventoryTable.setItems(inventoryTypeTms);

    }
    public void handleDeleteItem(ActionEvent actionEvent) throws SQLException {

        try {
            int itemId = Integer.parseInt(itemIdField.getText());
            boolean isDeleted = InventoryManagementModel.delete(itemId);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item deleted successfully.").show();
                setAll();
                clear();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Item not deleted successfully.").show();
            }
        }catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

        
    }

    public void handleUpdateItem(ActionEvent actionEvent) throws SQLException {
        int itemId = Integer.parseInt(itemIdField.getText());
        String itemName = itemNameField.getText();
        int itemQuantity = Integer.parseInt(quantityField.getText());
        double itemPrice = Double.parseDouble(priceField.getText());

        try{
            InventoryDTO inventoryDTO = new InventoryDTO(itemId,itemName,itemQuantity,itemPrice);
            boolean isUpdate = InventoryManagementModel.update(inventoryDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Item updated successfully").show();
                setAll();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR, "Item not updated successfully").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }

    public void handleAddItem(ActionEvent actionEvent) throws SQLException {
        int itemId = Integer.parseInt(itemIdField.getText());
        String itemName = itemNameField.getText();
        int itemQuantity = Integer.parseInt(quantityField.getText());
        double itemPrice = Double.parseDouble(priceField.getText());



        try {
            InventoryDTO inventoryDTO = new InventoryDTO(itemId,itemName,itemQuantity,itemPrice);
            boolean isSave = InventoryManagementModel.save(inventoryDTO);

            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Item added successfully").show();
                setAll();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR, "Item not added successfully").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    public void inventoryTable (MouseEvent mouseEvent) {
        TableView <InventoryTm> tableView = (TableView <InventoryTm>) inventoryTable;
        InventoryTm inventoryTm = tableView.getSelectionModel().getSelectedItem();
        itemIdField.setText(String.valueOf(inventoryTm.getItemId()));
        itemNameField.setText(inventoryTm.getItemName());
        quantityField.setText(String.valueOf(inventoryTm.getItemQuantity()));
        priceField.setText(String.valueOf(inventoryTm.getItemPrice()));

    }
    public void clear(){
        itemIdField.clear();
        itemNameField.clear();
        quantityField.clear();
        priceField.clear();

    }

    public void handleClear(ActionEvent actionEvent) {
        clear();
    }
}

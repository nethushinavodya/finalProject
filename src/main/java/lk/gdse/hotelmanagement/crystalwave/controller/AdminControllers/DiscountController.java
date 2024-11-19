package lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.gdse.hotelmanagement.crystalwave.dto.AddRoomDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.DiscountDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.DiscountTM;
import lk.gdse.hotelmanagement.crystalwave.model.DiscountModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DiscountController {

    @FXML
    private TableColumn<?, ?> condition;

    @FXML
    private TextField disCon;

    @FXML
    private TextField disId;

    @FXML
    private TextField disType;

    @FXML
    private TableColumn<?, ?> discountId;

    @FXML
    private TableColumn<?, ?> discountType;

    @FXML
    private TableColumn<?, ?> eDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TableColumn<?, ?> sDate;

    @FXML
    private DatePicker startDate;

    @FXML
    private TableView<DiscountTM> tableView;

    public void initialize() throws SQLException {
        setCellValue();
        setAll();
    }

    private void setCellValue() {
        discountId.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        discountType.setCellValueFactory(new PropertyValueFactory<>("discountType"));
        condition.setCellValueFactory(new PropertyValueFactory<>("discountCondition"));
        sDate.setCellValueFactory(new PropertyValueFactory<>("discountStartDate"));
        eDate.setCellValueFactory(new PropertyValueFactory<>("discountEndDate"));
    }
    private void setAll() throws SQLException {
        ObservableList<DiscountTM> discount = FXCollections.observableArrayList();

        List<DiscountTM> list = DiscountModel.getAll();

        discount.addAll(list);
        tableView.setItems(discount);
    }
    @FXML
    void handleDelete(ActionEvent event) throws SQLException {
        String id = disId.getText();

        boolean isDeleted = DiscountModel.delete(id);

        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Discount deleted successfully.").show();
            setAll();
            clear();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION, "Discount not deleted successfully.").show();
        }
    }

    @FXML
    void handleSave(ActionEvent event) throws SQLException {
        String id = disId.getText();
        String type = disType.getText();
        int con = Integer.parseInt(disCon.getText());
        String start = String.valueOf(startDate.getValue());
        String end = String.valueOf(endDate.getValue());

        DiscountDTO discountDTO = new DiscountDTO(id, type, con, start, end);
        boolean isSave = DiscountModel.save(discountDTO);

        if (isSave) {
            new Alert(Alert.AlertType.INFORMATION, "Discount saved successfully").show();
            setAll();
            clear();
        }else{
            new Alert(Alert.AlertType.ERROR, "Discount not saved successfully").show();
        }
    }

    @FXML
    void tableView(MouseEvent event) {
        TableView <DiscountTM> tableView1 = (TableView <DiscountTM>) event.getSource();
        DiscountTM discountTM = tableView1.getSelectionModel().getSelectedItem();
        disId.setText(discountTM.getDiscountId());
        disType.setText(discountTM.getDiscountType());
        disCon.setText(String.valueOf(discountTM.getDiscountCondition()));
        startDate.setValue(LocalDate.parse(discountTM.getDiscountStartDate()));
        endDate.setValue(LocalDate.parse(discountTM.getDiscountEndDate()));
    }
    public void clear(){
        disId.clear();
        disType.clear();
        disCon.clear();
        startDate.setValue(null);
        endDate.setValue(null);

    }
}

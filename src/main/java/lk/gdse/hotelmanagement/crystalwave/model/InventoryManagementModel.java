package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.InventoryDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.InventoryTm;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.RoomTypeTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class InventoryManagementModel {
    public static boolean update(InventoryDTO inventoryDTO) throws SQLException {
        String sql = "UPDATE Inventory set ItemName = ?,Quantity = ?, Price= ? where Inventory_Id = ? ";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, String.valueOf(inventoryDTO.getItemName()));
        preparedStatement.setString(2, String.valueOf(inventoryDTO.getItemQuantity()));
        preparedStatement.setString(3, String.valueOf(inventoryDTO.getItemPrice()));
        preparedStatement.setString(4, String.valueOf(inventoryDTO.getItemId()));

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean save(InventoryDTO inventoryDTO) throws SQLException {
        String sql = "INSERT INTO Inventory VALUES (?,?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, String.valueOf(inventoryDTO.getItemId()));
        preparedStatement.setString(2, String.valueOf(inventoryDTO.getItemName()));
        preparedStatement.setString(3, String.valueOf(inventoryDTO.getItemQuantity()));
        preparedStatement.setString(4, String.valueOf(inventoryDTO.getItemPrice()));

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean delete(String itemId) throws SQLException {
        String sql = "DELETE FROM Inventory WHERE Inventory_Id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, itemId);
        return preparedStatement.executeUpdate() > 0;
    }

    public static List<InventoryTm> getAll() throws SQLException {
        List<InventoryTm> list = new ArrayList<>();

        String sql = "select * from Inventory";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String itemId = resultSet.getString(1);
            String itemName = resultSet.getString(2);
            String itemQuantity = resultSet.getString(3);
            String itemPrice = resultSet.getString(4);
            InventoryTm inventoryDTO1 = new InventoryTm(itemId, itemName, itemQuantity, itemPrice);
            list.add(inventoryDTO1);

        }
        return list;

    }

    public static String getCurrentRoomId() throws SQLException {
        String sql = "SELECT CONCAT('C', MAX(CAST(SUBSTRING(Inventory_Id, 2) AS UNSIGNED))) AS max_i_id FROM Inventory";
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String itemid = resultSet.getString(1);
            return itemid;
        }
        return null;
    }

}

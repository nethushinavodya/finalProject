package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.AddItemDTO;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddItemModel {

    public static boolean isAdd(AddItemDTO addItemDTO) throws SQLException {
        /*String sql = "INSERT INTO Room_Inventory VALUES (?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, Integer.parseInt(addItemDTO.getRoomId()));
        preparedStatement.setInt(2, Integer.parseInt(addItemDTO.getInventoryId()));

        return preparedStatement.executeUpdate() > 0;*/

        return CrudUtil.execute("INSERT INTO Room_Inventory VALUES (?,?)",
                addItemDTO.getRoomId(),
                addItemDTO.getInventoryId()
                );
    }
}

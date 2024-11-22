package lk.gdse.hotelmanagement.crystalwave.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.RoomTypeDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.RoomTypeTm;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeManagementModel {
    public static boolean save(RoomTypeDTO roomTypeDTO) throws SQLException {
        String sql = "INSERT INTO Room_Type VALUES (?,?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, roomTypeDTO.getRoomTypeId());
        preparedStatement.setString(2,roomTypeDTO.getRoomTypeName());
        preparedStatement.setString(3,roomTypeDTO.getRoomTypeDescription());
        preparedStatement.setDouble(4,roomTypeDTO.getRoomTypePrice());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean delete(String roomNumber) throws SQLException {
        String sql = "delete from Room_Type where RoomType_Id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, roomNumber);
        return preparedStatement.executeUpdate() > 0;
    }

    public static List<RoomTypeTm> getAll() throws SQLException {
        List<RoomTypeTm> list = new ArrayList<>();

        String sql = "select * from Room_Type";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String roomId = resultSet.getString(1);
            String type = resultSet.getString(2);
            String description = resultSet.getString(3);
            String price = resultSet.getString(4);
            RoomTypeTm roomTypeDTO1 = new RoomTypeTm(roomId,type,description,price);
            list.add(roomTypeDTO1);

        }
        return list;

    }

    public static boolean update(RoomTypeDTO roomTypeDTO) throws SQLException {
        String sql = "Update Room_Type set RoomType = ?,Description = ?,Rate = ? where RoomType_Id = ? ";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,roomTypeDTO.getRoomTypeName());
        preparedStatement.setString(2, roomTypeDTO.getRoomTypeDescription());
        preparedStatement.setDouble(3,roomTypeDTO.getRoomTypePrice());
        preparedStatement.setString(4,roomTypeDTO.getRoomTypeId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static String getCurrentTypeId() throws SQLException {
        String sql = "SELECT CONCAT('T', MAX(CAST(SUBSTRING(RoomType_Id, 2) AS UNSIGNED))) AS max_p_id FROM Room_Type";
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String pid = resultSet.getString(1);
            return pid;
        }
        return null;
    }

    public static String getPrice(String type) throws SQLException {
        String query = "SELECT Rate FROM Room_Type WHERE RoomType_Id = ?";
        ResultSet resultSet = CrudUtil.execute(query, type);

        String roomTypePrice = null;
        if (resultSet.next()) {
            roomTypePrice = resultSet.getString(1);
        }
        return roomTypePrice;
    }

}

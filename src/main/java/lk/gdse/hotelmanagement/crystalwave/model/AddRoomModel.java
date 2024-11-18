package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.AddRoomDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.EmployeeDTO;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddRoomModel {
    public static boolean save(AddRoomDTO addRoomDTO) throws SQLException {
        String sql = "INSERT INTO Room VALUES (?,?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,addRoomDTO.getRoomId());
        preparedStatement.setString(2,addRoomDTO.getRoomNumber());
        preparedStatement.setString(3, addRoomDTO.getRoomStatus());
        preparedStatement.setString(4, addRoomDTO.getRoomTypeId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean update(AddRoomDTO addRoomDTO) throws SQLException {
        String sql = "UPDATE Room set RoomNumber=?, RoomStatus=? ,RoomType_Id=? where Room_Id=?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,addRoomDTO.getRoomNumber());
        preparedStatement.setString(2,addRoomDTO.getRoomStatus());
        preparedStatement.setString(3,addRoomDTO.getRoomTypeId());
        preparedStatement.setString(4, addRoomDTO.getRoomId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static List<AddRoomDTO> getAll() throws SQLException {
        List<AddRoomDTO> list = new ArrayList<>();

        String sql = "select * from Room";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String roomId = resultSet.getString(1);
            String roomNumber = resultSet.getString(2);
            String roomStatus = resultSet.getString(3);
            String roomTypeId = resultSet.getString(4);
            AddRoomDTO addRoomDTO = new AddRoomDTO(roomId, roomNumber, roomStatus, roomTypeId);
            list.add(addRoomDTO);

        }
        return list;

    }

    public static boolean delete(int roomId) throws SQLException {
        String sql = "DELETE FROM Room WHERE Room_Id=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,roomId);
        return preparedStatement.executeUpdate() > 0;
    }

    public static List<String> getActiveRoom() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Room_Id FROM Room WHERE RoomStatus = 'Available'");
        List<String> list = new ArrayList<>();
        while (rst.next()){
            String roomId = rst.getString(1);
            list.add(roomId);
        }
        return list;
    }

    public static List<String> getDeactiveRooms() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT Room_Id FROM Room WHERE RoomStatus = 'Occupied'");
        List<String> list = new ArrayList<>();
        while (resultSet.next()){
            String roomId = resultSet.getString(1);
            list.add(roomId);
        }
        return list;
    }

    public static boolean checkOut(String roomId) throws SQLException {
        return CrudUtil.execute("UPDATE Room SET RoomStatus = 'Available' WHERE Room_Id =? ",roomId);

    }
}

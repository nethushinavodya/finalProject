package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.RoomDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.RoomTypeDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.RoomTypeTm;

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

    /*
        public List<RoomDTO> getAllRooms() {
            List<RoomDTO> rooms = new ArrayList<>();
            rooms.add(new RoomDTO(101, "Single", 100.00, "Available"));
            rooms.add(new RoomDTO(102, "Double", 150.00, "Booked"));
            return rooms;
        }*/
    public void bookRoom(RoomDTO room) {
        room.setRoomStatus("Booked");
        System.out.println("Room " + room.getRoomNumber() + " booked successfully.");
    }
    public void cancelRoom(RoomDTO room) {
        room.setRoomStatus("Available");
        System.out.println("Room " + room.getRoomNumber() + " booking canceled.");
    }
}
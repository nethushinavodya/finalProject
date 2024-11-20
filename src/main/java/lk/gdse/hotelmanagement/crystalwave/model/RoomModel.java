package lk.gdse.hotelmanagement.crystalwave.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

public class RoomModel {
    public static boolean markRoomAsAvailable(String roomNumber) throws SQLException {
        String query = "UPDATE Rooms SET status = 'available' WHERE roomNumber = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, roomNumber);
            return statement.executeUpdate() > 0; // Returns true if update was successful
        }
    }

    public static boolean isRoomAvailable(String roomId) throws SQLException {
        String query = "SELECT status FROM Rooms WHERE roomNumber = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, roomId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String status = resultSet.getString("status");
                return "available".equalsIgnoreCase(status);
            }
            return false;         }
    }

    public static boolean updateAvailable(String roomId) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Room set RoomStatus = 'Occupied' where Room_Id = ?",
                roomId
        );
    }
}

package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.GuestDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class GuestModel {

    private static DBConnection dbConnection;

    static {
        try {
            dbConnection = DBConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean saveGuest(GuestDTO guest) throws SQLException {
        String query = "INSERT INTO Guests (name, phone, email, address, city, identityNo, days, amount, paymentStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, guest.getName());
            statement.setString(2, guest.getPhone());
            statement.setString(3, guest.getEmail());
            statement.setString(4, guest.getAddress());
            statement.setString(5, guest.getCity());
            statement.setString(6, guest.getIdentityNo());
            statement.setInt(7, guest.getDays());
            statement.setDouble(8, guest.getAmount());
            statement.setString(9, guest.getPaymentStatus());

            return statement.executeUpdate() > 0; // Returns true if a row was inserted
        }
    }

    public static boolean saveGuestInfo(String name, String phone, String email, String address, String city, String passport,
                                        String roomId, String roomType, String roomCapacity, String paymentStatus,
                                        String paymentMethod, LocalDate checkInDate, LocalDate checkOutDate) throws SQLException {
        String query = "INSERT INTO Guests (name, phone, email, address, city, identityNo, roomId, roomType, roomCapacity, paymentStatus, paymentMethod, checkInDate, checkOutDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setString(4, address);
            statement.setString(5, city);
            statement.setString(6, passport);
            statement.setString(7, roomId);
            statement.setString(8, roomType);
            statement.setString(9, roomCapacity);
            statement.setString(10, paymentStatus);
            statement.setString(11, paymentMethod);
            statement.setObject(12, checkInDate);
            statement.setObject(13, checkOutDate);
            return statement.executeUpdate() > 0;
        }
    }
}

package lk.gdse.hotelmanagement.crystalwave.Database;

import lk.gdse.hotelmanagement.crystalwave.model.UserModel;
import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    public static boolean saveUser(UserModel userModel) throws SQLException {
        String query = "INSERT INTO users (firstName, lastName, phoneNumber, address, email, password, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userModel.getFirstName());
            pstmt.setString(2, userModel.getLastName());
            pstmt.setString(3, userModel.getPhoneNumber());
            pstmt.setString(4, userModel.getAddress());
            pstmt.setString(5, userModel.getEmail());
            pstmt.setString(6, userModel.getPassword());
            pstmt.setString(7, userModel.getRole());

            return pstmt.executeUpdate() > 0;
        }
    }

    public static UserModel getUserByEmailAndRole(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new UserModel(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNumber"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        }
        return null;
    }
}

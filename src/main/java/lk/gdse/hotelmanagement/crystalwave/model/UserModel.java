package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel{
public static UserDTO getUserByEmail(String email) throws SQLException {


        String query = "SELECT * FROM users WHERE email = ?";
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    String address = resultSet.getString("address");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");

                    return new UserDTO(firstName, lastName, phoneNumber, address, email, password, role);
                }


        return null;
    }

    public static boolean updateUserPassword(UserDTO userModel, String newPassword) throws SQLException {
        if (newPassword == null || newPassword.isEmpty()) {
            return false;
        }

        String sql = "UPDATE users SET password = ? WHERE email = ?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newPassword);  // Ensure password is hashed
            preparedStatement.setString(2, userModel.getEmail());

            int result = preparedStatement.executeUpdate();
            return result > 0; // Return true if update was successful

    }

    public static UserDTO getUserByEmailAndRole(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";

        Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new UserDTO(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNumber"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }

        return null;
    }
    public static boolean saveUser(UserDTO userModel) throws SQLException {


        String query = "INSERT INTO users (firstName, lastName, phoneNumber, address, email, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, userModel.getFirstName());
            statement.setString(2, userModel.getLastName());
            statement.setString(3, userModel.getPhoneNumber());
            statement.setString(4, userModel.getAddress());
            statement.setString(5, userModel.getEmail());
            statement.setString(6, userModel.getPassword());
            statement.setString(7, userModel.getRole());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

    }
}

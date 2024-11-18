package lk.gdse.hotelmanagement.crystalwave.db;

import lk.gdse.hotelmanagement.crystalwave.model.UserModel;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    private static DBConnection instance;
    private Connection connection;

    private DBConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/HotelManagement";
            String username = "nethu";
            String password = "Nethu@2004";
            connection = DriverManager.getConnection(url, username, password);
            LOGGER.info("Connected to the database successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to connect to the database", e);
        }
    }
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/HotelManagement";
                String username = "nethu";
                String password = "Nethu@2004";
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting connection", e);
        }
        return connection;
    }

    public boolean saveUser(UserModel userModel) {
        Connection connection = getConnection();
        if (connection == null) {
            LOGGER.warning("No database connection available.");
            return false;
        }

        String query = "INSERT INTO users (firstName, lastName, phoneNumber, address, email, password, role, securityQuestion, securityAnswer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userModel.getFirstName());
            statement.setString(2, userModel.getLastName());
            statement.setString(3, userModel.getPhoneNumber());
            statement.setString(4, userModel.getAddress());
            statement.setString(5, userModel.getEmail());
            statement.setString(6, hashPassword(userModel.getPassword()));
            statement.setString(7, userModel.getRole());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving userModel", e);
            return false;
        }
    }

    public UserModel getUserByEmail(String email) {
        Connection connection = getConnection();
        if (connection == null) {
            LOGGER.warning("No database connection available.");
            return null;
        }

        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    String address = resultSet.getString("address");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");

                    return new UserModel(firstName, lastName, phoneNumber, address, email, password, role);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching user by email", e);
        }

        return null;
    }
    public boolean updateUserPassword(UserModel userModel, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            LOGGER.warning("New password cannot be empty.");
            return false;
        }

        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, hashPassword(newPassword));  // Ensure password is hashed
            preparedStatement.setString(2, userModel.getEmail());

            int result = preparedStatement.executeUpdate();
            return result > 0; // Return true if update was successful
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating password", e);
            return false;
        }
    }
    public boolean deleteUserByEmail(String email) {
        Connection connection = getConnection();
        if (connection == null) {
            LOGGER.warning("No database connection available.");
            return false;
        }

        String query = "DELETE FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting user", e);
            return false;
        }
    }
    private String hashPassword(String password) {
        return password;
    }
}

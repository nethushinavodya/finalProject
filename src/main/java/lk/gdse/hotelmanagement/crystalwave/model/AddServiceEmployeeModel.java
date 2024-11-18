package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.AddServiceEmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddServiceEmployeeModel {
    public static boolean save(AddServiceEmployeeDTO dto) throws SQLException {
        String sql = "INSERT INTO Employee_Service VALUES (?,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, dto.getEmployeeId());
        preparedStatement.setString(2,dto.getServiceId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean isAdd(AddServiceEmployeeDTO addServiceEmployeeDTO) throws SQLException {
        String sql = "INSERT INTO Employee_Service VALUES (?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, Integer.parseInt(addServiceEmployeeDTO.getEmployeeId()));
        preparedStatement.setInt(2, Integer.parseInt(addServiceEmployeeDTO.getServiceId()));

        return preparedStatement.executeUpdate() > 0;
    }
}

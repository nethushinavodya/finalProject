package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.EmployeeDTO;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class EmployeeManagementModel {

    public static boolean save(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Employeement VALUES (?,?,?,?)",
                employeeDTO.getEmployeeId(),
                employeeDTO.getName(),
                employeeDTO.getRole(),
                employeeDTO.getContact()
        );
    }

    public static boolean update(EmployeeDTO employeeDTO) throws SQLException {
        String sql = "Update Employeement set Name = ?, Role = ? , ContactNo = ? where Employeement_Id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, employeeDTO.getName());
        preparedStatement.setString(2, employeeDTO.getRole());
        preparedStatement.setString(3, employeeDTO.getContact());
        preparedStatement.setString(4, employeeDTO.getEmployeeId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static EmployeeDTO search(String contact) throws SQLException {
        String sql = "select * from Employeement where ContactNo = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, contact);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString("1");
            String name = resultSet.getString("2");
            String role = resultSet.getString("3");
            String contactNo = resultSet.getString("4");
            EmployeeDTO employeeDTO1 = new EmployeeDTO(id, name, role, contactNo);
            return employeeDTO1;
        }
        return null;
    }

    public static boolean delete(String eId) throws SQLException {
        String sql = "delete from Employeement where Employeement_Id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, eId);
        return preparedStatement.executeUpdate() > 0;
    }

    public static List<EmployeeDTO> getAll() throws SQLException {
        List<EmployeeDTO> list = new ArrayList<>();

        String sql = "select * from Employeement";
        System.out.print("DFGHJ");
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.print("fghjk");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String role = resultSet.getString(3);
            String contactNo = resultSet.getString(4);
            System.out.print(id + "    ");
            EmployeeDTO employeeDTO1 = new EmployeeDTO(id, name, role, contactNo);
            list.add(employeeDTO1);

        }
        return list;

    }

    public static String getCurrentDiscountId() throws SQLException {
        String sql = "SELECT CONCAT('E', MAX(CAST(SUBSTRING(Employeement_Id, 2) AS UNSIGNED))) AS max_e_id FROM Employeement";
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String eid = resultSet.getString(1);
            return eid;
        }
        return null;
    }
}

package crystalwave.model;

import crystalwave.dto.ReportDTO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReportModel {

    private Connection connection;

    public ReportModel(Connection connection) {
        this.connection = connection;
    }
    public ReportDTO getBillingReport(String startDate, String endDate) {
        String query = "SELECT SUM(amount) AS totalAmount FROM billing WHERE date BETWEEN '" + startDate + "' AND '" + endDate + "'";
        double totalAmount = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                totalAmount = rs.getDouble("totalAmount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReportDTO("Billing Report", startDate + " to " + endDate, totalAmount, 0, 0);
    }
    public ReportDTO getEmployeeReport() {
        String query = "SELECT COUNT(*) AS totalEmployees FROM employees";
        int totalEmployees = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                totalEmployees = rs.getInt("totalEmployees");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReportDTO("Employee Report", "N/A", 0, 0, totalEmployees);
    }
    public ReportDTO getRoomStatusReport() {
        String query = "SELECT COUNT(*) AS totalRooms FROM rooms WHERE status = 'Occupied'";
        int totalRooms = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                totalRooms = rs.getInt("totalRooms");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReportDTO("Room Status Report", "N/A", 0, totalRooms, 0);
    }
}

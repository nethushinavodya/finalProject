package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentModel {
    public static String getCurrentId() throws SQLException {
        String sql = " select max(Payment_Id) as ii  from Payment;";
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String locationId = resultSet.getString(1);
            return locationId;
        }
        return null;
    }

    public static boolean paymentSaved(String paymentId, String paymentmethod, double total, String date, String reservationId, String disId) throws SQLException {

        return CrudUtil.execute(
                "INSERT INTO Payment VALUES (?,?,?,?,?,?)",
                paymentId,paymentmethod,total,date,reservationId,disId
        );
    }
}

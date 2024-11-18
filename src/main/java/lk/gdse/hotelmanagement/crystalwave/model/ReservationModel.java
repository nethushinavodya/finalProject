package lk.gdse.hotelmanagement.crystalwave.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.ReservationDTO;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationModel {

    public static String search(String roomId) throws SQLException {
        String sql = "select * from Room where Room_Id=?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,roomId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String roomType  = resultSet.getString(4);
            return roomType ;
        }
return null;
    }

    public static String getCurrentId() throws SQLException {
            String sql = " select max(Reservation_Id) as ii  from Reservation;";
            PreparedStatement pstm = DBConnection.getInstance().getConnection()
                    .prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()) {
                String locationId = resultSet.getString(1);
                return locationId;
            }
            return null;
    }

    public static boolean saveReservation(ReservationDTO reservationDTO, double total, String paymentId, String paymentmethod, String disId) throws SQLException {
        System.out.println("model");
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            System.out.println("000");
            boolean isReserved = saveReservation1(reservationDTO);

            if(isReserved) {
                System.out.println("111");
                boolean isSave = RoomReserveModel.saved(reservationDTO.getRoomReserves());

                if(isSave) {
                    String date = LocalDate.now().toString();
                    System.out.println("555");
                    boolean isPaymentSaved = PaymentModel.paymentSaved(paymentId,paymentmethod,total,date,reservationDTO.getReservationId(),disId);
                    if(isPaymentSaved) {
                        connection.commit();
                        return true;
                    }else {
                        connection.rollback();
                        return false;
                    }
                }else {
                    connection.rollback();
                    return false;
                }
            }else {
                System.out.println("222");
                connection.rollback();
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }

    }

    private static boolean saveReservation1(ReservationDTO dto) throws SQLException {
        return CrudUtil.execute("insert into Reservation values(?,?,?,?,?)",
                    dto.getReservationId(),
                    dto.getGuestId(),
                    dto.getRoomId(),
                    dto.getCheckInDate(),
                    dto.getCheckOutDate()
                );



    }

    public static ObservableList<String> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute(
                "SELECT * from Reservation "
        );
        ObservableList<String> list = FXCollections.observableArrayList();
        while (rst.next()){
            String id = rst.getString(1);
            list.add(id);
        }
        return FXCollections.observableList(list);
    }
}

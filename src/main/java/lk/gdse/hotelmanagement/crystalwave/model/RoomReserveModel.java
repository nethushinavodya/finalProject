package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.dto.RoomReserveDTO;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoomReserveModel {
    public static boolean saved(ArrayList<RoomReserveDTO> roomReserves) throws SQLException {
        for (RoomReserveDTO roomReserve : roomReserves) {
            System.out.println("ertjk");
            System.out.println( roomReserves);
            boolean isSaved = save1(roomReserve.getRoomId(),roomReserve.getReservationId());
            if (!isSaved) {
                return false;

            }
            boolean isUpdate1 = RoomModel.updateAvailable(roomReserve.getRoomId());
        }

        return true;
    }

    private static boolean save1(String roomId, String reservationId) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Reservation_Room VALUES (?,?)",
                reservationId,
                roomId

        );

    }
}

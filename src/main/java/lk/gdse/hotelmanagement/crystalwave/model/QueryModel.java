package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.dto.tm.CheckInDetailTM;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryModel {

    public static List<CheckInDetailTM> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT \n" +
                "    R.Reservation_Id,\n" +
                "    R.Guest_Id,\n" +
                "    Ro.Room_Id,\n" +
                "    R.CheckIn_Date,\n" +
                "    R.CheckOut_Date,\n" +
                "    P.Amount_Paid\n" +
                "FROM \n" +
                "    Reservation R\n" +
                "JOIN \n" +
                "    Reservation_Room RR ON R.Reservation_Id = RR.Reservation_Id\n" +
                "JOIN \n" +
                "    Room Ro ON RR.Room_Id = Ro.Room_Id\n" +
                "LEFT JOIN \n" +
                "    Payment P ON R.Reservation_Id = P.Reservation_Id\n" +
                "ORDER BY \n" +
                "    R.Reservation_Id;\n");
        List<CheckInDetailTM> res = new ArrayList<>();
        while (rst.next()){
            CheckInDetailTM checkInDetailTM = new CheckInDetailTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            res.add(checkInDetailTM);
        }
        return res;
    }
}

package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.AddBilDto;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddBillModel {
    public static String getCurrentId() throws SQLException {
        ResultSet rst = CrudUtil.execute(
                "select max(Bill_Id) as ii  from Billing;"
        );
        while (rst.next()){
            String id = rst.getString(1);
            return id;
        }
        return null;
    }

    public static boolean save(AddBilDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        boolean result = false;

        try{
            boolean isSaved = CrudUtil.execute(
                    "INSERT into Billing values (?,?,?,?)",
                    dto.getBillid(),dto.getRid(),dto.getTot(),dto.getDate()
            );

            if (isSaved){
                for (String sid : dto.getServiceList()){
                    boolean isSaved2 = CrudUtil.execute(
                            "INSERT into Service_Billing values (?,?)",
                            dto.getBillid(),
                            sid);
                    if (isSaved2){
                        result = true;

                    }else {
                        result = false;
                    }

                }

                if (result){
                    connection.commit();
                }
                else{
                    connection.rollback();
                }
            }else {
                connection.rollback();
            }

        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
return result;
    }
}

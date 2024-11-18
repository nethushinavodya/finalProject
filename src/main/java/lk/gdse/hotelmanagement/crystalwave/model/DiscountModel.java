package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.DiscountDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.DiscountTM;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountModel {
    public static boolean save(DiscountDTO discountDTO) throws SQLException {
        String sql = "INSERT INTO Discount VALUES (?,?,?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,discountDTO.getDiscountId());
        preparedStatement.setString(2,discountDTO.getDiscountType());
        preparedStatement.setString(3, String.valueOf(discountDTO.getDiscountCondition()));
        preparedStatement.setString(4, discountDTO.getDiscountStartDate());
        preparedStatement.setString(5, discountDTO.getDiscountEndDate());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Discount WHERE Discount_Id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate() > 0;
    }

    public static List<DiscountTM> getAll() throws SQLException {
        List<DiscountTM> discountTMList = new ArrayList<>();
        String sql = "SELECT * FROM Discount";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String discountId = resultSet.getString(1);
            String discountType = resultSet.getString(2);
            int discountCondition = Integer.parseInt(resultSet.getString(3));
            String discountStartDate = resultSet.getString(4);
            String discountEndDate = resultSet.getString(5);
            DiscountTM discountTM = new DiscountTM(discountId, discountType, discountCondition, discountStartDate, discountEndDate);
            discountTMList.add(discountTM);

        }
        return discountTMList;
    }

    public static List<String> getDiscount() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Discount_Id FROM Discount ");
        List<String> list = new ArrayList<>();
        while (rst.next()){
            String roomId = rst.getString(1);
            list.add(roomId);
        }
        return list;
    }

    public static int search(int discountId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Conditions FROM Discount WHERE Discount_Id = ?", discountId);


        while (rst.next()){
            int disId = Integer.parseInt(rst.getString(1));
            return disId;
        }
        return 0;
    }
}

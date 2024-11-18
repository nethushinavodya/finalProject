package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.AddServiceDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.AddServiceTM;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.RoomTypeTm;
import lk.gdse.hotelmanagement.crystalwave.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddServiceModel {
    public static boolean save(AddServiceDTO addServiceDTO) throws SQLException {
        String sql = "INSERT INTO Service VALUES (?,?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, String.valueOf(addServiceDTO.getServiceId()));
        preparedStatement.setString(2, addServiceDTO.getServiceName());
        preparedStatement.setString(3, addServiceDTO.getServiceDescription());
        preparedStatement.setString(4,addServiceDTO.getServicePrice());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean update(AddServiceDTO addServiceDTO) throws SQLException {
        String sql = "UPDATE Service set Service_Name = ?, Description = ?, Price = ? where Service_Id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, addServiceDTO.getServiceName());
        preparedStatement.setString(2, addServiceDTO.getServiceDescription());
        preparedStatement.setString(3, addServiceDTO.getServicePrice());
        preparedStatement.setString(4, addServiceDTO.getServiceId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean delete(String ServiceId) throws SQLException {
        String sql = "DELETE FROM Service WHERE Service_Id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,ServiceId);

        return preparedStatement.executeUpdate() > 0;
    }

    public static List<AddServiceDTO> getAll() throws SQLException {
        System.out.println("add service model");
        List<AddServiceDTO> list = new ArrayList<>();

        String sql = "select * from Service";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("resultSet");
        while (resultSet.next()) {
            String sId = resultSet.getString(1);
            String sName = resultSet.getString(2);
            String sDescription = resultSet.getString(3);
            String sPrice = resultSet.getString(4);
            AddServiceDTO serviceTypeDto1 = new AddServiceDTO(sId,sName,sDescription,sPrice);
            list.add(serviceTypeDto1);

        }
        return list;
    }

    public static String getprice(String serviceId) throws SQLException {
        ResultSet rst = CrudUtil.execute(
                "SELECT Price from Service where Service_Id = ?",
                serviceId
        );

        while (rst.next()){
            String price = rst.getString(1);
            return price;
        }
        return null;
    }
}

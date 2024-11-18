package lk.gdse.hotelmanagement.crystalwave.model;

import lk.gdse.hotelmanagement.crystalwave.db.DBConnection;
import lk.gdse.hotelmanagement.crystalwave.dto.AddGuestDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.GuestDTO;
import lk.gdse.hotelmanagement.crystalwave.dto.tm.AddGuestTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddGuestModel {

    public static boolean save(AddGuestDTO addGuestDTO) throws SQLException {
        String sql = "INSERT INTO Guest VALUES (?,?,?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,addGuestDTO.getGuestId());
        preparedStatement.setString(2,addGuestDTO.getGuestName());
        preparedStatement.setString(3,addGuestDTO.getGuestPhone());
        preparedStatement.setString(4,addGuestDTO.getGuestAddress());
        preparedStatement.setString(5,addGuestDTO.getGuestEmail());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean update(AddGuestDTO addGuestDTO) throws SQLException {
        String sql = "UPDATE Guest set Name = ?,Contact_No = ?,Address=?,Email=? where Guest_Id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,addGuestDTO.getGuestName());
        preparedStatement.setString(2,addGuestDTO.getGuestPhone());
        preparedStatement.setString(3,addGuestDTO.getGuestAddress());
        preparedStatement.setString(4,addGuestDTO.getGuestEmail());
        preparedStatement.setString(5,addGuestDTO.getGuestId());
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Guest WHERE Guest_Id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate() > 0;
    }

    public static List<AddGuestTM> getAll() throws SQLException {
        List<AddGuestTM> list = new ArrayList<>();

        String sql = "SELECT * FROM Guest";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String guestId = resultSet.getString(1);
            String guestName = resultSet.getString(2);
            String guestPhone = resultSet.getString(3);
            String guestAddress = resultSet.getString(4);
            String guestEmail = resultSet.getString(5);
            AddGuestTM addGuestTM = new AddGuestTM(guestId, guestName, guestPhone, guestAddress, guestEmail);
            list.add(addGuestTM);
        }
        return list;
    }

    public static AddGuestDTO search(String guestId) throws SQLException {
        String sql = "SELECT * FROM Guest where Guest_Id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,guestId);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            String id = resultSet.getString(1);
            String guestName = resultSet.getString(2);
            String guestPhone = resultSet.getString(3);
            String guestAddress = resultSet.getString(4);
            String guestEmail = resultSet.getString(5);

            AddGuestDTO guestDTO1 = new AddGuestDTO(id , guestName, guestPhone, guestAddress, guestEmail);
            return guestDTO1;
        }
        return null;
    }
}

package repository;

import database.Database;
import entity.Address;

import java.sql.*;

public class AddressRepository {
    private final Connection connection = Database.getConnection();


    public Address getAddress(int id) {

        try {
            String query = "SELECT * FROM Address WHERE id=?";

            CallableStatement statement = connection.prepareCall(query);
            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                Address outputAddress = new Address();
                outputAddress.setId(resultSet.getInt("id"));
                outputAddress.setStreet(resultSet.getString("street"));
                outputAddress.setHouseNumber(resultSet.getString("house_number"));
                outputAddress.setCity(resultSet.getString("city"));
                outputAddress.setCountry(resultSet.getString("country"));
                return outputAddress;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void saveAddress(Address Address) {

        try {
            String query = "INSERT INTO CUSTOMER(street, house_number, city, country) VALUES " +
                    "(?,?,?,?)";

            CallableStatement statement = connection.prepareCall(query);
            statement.setString(1, Address.getStreet());
            statement.setString(2, Address.getHouseNumber());
            statement.setString(3, Address.getCity());
            statement.setString(4, Address.getCountry());
            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteAddress(int id) {

        String query = "DELETE FROM ADDRESS WHERE id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

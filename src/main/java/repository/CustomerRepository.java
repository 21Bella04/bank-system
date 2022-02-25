package repository;

import database.Database;
import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final Connection connection = Database.getConnection();

    public Customer getCustomer(int id) {

        try {
            String query = "SELECT * FROM Customer WHERE id=?";

            CallableStatement statement = connection.prepareCall(query);
            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                Customer outputCustomer = new Customer();
                outputCustomer.setId(resultSet.getInt("id"));
                outputCustomer.setName(resultSet.getString("name"));
                outputCustomer.setSurname(resultSet.getString("surname"));
                outputCustomer.setAddress(resultSet.getInt("address_fk"));
                outputCustomer.setPhoneNumber(resultSet.getString("phone_number"));
                outputCustomer.setEmail(resultSet.getString("email"));
                return outputCustomer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void saveCustomer(Customer Customer) {

        try {
            String query = "INSERT INTO CUSTOMER(NAME,SURNAME,ADDRESS,PHONE_NUMBER,EMAIL) VALUES " +
                    "(?,?,?,?,?)";

            CallableStatement statement = connection.prepareCall(query);
            statement.setString(1, Customer.getName());
            statement.setString(2, Customer.getSurname());
            statement.setInt(3, Customer.getAddress());
            statement.setString(4, Customer.getPhoneNumber());
            statement.setString(5, Customer.getEmail());
            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteCustomer(int id) {

        String query = "DELETE FROM CUSTOMER WHERE id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO 1. At least 2 queries should query data from more than one table, i.e.,
    //  you should use at least two multirelation queries
    public List<Customer> findDebtors() {
        try {
            String query = "SELECT * FROM customer" +
                    " WHERE ID in (SELECT DISTINCT CUSTOMER_FK from account where BALANCE < 0 AND ADMIN = 0)";

            CallableStatement statement = connection.prepareCall(query);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            final List<Customer> result = new ArrayList<>();
            while (resultSet != null && resultSet.next()) {
                Customer outputCustomer = new Customer();
                outputCustomer.setId(resultSet.getInt("id"));
                outputCustomer.setName(resultSet.getString("name"));
                outputCustomer.setSurname(resultSet.getString("surname"));
                outputCustomer.setAddress(resultSet.getInt("address_fk"));
                outputCustomer.setPhoneNumber(resultSet.getString("phone_number"));
                outputCustomer.setEmail(resultSet.getString("email"));
                result.add(outputCustomer);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO 1. At least 2 queries should query data from more than one table, i.e.,
    //  you should use at least two multirelation queries
    public void deleteEveryCustomerAccount(Integer customerId) {

        String query = "DELETE FROM account" +
                " WHERE CUSTOMER_FK in (SELECT DISTINCT ID from customer where id = ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

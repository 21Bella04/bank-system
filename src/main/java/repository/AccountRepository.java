package repository;

import database.Database;
import entity.Account;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private final Connection connection = Database.getConnection();

    public Account getAccount(int id) {
        try {
            String query = "SELECT * FROM ACCOUNT WHERE ID=?";

            CallableStatement statement = connection.prepareCall(query);
            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                Account outputAccount = new Account();
                outputAccount.setId(resultSet.getInt("id"));
                outputAccount.setCustomer(resultSet.getInt("customer_fk"));
                outputAccount.setBankAccountNumber(resultSet.getString("bank_account_number"));
                outputAccount.setBalance(resultSet.getBigDecimal("balance"));
                outputAccount.setLogin(resultSet.getString("login"));
                outputAccount.setPassword(resultSet.getString("password"));
                outputAccount.setAdmin(resultSet.getBoolean("admin"));
                return outputAccount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void saveAccount(Account account) {
        try {
            String query = "INSERT INTO ACCOUNT(CUSTOMER_FK,BANK_ACCOUNT_NUMBER,BALANCE, LOGIN, PASSWORD, ADMIN) VALUES " +
                    "(?,?,?,?,?,?)";

            CallableStatement statement = connection.prepareCall(query);
            statement.setInt(1, account.getCustomer());
            statement.setString(2, account.getBankAccountNumber());
            statement.setBigDecimal(3, account.getBalance());
            statement.setString(4, account.getLogin());
            statement.setString(5, account.getPassword());
            statement.setBoolean(6, account.getAdmin());
            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteAccount(int id) {
        String query = "DELETE FROM ACCOUNT WHERE id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //TODO 2. You should make use of SQL JOIN
    public List<Account> findAccountByCustomerId(Integer customerId) {
        try {
            String query = "SELECT a.ID, a.CUSTOMER_FK, a.BANK_ACCOUNT_NUMBER, a.BALANCE, a.LOGIN, a.PASSWORD, a.ADMIN FROM CUSTOMER " +
                    "INNER JOIN account a on customer.ID = a.CUSTOMER_FK" +
                    " WHERE customer.id=?";

            CallableStatement statement = connection.prepareCall(query);
            statement.setInt(1, customerId);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            final List<Account> result = new ArrayList<>();
            while (resultSet != null && resultSet.next()) {
                Account outputAccount = new Account();
                outputAccount.setId(resultSet.getInt("id"));
                outputAccount.setCustomer(resultSet.getInt("customer_fk"));
                outputAccount.setBankAccountNumber(resultSet.getString("bank_account_number"));
                outputAccount.setBalance(resultSet.getBigDecimal("balance"));
                outputAccount.setLogin(resultSet.getString("login"));
                outputAccount.setPassword(resultSet.getString("password"));
                outputAccount.setAdmin(resultSet.getBoolean("admin"));
                result.add(outputAccount);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account login(String login, String password) {
        try {
            String query = "SELECT * FROM ACCOUNT WHERE LOGIN=? and PASSWORD=?";

            CallableStatement statement = connection.prepareCall(query);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                Account outputAccount = new Account();
                outputAccount.setId(resultSet.getInt("id"));
                outputAccount.setCustomer(resultSet.getInt("customer_fk"));
                outputAccount.setBankAccountNumber(resultSet.getString("bank_account_number"));
                outputAccount.setBalance(resultSet.getBigDecimal("balance"));
                outputAccount.setLogin(resultSet.getString("login"));
                outputAccount.setPassword(resultSet.getString("password"));
                outputAccount.setAdmin(resultSet.getBoolean("admin"));
                return outputAccount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account findByAccountNumber(String bankaAcountNumber) {
        try {
            String query = "SELECT * FROM ACCOUNT WHERE BANK_ACCOUNT_NUMBER=?";

            CallableStatement statement = connection.prepareCall(query);
            statement.setString(1, bankaAcountNumber);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                Account outputAccount = new Account();
                outputAccount.setId(resultSet.getInt("id"));
                outputAccount.setCustomer(resultSet.getInt("customer_fk"));
                outputAccount.setBankAccountNumber(resultSet.getString("bank_account_number"));
                outputAccount.setBalance(resultSet.getBigDecimal("balance"));
                outputAccount.setLogin(resultSet.getString("login"));
                outputAccount.setPassword(resultSet.getString("password"));
                outputAccount.setAdmin(resultSet.getBoolean("admin"));
                return outputAccount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void updateBalance(Account loggedAccount) {
        try {
            String query = "UPDATE account set BALANCE =? where account.ID =?";

            CallableStatement statement = connection.prepareCall(query);
            statement.setBigDecimal(1, loggedAccount.getBalance());
            statement.setInt(2, loggedAccount.getId());
            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //TODO 3. You should make use of Aggregation and/or Grouping
    public BigDecimal findBalanceFromAllAccountsByUserId(Integer customerId) {
        String query = "SELECT SUM(BALANCE) as balance FROM ACCOUNT WHERE CUSTOMER_FK = ?";
        try {
            CallableStatement statement = connection.prepareCall(query);
            statement.setInt(1, customerId);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                return resultSet.getBigDecimal("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package repository;

import database.Database;
import dictionary.TransactionType;
import entity.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private final Connection connection = Database.getConnection();

    public Transaction getTransaction(int id) {

        try {
            String query = "SELECT * FROM TRANSACTION WHERE id=?";

            CallableStatement statement = connection.prepareCall(query);
            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                Transaction outputTransaction = new Transaction();
                outputTransaction.setId(resultSet.getInt("id"));
                outputTransaction.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                outputTransaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                outputTransaction.setAmount(resultSet.getBigDecimal("amount"));
                outputTransaction.setAccount(resultSet.getInt("account_fk"));
                return outputTransaction;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Transaction> getAccountTransactions(int accountId) {

        try {
            String query = "SELECT *" +
                    " FROM TRANSACTION" +
                    " WHERE ACCOUNT_FK =?";

            CallableStatement statement = connection.prepareCall(query);
            statement.setInt(1, accountId);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            List<Transaction> result = new ArrayList<>();
            while (resultSet != null && resultSet.next()) {
                Transaction outputTransaction = new Transaction();
                outputTransaction.setId(resultSet.getInt("id"));
                outputTransaction.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                outputTransaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                outputTransaction.setAmount(resultSet.getBigDecimal("amount"));
                outputTransaction.setAccount(resultSet.getInt("account_fk"));
                result.add(outputTransaction);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void saveTransaction(Transaction Transaction) {

        try {
            String query = "INSERT INTO TRANSACTION(TYPE, AMOUNT, ACCOUNT_FK) VALUES " +
                    "(?,?,?)";

            CallableStatement statement = connection.prepareCall(query);
            statement.setString(1, Transaction.getType().name());
            statement.setBigDecimal(2, Transaction.getAmount());
            statement.setInt(3, Transaction.getAccount());
            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteTransaction(int id) {

        String query = "DELETE FROM TRANSACTION WHERE id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

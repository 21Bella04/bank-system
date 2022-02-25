package repository;

import database.Database;
import view.BankCapitalView;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class ViewRepository {
    private final Connection connection = Database.getConnection();

    public BankCapitalView getBankCapitalInfo() {

        try {
            String query = "SELECT * FROM bank_capital";

            CallableStatement statement = connection.prepareCall(query);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet != null && resultSet.next()) {
                BankCapitalView bankCapitalViewOutput = new BankCapitalView();
                bankCapitalViewOutput.setBankCapital(resultSet.getBigDecimal("capital_balance"));
                bankCapitalViewOutput.setAverageBankCapital(resultSet.getBigDecimal("average_balance"));
                return bankCapitalViewOutput;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

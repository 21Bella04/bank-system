package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/bank?useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "admin";
    private static Connection con;

    public static Connection getConnection() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager
                        .getConnection(URL, USER, PASS);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        return con;
    }
}

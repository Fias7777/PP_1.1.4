package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String  DB_URL= "jdbc:mysql://localhost:3306/customer";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("CONNECTION OK");
        } catch (SQLException | ClassNotFoundException e)  {
            System.out.println("CONNECTION ERROR");
            throw new RuntimeException(e);
        }
        return connection;
    }
}

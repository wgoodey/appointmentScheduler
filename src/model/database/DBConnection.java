package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    //JDBC URL components
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/WJ06Fa5";

    //concatenated URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    //Driver and interface reference
    private static final String MySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    //username and password
    private static final String username = "U06Fa5";
    private static final String password = "53688746693";

    public static Connection startConnection() {
        try {
            Class.forName(MySQLJDBCDriver);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        } catch (SQLException e){
            System.out.println("SQL exception: " + e.getMessage());
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

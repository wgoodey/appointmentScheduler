package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The DBConnection class manages the connection to the database utilized throughout the application.
 */
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


    /**
     * Creates a Connection to the database.
     * @return the Connection to the MySQL database.
     */
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

    /**
     * Closes the Connection to the MySQL database.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets the connection.
     * @return the MySQL database connection
     */
    public static Connection getConnection() {
        return connection;
    }


}

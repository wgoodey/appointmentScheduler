package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The DBQuery class manages preparedStatements for making queries to the database.
 */
public class DBQuery {

    //Statement reference
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    /**
     * Gets a SQL statement for MySQL query.
     * @return SQL statement.
     */
    //return Statement object
    public static Statement getStatement() {
        return statement;
    }

    /**
     * Sets a SQL statement for MySQL query.
     * @param connection the Connection to the MySQL database.
     * @throws SQLException
     */
    //create Statement object
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    /**
     * Gets a PreparedStatement for MySQL query.
     * @return PreparedStatement for MySQL query.
     */
    //return prepared Statement object
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    /**
     * Sets a PreparedStatement for MySQL query.
     * @param connection the Connection to the MySQL database.
     * @param sqlStatement the String template for a MySQL query.
     * @throws SQLException
     */
    //create prepared Statement object
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        preparedStatement = connection.prepareStatement(sqlStatement);
    }

    /**
     * prepares a ZonedDateTime for use in a MySQL query.
     * @param zdt the ZonedDateTime to be formatted.
     * @return a String in the proper format for MySQL.
     */
    public static String getSQLFormattedTime(ZonedDateTime zdt) {
        //get local time and format it for sql
        DateTimeFormatter dateTimeSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return zdt.format(dateTimeSQL);
    }
}

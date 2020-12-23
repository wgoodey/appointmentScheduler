package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBQuery {

    //Statement reference
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    
    //return Statement object
    public static Statement getStatement() {
        return statement;
    }

    //create Statement object
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    //return prepared Statement object
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    //create prepared Statement object
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        preparedStatement = connection.prepareStatement(sqlStatement);
    }

    public static String getSQLFormattedDateTime(LocalDateTime localDateTime) {
        //get local time and format it for sql
        DateTimeFormatter dateTimeSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return localDateTime.format(dateTimeSQL);
    }
}

package model.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

    //statement reference
    private static Statement statement;
    
    //return statement object
    public static Statement getStatement() {
        return statement;
    }

    //create statement object
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }
}

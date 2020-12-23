package model.database;

import model.Country;
import model.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUpdateData {

    public static void updateCountry(Connection connection, Country country) throws SQLException {
        String updateStatement = "UPDATE countries SET Country = ?, Last_Updated_By = ?";

        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String countryName = country.getName();
        String last_Updated_By = Data.getCurrentUser().getUsername();

        //key-value mapping
        preparedStatement.setString(1, countryName);
        preparedStatement.setString(2, last_Updated_By);

        //execute PreparedStatement
        preparedStatement.execute();
    }
}

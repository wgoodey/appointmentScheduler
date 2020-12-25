package model.database;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUpdate {

    public static boolean updateCountry(Connection connection, Country country) throws SQLException {
        String updateStatement = "UPDATE countries " +
                                 "SET Country = ?, Last_Updated_By = ? " +
                                 "WHERE Country_ID = ?";

        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String countryName = country.getName();
        String last_Updated_By = Data.getCurrentUser().getUsername();
        String countryID = String.valueOf(country.getID());

        //key-value mapping
        preparedStatement.setString(1, countryName);
        preparedStatement.setString(2, last_Updated_By);
        preparedStatement.setString(3, countryID);

        try {
            //execute PreparedStatement
            preparedStatement.execute();
            return true;

        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }
        return false;
    }

    public static boolean updateDivision(Connection connection, Division division) throws SQLException {
        String updateStatement = "UPDATE first_level_divisions " +
                                 "SET Division = ?, Last_Updated_By = ? " +
                                 "WHERE Division_ID = ?";

        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String divisionName = division.getName();
        String countryID = String.valueOf(division.getCountryID());
        String last_Updated_By = Data.getCurrentUser().getUsername();
        String divisionID = String.valueOf(division.getID());

        //key-value mapping
        preparedStatement.setString(1, divisionName);
        preparedStatement.setString(2, countryID);
        preparedStatement.setString(3, last_Updated_By);
        preparedStatement.setString(4, divisionID);

        try {
            //execute PreparedStatement
            preparedStatement.execute();
            return true;

        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }
        return false;
    }

    public static boolean updateCustomer(Connection connection, Customer customer) throws SQLException {
        String updateStatement = "Update customers " +
                                 "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Updated_By = ?, Division_ID = ?" +
                                 "WHERE Customer_ID = ?";

        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String customerID = String.valueOf(customer.getCustomerID());
        String customerName = customer.getName();
        String address = customer.getAddress();
        String postal = customer.getPostalCode();
        String phone = customer.getPhone();
        String divisionID = String.valueOf(Data.getDivisionID(customer.getCountry(), customer.getDivision()));
        String lastUpdatedBy = Data.getCurrentUser().getUsername();

        //key-value mapping
        preparedStatement.setString(1, customerName);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, postal);
        preparedStatement.setString(4, phone);
        preparedStatement.setString(5, lastUpdatedBy);
        preparedStatement.setString(6, divisionID);
        preparedStatement.setString(7, customerID);

        try {
            //execute PreparedStatement
            preparedStatement.execute();
            return true;

        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }
        return false;
    }

    public static boolean  updateAppointment(Connection connection, Appointment appointment) throws SQLException {
        String updateStatement = "Update appointments " +
                                 "SET title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Contact_ID = ?, User_ID = ?, Last_Updated_By = ? " +
                                 "WHERE Appointment_ID = ?";

        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        //TODO figure out timezones
        String start = DBQuery.getSQLFormattedDateTime(appointment.getStartTime());
        String end = DBQuery.getSQLFormattedDateTime(appointment.getEndTime());
        String contactID = String.valueOf(appointment.getContactID());
        String userID = String.valueOf(appointment.getUserID());
        String lastUpdatedBy = Data.getCurrentUser().getUsername();
        String appointmentID = String.valueOf(appointment.getAppointmentID());

        //key-value mapping
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setString(5, start);
        preparedStatement.setString(6, end);
        preparedStatement.setString(7, contactID);
        preparedStatement.setString(8, userID);
        preparedStatement.setString(9, lastUpdatedBy);
        preparedStatement.setString(10, appointmentID);

        try {
            //execute PreparedStatement
            preparedStatement.execute();
            return true;

        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }
        return false;
    }
}

package model.database;

import model.Appointment;
import model.Customer;
import model.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The DBSelect class queries the database for the most recently added record in order to load auto-generated information into the application's internal lists.
 */
public class DBSelect {

    /**
     * Queries a newly inserted customer from the customers table in the MySQL database;
     * @param connection the Connection for the MySQL database.
     * @param customer the customer that was inserted.
     * @return the customerID;
     * @throws SQLException if the database query cannot be executed.
     */
    public static int getNewCustomerID(Connection connection, Customer customer) throws SQLException {
        String selectStatement = "SELECT * " +
                                 "FROM customers " +
                                 "WHERE Customer_Name = ? AND Phone = ? AND Address = ? AND Postal_Code = ? AND Division_ID = ?";

        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String customerName = customer.getName();
        String address = customer.getAddress();
        String postal = customer.getPostalCode();
        String phone = customer.getPhone();
        String divisionID = String.valueOf(Data.getDivisionID(customer.getCountry(), customer.getDivision()));

        //key-value mapping
        preparedStatement.setString(1, customerName);
        preparedStatement.setString(2, phone);
        preparedStatement.setString(3, address);
        preparedStatement.setString(4, postal);
        preparedStatement.setString(5, divisionID);


        try {
            preparedStatement.execute();
            ResultSet result = preparedStatement.getResultSet();
            result.next();
            return result.getInt("Customer_ID");

        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }

        return customer.getCustomerID();
    }

    /**
     * Queries a newly inserted appointment from the appointments table in the MySQL database;
     * @param connection the Connection for the MySQL database.
     * @param appointment the customer that was inserted.
     * @return the appointmentID;
     * @throws SQLException if the database query cannot be executed.
     */
    public static int getNewAppointmentID(Connection connection, Appointment appointment) throws SQLException {
        String selectStatement = "SELECT * " +
                                 "FROM appointments " +
                                 "WHERE Customer_ID = ? AND User_ID = ? AND Start = ? AND End = ?";

        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //convert times to UTC
        ZonedDateTime localStart = appointment.getStartTime();
        ZonedDateTime localEnd = appointment.getEndTime();
        ZonedDateTime UTCStart = localStart.withZoneSameInstant(Data.getUTC());
        ZonedDateTime UTCEnd = localEnd.withZoneSameInstant(Data.getUTC());

        //record data
        String customerID = String.valueOf(appointment.getCustomerID());
        String userID = String.valueOf(appointment.getUserID());
        String start = String.valueOf(appointment.getStartTime());
        String end = String.valueOf(appointment.getEndTime());

        //key-value mapping
        preparedStatement.setString(1, customerID);
        preparedStatement.setString(2, userID);
        preparedStatement.setString(3, DBQuery.getSQLFormattedTime(UTCStart));
        preparedStatement.setString(4, DBQuery.getSQLFormattedTime(UTCEnd));


        try {
            preparedStatement.execute();
            ResultSet result = preparedStatement.getResultSet();
            result.next();
            return result.getInt("Appointment_ID");

        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }

        return appointment.getAppointmentID();
    }
}

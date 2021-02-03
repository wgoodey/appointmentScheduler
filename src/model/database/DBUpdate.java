package model.database;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZonedDateTime;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The DBUpdate class manages making updates to records in the database.
 */
public class DBUpdate {

    /**
     * Updates a country in the countries table of the MySQL database.
     * @param connection the Connection for the MySQL database.
     * @param country the country to update in the database.
     * @return true if successful and false if not.
     * @throws SQLException
     */
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

    /**
     * Updates a division in the first_level_divisions table of the MySQL database.
     * @param connection the Connection for the MySQL database.
     * @param division the division to update in the database.
     * @return true if successful and false if not.
     * @throws SQLException
     */
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

    /**
     * Updates a customer in the customers table of the MySQL database.
     * @param connection the Connection for the MySQL database.
     * @param customer the customer to update in the database.
     * @return true if successful and false if not.
     * @throws SQLException
     */
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

    /**
     * Updates an appointment in the appointments table of the MySQL database.
     * @param connection the Connection for the MySQL database.
     * @param appointment the appointment to update in the database.
     * @return true if successful and false if not.
     * @throws SQLException
     */
    public static boolean  updateAppointment(Connection connection, Appointment appointment) throws SQLException {
        String updateStatement = "Update appointments " +
                                 "SET title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Contact_ID = ?, User_ID = ?, Last_Updated_By = ? " +
                                 "WHERE Appointment_ID = ?";

        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //convert times to UTC
        ZonedDateTime starting = appointment.getStartTime().withZoneSameInstant(Data.getUTC());
        ZonedDateTime ending = appointment.getEndTime().withZoneSameInstant(Data.getUTC());

        //record data
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        String start = DBQuery.getSQLFormattedTime(starting);
        String end = DBQuery.getSQLFormattedTime(ending);
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

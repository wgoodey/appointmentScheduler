package model.database;

import model.Appointment;
import model.Country;
import model.Customer;
import model.Division;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The DBDelete class manages the deletion of records from the database.
 */
public class DBDelete {

    /**
     * Deletes a country from the database.
     * @param connection the Connection to the MySQL database.
     * @param country the country to be deleted.
     * @return true if successfully deleted and false if not.
     * @throws SQLException if the database query cannot be executed.
     */
    public static boolean deleteCountry(Connection connection, Country country) throws SQLException {
        String deleteStatement = "DELETE FROM countries WHERE Country_ID = ?";

        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String countryID = String.valueOf(country.getID());

        //key-value mapping
        preparedStatement.setString(1, countryID);

        try {
            //execute prepared statement
            preparedStatement.execute();
            return true;

        } catch (Exception throwables) {
//            e.printStackTrace();
            System.out.println(throwables.getMessage());
        }

        return false;
    }

    /**
     * Deletes a division from the database.
     * @param connection the Connection to the MySQL database.
     * @param division the division to be deleted.
     * @return true if successfully deleted and false if not.
     * @throws SQLException if the database query cannot be executed.
     */
    public static boolean deleteDivision(Connection connection, Division division) throws SQLException {
        String deleteStatement = "DELETE FROM first_level_divisions WHERE Division_ID = ?";

        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String divisionID = String.valueOf(division.getID());

        //key-value mapping
        preparedStatement.setString(1, divisionID);

        try {
            //execute prepared statement
            preparedStatement.execute();
            return true;

        } catch (Exception throwables) {
//            e.printStackTrace();
            System.out.println(throwables.getMessage());
        }

        return false;
    }

    /**
     * Deletes a customer from the database.
     * @param connection the Connection to the MySQL database.
     * @param customer the customer to be deleted.
     * @return true if successfully deleted and false if not.
     * @throws SQLException if the database query cannot be executed.
     */
    public static boolean deleteCustomer(Connection connection, Customer customer) throws SQLException {
        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";

        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String customerID = String.valueOf(customer.getCustomerID());

        //key-value mapping
        preparedStatement.setString(1, customerID);

        try {
            //execute prepared statement
            preparedStatement.execute();
            return true;

        } catch (Exception throwables) {
//            e.printStackTrace();
            System.out.println(throwables.getMessage());
        }

        return false;
    }

    /**
     * Deletes an appointment from the database.
     * @param connection the Connection to the MySQL database.
     * @param appointment the appointment to be deleted.
     * @return true if successfully deleted and false if not.
     * @throws SQLException if the database query cannot be executed.
     */
    public static boolean deleteAppointment(Connection connection, Appointment appointment) throws SQLException {
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String appointmentID = String.valueOf(appointment.getAppointmentID());

        //key-value mapping
        preparedStatement.setString(1, appointmentID);

        try {
            //execute prepared statement
            preparedStatement.execute();
            return true;

        } catch (Exception throwables) {
//            e.printStackTrace();
            System.out.println(throwables.getMessage());
        }

        return false;
    }

}

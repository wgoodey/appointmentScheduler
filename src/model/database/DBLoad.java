package model.database;

import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The DBLoad class manages the loading of records from the database into the application's internal lists.
 */
public class DBLoad {

    /**
     * Queries the countries table in the database and loads all results into the Data.AllCountries list.
     * @param connection the Connection to the MySQL database.
     * @throws SQLException if the database query cannot be executed.
     */
    public static void loadCountries(Connection connection) throws SQLException {
        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();

        String selectStatement = "SELECT * FROM countries";
        try {
            statement.execute(selectStatement);
            ResultSet result = statement.getResultSet();

            //iterate through countries table
            while (result.next()) {
                int countryId = result.getInt("Country_ID");
                String name = result.getString("Country");

                Country country = new Country(countryId, name);
                Data.addCountry(country);
            }

            //iterate through Countries list and add divisions from database
            for(Country country : Data.getAllCountries()) {
                selectStatement = "SELECT * " +
                                  "FROM first_level_divisions " +
                                  "WHERE COUNTRY_ID = " + country.getID();
                statement.execute(selectStatement);
                result = statement.getResultSet();

                //iterate through divisions table
                while (result.next()) {
                    loadDivisions(result, country);
                }
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    /**
     * Queries the first_level_divisions table in the database and loads all results into the Country.divisions list.
     * @param result the results of the query.
     * @param country the country for which first level divisions are being loaded.
     * @throws SQLException if the database query cannot be executed.
     */
    private static void loadDivisions(ResultSet result, Country country) throws SQLException {
        try {
            int ID = result.getInt("Division_ID");
            int countryID = result.getInt("COUNTRY_ID");
            String name = result.getString("Division");

            Division division = new Division(ID, countryID, name);
            country.addDivision(division);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    /**
     * Queries the customers table in the database and loads all results into the Data.AllCustomers list.
     * @param connection the Connection to the MySQL database.
     * @throws SQLException if the database query cannot be executed.
     */
    public static void loadCustomers(Connection connection) throws SQLException {
        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();

        String selectStatement = "SELECT * " +
                                 "FROM customers, first_level_divisions, countries " +
                                 "WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.COUNTRY_ID = countries.Country_ID";
        try {
            statement.execute(selectStatement);
            ResultSet result = statement.getResultSet();

            //iterate through customers table
            while (result.next()) {
                int customerID = result.getInt("Customer_ID");
                String name = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postalCode = result.getString("Postal_Code");
                String phone = result.getString("Phone");

                String division = result.getString("Division");
                String country = result.getString("Country");

                Customer customer = new Customer(customerID, name, address, division, postalCode, country, phone);

                Data.addCustomer(customer);

            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    /**
     * Queries the contacts table in the database and loads all results into the Data.AllContacts list.
     * @param connection the Connection to the MySQL database.
     * @throws SQLException if the database query cannot be executed.
     */
    public static void loadContacts(Connection connection) throws SQLException {
        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();

        String selectStatement = "SELECT * FROM contacts";

        try {
            statement.execute(selectStatement);
            ResultSet result = statement.getResultSet();

            while (result.next()) {
                int contactID = result.getInt("Contact_ID");
                String name = result.getString("Contact_Name");
                String email = result.getString("Email");

                Contact contact = new Contact(contactID, name, email);
                Data.addContact(contact);
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    /**
     * Queries the users table in the database and loads all results into the Data.AllUsers list.
     * @param connection the Connection to the MySQL database.
     * @throws SQLException if the database query cannot be executed.
     */
    public static void loadUsers(Connection connection) throws SQLException {
        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();

        String selectStatement = "SELECT * FROM users";
        try {
            statement.execute(selectStatement);
            ResultSet result = statement.getResultSet();

            while (result.next()) {
                int userID =  result.getInt("User_ID");
                String username = result.getString("User_Name");
                String password = result.getString("Password");

                User user = new User(userID, username, password);
                Data.addUser(user);
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    /**
     * Queries the appointments table in the database and loads all results into the Data.AllAppointments list.
     * @param connection the Connection to the MySQL database.
     * @throws SQLException if the database query cannot be executed.
     */
    public static void loadAppointments(Connection connection) throws SQLException {
        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();

        for (Customer customer : Data.getAllCustomers()) {
            String selectStatement = "SELECT * " +
                                     "FROM appointments " +
                                     "WHERE Customer_ID = " + customer.getCustomerID();
            try {
                statement.execute(selectStatement);
                ResultSet result = statement.getResultSet();

                while (result.next()) {
                    //read data from database and add to customer's appointments
                    int appointmentID = result.getInt("Appointment_ID");
                    int customerID = result.getInt("Customer_ID");
                    int contactID = result.getInt("Contact_ID");
                    int userID = result.getInt("User_ID");
                    String title = result.getString("Title");
                    String description = result.getString("Description");
                    String location = result.getString("Location");
                    String type = result.getString("Type");
                    LocalDateTime startTime = result.getTimestamp("Start").toLocalDateTime();
                    ZonedDateTime start = startTime.atZone(Data.getUserZoneID());
                    LocalDateTime endTime = result.getTimestamp("End").toLocalDateTime();
                    ZonedDateTime end = endTime.atZone(Data.getUserZoneID());

                    //create new appointment and add it to the myAppointments list for the customer
                    Appointment appointment = new Appointment(appointmentID, customerID, contactID, userID, title, description, location, type, start, end);
                    Data.addAppointment(appointment);
                }
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
    }

    /**
     * Runs all the individual load methods.
     * @param connection the Connection to the MySQL database.
     * @throws SQLException if a database query cannot be executed.
     */
    public static void loadAll(Connection connection) throws SQLException {
        loadCountries(connection);
        loadCustomers(connection);
        loadContacts(connection);
        loadUsers(connection);
        loadAppointments(connection);
    }
}

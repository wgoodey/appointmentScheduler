package model.database;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DBLoadData {


    public static void loadCountries(Statement statement) throws SQLException {
        String selectStatement = "SELECT * FROM countries";
        statement.execute(selectStatement);
        ResultSet result = statement.getResultSet();

        //iterate through countries table
        while (result.next()) {
            int countryId = result.getInt("Country_ID");
            String name = result.getString("Country");

            String createdBy = result.getString("Created_By");
            LocalDateTime createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
            String lastUpdateBy = result.getString("Last_Updated_By");
            LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();

            Country country = new Country(countryId, name, createdBy, createDateTime, lastUpdateBy, lastUpdate);
            Lists.addCountry(country);
        }

        //iterate through Countries list and add divisions from database
        for(Country country : Lists.getAllCountries()) {
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
    }

    private static void loadDivisions(ResultSet result, Country country) throws SQLException {
        int ID = result.getInt("Division_ID");
        int countryID = result.getInt("COUNTRY_ID");
        String name = result.getString("Division");
        //TODO fix timeZone issue: should not be hardcoded
        ZoneId timeZone = ZoneId.of("America/Los_Angeles");

        String createdBy = result.getString("Created_By");
        LocalDateTime createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
        String lastUpdateBy = result.getString("Last_Updated_By");
        LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();

        Division division = new Division(ID, countryID, name, timeZone, createdBy, createDateTime, lastUpdateBy, lastUpdate);
        country.addDivision(division);
    }

    public static void loadCustomers(Statement statement) throws SQLException {
        String selectStatement = "SELECT * " +
                                 "FROM customers, first_level_divisions, countries " +
                                 "WHERE customers.Division_ID = first_level_divisions.Division_ID and first_level_divisions.COUNTRY_ID = countries.Country_ID";
        statement.execute(selectStatement);
        ResultSet result = statement.getResultSet();

        //iterate through customers table
        while (result.next()) {
            int customerID = result.getInt("Customer_ID");
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            //TODO modify so ID is used instead of name?
            int divisionID = result.getInt("Division_ID");
            int countryID = result.getInt("COUNTRY_ID");

            String division = result.getString("Division");
            String country = result.getString("Country");

            String createdBy = result.getString("Created_By");
            LocalDateTime createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
            String lastUpdateBy = result.getString("Last_Updated_By");
            LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();

            Customer customer = new Customer(customerID, name, address, division, postalCode, country, phone);

            Lists.addCustomer(customer);

        }
    }

    private static void getCreate_UpdateInfo (ResultSet result, String createdBy, LocalDateTime createDateTime, String lastUpdateBy, LocalDateTime lastUpdate) throws SQLException {
        createdBy = result.getString("Created_By");
        createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
        lastUpdateBy = result.getString("Last_Updated_By");
        lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
    }

    public static void loadContacts(Statement statement) throws SQLException {
        String selectStatement = "SELECT * FROM contacts";
        statement.execute(selectStatement);
        ResultSet result = statement.getResultSet();

        while (result.next()) {
            int contactID = result.getInt("Contact_ID");
            String name = result.getString("Contact_Name");
            String email = result.getString("Email");

            Contact contact = new Contact(contactID, name, email);
            Lists.addContact(contact);
        }
    }

    public static void loadUsers(Statement statement) throws SQLException {
        String selectStatement = "SELECT * FROM users";
        statement.execute(selectStatement);
        ResultSet result = statement.getResultSet();

        while (result.next()) {
            int userID =  result.getInt("User_ID");
            String username = result.getString("User_Name");
            String password = result.getString("Password");

            String createdBy = result.getString("Created_By");
            LocalDateTime createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
            String lastUpdateBy = result.getString("Last_Updated_By");
            LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();

            User user = new User(userID, username, password, createdBy, createDateTime, lastUpdateBy, lastUpdate);
            Lists.addUser(user);
        }
    }

    public static void loadAppointments(Statement statement) throws SQLException {
        for (Customer customer : Lists.getAllCustomers()) {
            String selectStatement = "SELECT * " +
                                     "FROM appointments " +
                                     "WHERE Customer_ID = " + customer.getCustomerID();
            statement.execute(selectStatement);
            ResultSet result = statement.getResultSet();

            while (result.next()) {
                int appointmentID = result.getInt("Appointment_ID");
                int customerID = result.getInt("Customer_ID");
                int contactID = result.getInt("Contact_ID");
                int userID = result.getInt("User_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime startTime = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = result.getTimestamp("End").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                ZoneId timeZone;

                //TODO fill in parameters
                Appointment appointment = new Appointment(appointmentID, customerID, contactID, userID, title, description, location, type, startTime, endTime, createdBy, createDateTime, lastUpdateBy, lastUpdate);
                customer.setAppointment(appointment);
            }
        }
    }

    public static void loadAll(Statement statement) throws SQLException {
        loadCountries(statement);
        loadCustomers(statement);
        loadContacts(statement);
        loadUsers(statement);
        loadAppointments(statement);
    }
}

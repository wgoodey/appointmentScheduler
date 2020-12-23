package model.database;

import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DBLoadData {

    public static void newLoadCountries(Connection connection) throws SQLException {
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

                String createdBy = result.getString("Created_By");
                LocalDateTime createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();

                Country country = new Country(countryId, name, createdBy, createDateTime, lastUpdateBy, lastUpdate);
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

                String createdBy = result.getString("Created_By");
                LocalDateTime createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();

                Country country = new Country(countryId, name, createdBy, createDateTime, lastUpdateBy, lastUpdate);
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

    private static void loadDivisions(ResultSet result, Country country) throws SQLException {
        try {
            int ID = result.getInt("Division_ID");
            int countryID = result.getInt("COUNTRY_ID");
            String name = result.getString("Division");

            //FIXME timeZone issue: should not be hardcoded
            ZoneId timeZone = ZoneId.of("America/Los_Angeles");

            String createdBy = result.getString("Created_By");
            LocalDateTime createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
            String lastUpdateBy = result.getString("Last_Updated_By");
            LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();

            Division division = new Division(ID, countryID, name, timeZone, createdBy, createDateTime, lastUpdateBy, lastUpdate);
            country.addDivision(division);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public static void loadCustomers(Connection connection) throws SQLException {
        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();

        String selectStatement = "SELECT * " +
                                 "FROM customers, first_level_divisions, countries " +
                                 "WHERE customers.Division_ID = first_level_divisions.Division_ID and first_level_divisions.COUNTRY_ID = countries.Country_ID";
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

                Data.addCustomer(customer);

            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    private static void getCreate_UpdateInfo (ResultSet result, String createdBy, LocalDateTime createDateTime, String lastUpdateBy, LocalDateTime lastUpdate) throws SQLException {
        createdBy = result.getString("Created_By");
        createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
        lastUpdateBy = result.getString("Last_Updated_By");
        lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
    }

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

                String createdBy = result.getString("Created_By");
                LocalDateTime createDateTime = result.getTimestamp("Create_Date").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();

                User user = new User(userID, username, password, createdBy, createDateTime, lastUpdateBy, lastUpdate);
                Data.addUser(user);
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

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
                    //read data from database and
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

                    //create new appointment and add it to the myAppointments list for the customer
                    Appointment appointment = new Appointment(appointmentID, customerID, contactID, userID, title, description, location, type, startTime, endTime, createdBy, createDateTime, lastUpdateBy, lastUpdate);
                    customer.setAppointment(appointment);
                }
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
    }

    public static void loadAll(Connection connection) throws SQLException {
        loadCountries(connection);
        loadCustomers(connection);
        loadContacts(connection);
        loadUsers(connection);
        loadAppointments(connection);
    }
}

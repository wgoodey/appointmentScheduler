package model.database;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInsertData {

    public static void insertCountry(Connection connection, Country country) throws SQLException {

        String insertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By) " +
                                 "VALUES (?,?,?,?)";

        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String countryName = country.getName();
        //FIXME get user from login
        String created_By = Data.getCurrentUser().getUsername();
        String last_Updated_By = Data.getCurrentUser().getUsername();

        //key-value mapping
        preparedStatement.setString(1, countryName);
        preparedStatement.setString(2, created_By);
        preparedStatement.setString(3, last_Updated_By);

        //execute PreparedStatement
        preparedStatement.execute();

    }

    public static void insertDivision(Connection connection, Division division) throws SQLException {
        String insertStatement = "INSERT INTO first_level_divisions(Division, Created_By, Last_Updated_By, COUNTRY_ID) " +
                "VALUES (?,?,?,?)";

        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String divisionName = division.getName();
        //FIXME get user from login
        String created_By = Data.getCurrentUser().getUsername();
        String last_Updated_By = Data.getCurrentUser().getUsername();
        int countryID = division.getCountryID();

        //key-value mapping
        preparedStatement.setString(1, divisionName);
        preparedStatement.setString(2, created_By);
        preparedStatement.setString(3, last_Updated_By);
        preparedStatement.setString(4, String.valueOf(countryID));

        //execute prepared statement
        preparedStatement.execute();

    }

    public static void insertCustomer(Connection connection, Customer customer) throws SQLException {
        String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) " +
                                 "VALUES (?,?,?,?,?,?,?)";

        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String customerName = customer.getName();
        String address = customer.getAddress();
        String postal = customer.getPostalCode();
        String phone = customer.getPhone();
        //FIXME get user from login
        String created_By = Data.getCurrentUser().getUsername();
        String last_Updated_By = Data.getCurrentUser().getUsername();
        String divisionID = String.valueOf(Data.getDivisionID(customer.getCountry(), customer.getDivision()));

        //key-value mapping
        preparedStatement.setString(1, customerName);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, postal);
        preparedStatement.setString(4, phone);
        preparedStatement.setString(5, created_By);
        preparedStatement.setString(6, last_Updated_By);
        preparedStatement.setString(7, divisionID);

        //execute prepared statement
        preparedStatement.execute();
    }

    public static void insertAppointment(Connection connection, Appointment appointment) throws SQLException {
        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                                 "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        String start = DBQuery.getSQLFormattedDateTime(appointment.getStartTime());
        String end = DBQuery.getSQLFormattedDateTime(appointment.getEndTime());
        String created_By = appointment.getCreatedBy();
        String last_Updated_By = appointment.getLastUpdateBy();
        String customerID = String.valueOf(appointment.getCustomerID());
        String userID = String.valueOf(appointment.getUserID());
        String contactID = String.valueOf(appointment.getContactID());

        //key-value mapping
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setString(5, start);
        preparedStatement.setString(6, end);
        preparedStatement.setString(7, created_By);
        preparedStatement.setString(8, last_Updated_By);
        preparedStatement.setString(9, customerID);
        preparedStatement.setString(10, userID);
        preparedStatement.setString(11, contactID);

        preparedStatement.execute();


    }


}

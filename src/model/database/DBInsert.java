package model.database;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInsert {

    public static boolean insertCountry(Connection connection, Country country) throws SQLException {

        String insertStatement = "INSERT INTO countries(Country, Created_By, Last_Updated_By) " +
                                 "VALUES (?,?,?)";

        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String countryName = country.getName();
        String created_By = Data.getCurrentUser().getUsername();
        String last_Updated_By = Data.getCurrentUser().getUsername();

        //key-value mapping
        preparedStatement.setString(1, countryName);
        preparedStatement.setString(2, created_By);
        preparedStatement.setString(3, last_Updated_By);

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

    public static boolean insertDivision(Connection connection, Division division) throws SQLException {
        String insertStatement = "INSERT INTO first_level_divisions(Division, Created_By, Last_Updated_By, COUNTRY_ID) " +
                                 "VALUES (?,?,?,?)";

        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String divisionName = division.getName();
        String created_By = Data.getCurrentUser().getUsername();
        String last_Updated_By = Data.getCurrentUser().getUsername();
        int countryID = division.getCountryID();

        //key-value mapping
        preparedStatement.setString(1, divisionName);
        preparedStatement.setString(2, created_By);
        preparedStatement.setString(3, last_Updated_By);
        preparedStatement.setString(4, String.valueOf(countryID));

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

    public static boolean insertCustomer(Connection connection, Customer customer) throws SQLException {
        String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) " +
                                 "VALUES (?,?,?,?,?,?,?)";

        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        //record data
        String customerName = customer.getName();
        String address = customer.getAddress();
        String postal = customer.getPostalCode();
        String phone = customer.getPhone();
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

    public static boolean insertAppointment(Connection connection, Appointment appointment) throws SQLException {
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
        String created_By = Data.getCurrentUser().getUsername();
        String last_Updated_By = Data.getCurrentUser().getUsername();
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
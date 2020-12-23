package com.whitneygoodey.appointmentScheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.database.DBConnection;
import model.database.DBLoadData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Whitney Goodey
 * @version 1.0
 * <p>
 *
 */
public class Main extends Application {

    @Override
    public void init() throws Exception {
        //test data
//        Lists.addCustomer(new Customer(1,"Whitney","9912 SW Conestoga Drive APT 217","Oregon","97008","USA",
//                "503-432-5865"));
//        Lists.addCustomer(new Customer(2,"Name","address","Wales","post","UK",
//                "phone"));
//
//        ZoneId zoneId = ZoneId.of("America/Los_Angeles");
//        ZonedDateTime startTime = ZonedDateTime.of(LocalDateTime.now(), zoneId);
//        ZonedDateTime endTime = startTime.plusSeconds(3600);
//
//
//        Lists.getAllCustomers().get(0).setAppointment(new Appointment(1, 1, "title", "description", startTime, endTime));
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getAppointmentID());
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getCustomerID());
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getTitle());
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getDescription());
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getStart());
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getEnd());
//
//        startTime = ZonedDateTime.of(2020,12,8,17,30,0,0,zoneId);
//        endTime = startTime.plusSeconds(500);
//
//        Lists.getAllCustomers().get(0).setAppointment(new Appointment(2, 1, "Fun Times", "sleeping", startTime, endTime));
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getAppointmentID());
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getCustomerID());
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getTitle());
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getDescription());
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getStart());
//        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getEnd());


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //FIXME set to load loginForm
//        Parent root = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"));
        primaryStage.setTitle("Login");

        //Load resource bundle
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("com.whitneygoodey.appointmentScheduler/Language",
                    Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr")
                    || Locale.getDefault().getLanguage().equals("ja")) {
                primaryStage.setTitle(bundle.getString("Login"));
            }
        } catch (MissingResourceException throwables) {
//            e.printStackTrace();
            System.out.println(throwables.getMessage());
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        //connect to database
        Connection connection = DBConnection.startConnection();

        DBLoadData.loadAll(connection);

        launch(args);

        DBConnection.closeConnection();
    }




}

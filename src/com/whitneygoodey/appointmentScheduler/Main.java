package com.whitneygoodey.appointmentScheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        Country usa = new Country(1, "USA");
        Country uk = new Country(2, "UK");
        Country canada = new Country(3, "Canada");

        usa.addDivision(new Division(36, "Oregon", ZoneId.of("America/Los_Angeles")));
        usa.addDivision(new Division(46, "Washington", ZoneId.of("America/Los_Angeles")));
        uk.addDivision(new Division(104, "Northern Ireland", ZoneId.of("Europe/London")));
        uk.addDivision(new Division(103, "England", ZoneId.of("Europe/London")));
        uk.addDivision(new Division(102, "Wales", ZoneId.of("Europe/London")));
        uk.addDivision(new Division(101, "Scotland", ZoneId.of("Europe/London")));
        canada.addDivision(new Division(71, "Yukon", ZoneId.of("America/Dawson")));
        canada.addDivision(new Division(68, "Québec", ZoneId.of("America/Dawson")));
        Lists.addCountry(usa);
        Lists.addCountry(uk);
        Lists.addCountry(canada);

        Lists.addCustomer(new Customer(1,"Whitney","9912 SW Conestoga Drive APT 217","Oregon","97008","USA",
                "503-432-5865"));
        Lists.addCustomer(new Customer(2,"Name","address","Wales","post","UK",
                "phone"));

        ZoneId zoneId = ZoneId.of("America/Los_Angeles");
        ZonedDateTime startTime = ZonedDateTime.of(LocalDateTime.now(), zoneId);
        ZonedDateTime endTime = startTime.plusSeconds(3600);


        Lists.getAllCustomers().get(0).setAppointment(new Appointment(1, 1, "title", "description", startTime, endTime));
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getAppointmentID());
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getCustomerID());
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getTitle());
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getDescription());
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getStart());
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(0).getEnd());

        startTime = ZonedDateTime.of(2020,12,8,17,30,0,0,zoneId);
        endTime = startTime.plusSeconds(500);

        Lists.getAllCustomers().get(0).setAppointment(new Appointment(2, 1, "Fun Times", "sleeping", startTime, endTime));
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getAppointmentID());
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getCustomerID());
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getTitle());
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getDescription());
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getStart());
        System.out.println(Lists.getAllCustomers().get(0).getMyAppointments().get(1).getEnd());


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

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
        } catch (MissingResourceException e) {
//            e.printStackTrace();
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package com.whitneygoodey.appointmentScheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import model.Lists;

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
        Country france = new Country(2, "France");

        usa.addDivision(new Division(1, "Oregon"));
        usa.addDivision(new Division(2, "Washington"));
        france.addDivision(new Division(1, "Nice"));
        france.addDivision(new Division(2, "Toulouse"));
        Lists.addCountry(usa);
        Lists.addCountry(france);

        Lists.addCustomer(new Customer(1,"Whitney","9912 SW Conestoga Drive APT 217","Oregon","97008","USA",
                "503-432-5865"));
        Lists.addCustomer(new Customer(2,"Name","address","Nice","post","France",
                "phone"));

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

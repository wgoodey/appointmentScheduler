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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Lists.addCustomer(new Customer(1,"d","d","12","32","ds",
                "503-432-5865", "","1",LocalDate.now(), LocalDateTime.now()));

        Country usa = new Country(1, "USA");
        Country france = new Country(2, "France");

        usa.addDivision(new Division(1, "Oregon"));
        usa.addDivision(new Division(2, "Washington"));
        france.addDivision(new Division(1, "Nice"));
        france.addDivision(new Division(2, "Toulouse"));
//        Lists.addCountry(usa);
//        Lists.addCountry(france);


//        ObservableList<Division> divisions = france.getDivisions();
        for (int i = 0; i < usa.getDivisions().size(); i++) {
            System.out.println(usa.getDivisions().get(i).getName());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
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

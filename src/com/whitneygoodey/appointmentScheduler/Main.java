package com.whitneygoodey.appointmentScheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.database.DBConnection;
import model.database.DBLoad;

import java.sql.SQLException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The Appointment Scheduler application allows the user to manage a database of customers and their associated appointments. Customer and appointment data in the application are synchronized with the user's database. The application is able to generate various reports related to customers and appointments.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));

//        //directly load different windows for testing purposes
//        Parent root = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/view/reportWindow.fxml"));
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
        DBConnection.startConnection();

        DBLoad.loadAll(DBConnection.getConnection());


//        Data.setCurrentUser(Data.getAllUsers().get(0));

        launch(args);

        DBConnection.closeConnection();

    }

}
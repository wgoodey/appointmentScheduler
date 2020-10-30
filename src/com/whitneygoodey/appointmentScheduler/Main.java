package com.whitneygoodey.appointmentScheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
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

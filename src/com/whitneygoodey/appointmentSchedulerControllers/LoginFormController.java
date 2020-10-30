package com.whitneygoodey.appointmentSchedulerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LoginFormController {

    @FXML
    Label badLoginLabel;
    @FXML
    Label regionLabel;
    @FXML
    Label usernameLabel;
    @FXML
    Label passwordLabel;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton;

    Boolean credentialMatch = false;

    /**
     * Sets region information and
     */
    public void initialize() {
        //Load resource bundle
        ResourceBundle bundle;
        Locale locale = Locale.getDefault();

        try {
            bundle = ResourceBundle.getBundle("com.whitneygoodey.appointmentScheduler/Language",
                    Locale.getDefault());
            if(locale.getLanguage().equals("fr") || locale.getLanguage().equals("ja")) {
                loginButton.setText(bundle.getString("Login"));
                usernameLabel.setText(bundle.getString("Username") + ":");
                passwordLabel.setText(bundle.getString("Password") + ":");
                badLoginLabel.setText(bundle.getString("badLogin"));
                regionLabel.setText(locale.getDisplayCountry() + ", " + locale.getDisplayLanguage());
            }
        } catch (MissingResourceException e) {
//            e.printStackTrace();
            regionLabel.setText(locale.getDisplayCountry() + ", English");
        }
    }

    /**
     * Check login credentials.
     */
    public void login() {

        //get username and password
        String username = usernameField.getText().toLowerCase();
        String password = passwordField.getText();
        //trim to 30 characters to prevent buffer overflow
        username = username.substring(0, Math.min(username.length(), 30));
        password = password.substring(0, Math.min(password.length(), 30));

        //TODO write code to check credentials
        //if username matches User_Name in database check password
        if (username.equals("")) {
            //check password against Password
            if (password.equals("")) {
                credentialMatch = true;
            }
        }


        if (!credentialMatch) {
            badLoginLabel.setOpacity(100.00);
//            passwordField.clear();
        } else {
            openMainWindow();
        }
    }

    /**
     * Opens the application's main window.
     */
    //need to pass user credentials for this? I don't think so.
    public void openMainWindow() {
        System.out.println("Welcome to the city of baby sandwiches!");
    }
}
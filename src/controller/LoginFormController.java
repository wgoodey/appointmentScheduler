package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LoginFormController {

    Stage stage;
    Parent scene;

    @FXML
    private Label badLoginLabel;
    @FXML
    private Label regionLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    Boolean credentialMatch = false;

    /**
     * Sets region information and translates text.
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
     * Checks login credentials and opens the application's main window.
     */
    public void login(ActionEvent event) throws IOException {

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
            System.out.println("Welcome to the city of baby sandwiches!");

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.close();
            scene = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Scheduler");
            stage.show();
        }
    }

}
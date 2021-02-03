package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Data;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The LoginFormController class manages the user login function.
 */
public class LoginFormController {

    /**
     * Label for failed login.
     */
    @FXML
    private Label badLoginLabel;
    /**
     * Label for the region of the user's system.
     */
    @FXML
    private Label regionLabel;
    /**
     * Label for username field.
     */
    @FXML
    private Label usernameLabel;
    /**
     * Label for password field.
     */
    @FXML
    private Label passwordLabel;
    /**
     * Text field for username.
     */
    @FXML
    private TextField usernameField;
    /**
     * Text field for password.
     */
    @FXML
    private PasswordField passwordField;
    /**
     * The button the user clicks to login.
     */
    @FXML
    private Button loginButton;

    private Stage stage;
    private Parent scene;
    private Boolean credentialMatch = false;


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
     * Checks login credentials and opens the application's main window if login is successful. If attempted login fails, an error message is printed in the UI.
     * @param event the login button that was clicked.
     * @throws IOException if IO error occurs.
     */
    public void login(ActionEvent event) throws IOException {

        //get username and password
        String username = usernameField.getText().trim().toLowerCase();
        String password = passwordField.getText();
        //trim to 30 characters to prevent buffer overflow
        username = username.substring(0, Math.min(username.length(), 30));
        password = password.substring(0, Math.min(password.length(), 30));


        //iterate through user list
        for (User user : Data.getAllUsers()) {
            if (username.equals(user.getUsername())) {
                //check password
                if (password.equals(user.getPassword())) {
                    credentialMatch = true;
                    Data.setCurrentUser(user);
                    break;
                }
            }
        }

        //file printing setup

        if (credentialMatch) {
            //record login activity
            logActivity(username, credentialMatch);

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.close();
            scene = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Appointment Scheduler" +
                    "");
            stage.show();

        } else {

            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Failed Login");
            dialog.setContentText("You failed to login.");
            ButtonType okay = new ButtonType("Okay");
            dialog.getDialogPane().getButtonTypes().add(okay);
            dialog.showAndWait();

            badLoginLabel.setOpacity(100.00);
            passwordField.clear();
            passwordField.requestFocus();

            //record login activity
            logActivity(username, credentialMatch);
        }
    }

    /**
     * Records login attempts to a text file.
     * @param username the username entered in the form for for a login attempt.
     * @param success true if user has successfully logged in or false if not.
     * @throws IOException if IO error occurs.
     */
    private void logActivity(String username, boolean success) throws IOException {
        //file printing variables
        FileWriter fw = new FileWriter("login_activity.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (success) {
            pw.append("Successful login with username " + username + " at " + LocalDateTime.now().format(formatter) + '\n');
            System.out.println("Successful login with username " + username + " at " + LocalDateTime.now());

        } else {
            pw.append("Failed login with username " + username + " at " + LocalDateTime.now().format(formatter) + '\n');
            System.out.println("Failed login with username " + username + " at " + LocalDateTime.now());
        }
        pw.close();
    }

}
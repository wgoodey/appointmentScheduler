package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Data;
import model.database.DBConnection;
import model.database.DBInsert;
import model.database.DBSelect;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;

public class AppointmentFormController {

    @FXML
    private TextField textAppointmentID;
    @FXML
    private TextField textCustomerID;
    @FXML
    private ComboBox<String> comboCustomer;
    @FXML
    private ComboBox<String> comboContact;
    @FXML
    private TextField textTitle;
    @FXML
    private TextArea textDescription;
    @FXML
    private TextField textType;
    @FXML
    private TextField textLocation;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> comboStart;
    @FXML
    private ComboBox<String> comboEnd;
    @FXML
    private RadioButton radioStartPM;
    @FXML
    private RadioButton radioEndPM;


    public void initialize() {
        //set customer combobox
        ObservableList<String> customers = FXCollections.observableArrayList();
        for (Customer customer : Data.getAllCustomers()) {
            customers.add(customer.getName());
        }
        Collections.sort(customers);
        comboCustomer.setItems(customers);
        comboCustomer.getItems().addAll();

        //set contact combobox
        ObservableList<String> contacts = FXCollections.observableArrayList();
        for (Contact contact : Data.getAllContacts()) {
            contacts.add(contact.getName());
        }
        Collections.sort(contacts);
        comboContact.setItems(contacts);
        comboContact.getItems().addAll();


//        //set time comboBoxes
//        ObservableList<String> times = FXCollections.observableArrayList();
//
//        int hour = 1;
//        String AM_PM = ":00 AM";
//
//        String string = "12:00 AM";
//        times.add(string);
//
//        for (int i = 0; i < 23; i++) {
//            if (hour == 12) {
//                AM_PM = ":00 PM";
//                string = hour + AM_PM;
//                times.add(string);
//                hour = 1;
//                continue;
//            }
//            string = (hour + AM_PM);
//            times.add(string);
//            hour++;
//        }

        //set time comboBoxes
        ObservableList<String> hours = FXCollections.observableArrayList();

        for (int i = 1; i <= 12; i++) {
            String string;
            if (i < 10) {
                string = "0" + i + ":00";
            } else {
                string = i + ":00";
            }
            hours.add(string);
        }

        comboStart.setItems(hours);
        comboStart.getItems().addAll();
        comboStart.setEditable(true);

        comboEnd.setItems(hours);
        comboEnd.getItems().addAll();
        comboEnd.setEditable(true);

    }

    public void initialize(Appointment appointment) {
        String customerName = "";
        for (Customer customer : Data.getAllCustomers()) {
            if (customer.getCustomerID() == appointment.getCustomerID()) {
                customerName = customer.getName();
                break;
            }
        }

        String contactName = "";
        for (Contact contact : Data.getAllContacts()) {
            if (contact.getContactID() == appointment.getContactID()) {
                contactName = contact.getName();
            }
        }


        textAppointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        textCustomerID.setText(String.valueOf(appointment.getCustomerID()));
        comboCustomer.getSelectionModel().select(customerName);
        textTitle.setText(appointment.getTitle());
        textType.setText(appointment.getType());
        textLocation.setText(appointment.getLocation());
        comboContact.getSelectionModel().select(contactName);
        textDescription.setText(appointment.getDescription());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");

        LocalDate dateTime = LocalDate.parse(appointment.getStartTime().format(dateFormatter));
        datePicker.setValue(dateTime);
        comboStart.setValue(appointment.getStartTime().format(timeFormatter));
        int hour = Integer.parseInt(appointment.getStartTime().format(hourFormatter));
        if (hour > 11) {
            radioStartPM.setSelected(true);
        }

        comboEnd.setValue(appointment.getEndTime().format(timeFormatter));
        hour = Integer.parseInt(appointment.getEndTime().format(hourFormatter));
        if (hour > 11) {
            radioEndPM.setSelected(true);
        }

    }

    @FXML
    private void save(ActionEvent click) throws SQLException {
        //TODO checks for current data
        int appointmentID = -1;
        int customerID = Integer.parseInt(textCustomerID.getText());
        Customer customer = Data.getAllCustomers().get(Data.getCustomerIndex(customerID));
        String contactName = comboContact.getValue();

        int contactID = -1;
        for(Contact contact : Data.getAllContacts()) {
            if (contactName.equals(contact.getName())) {
                contactID = contact.getContactID();
                break;
            }
        }

        int userID = Data.getCurrentUser().getUserID();
        String title = textTitle.getText();
        String description = textDescription.getText();
        String location = textLocation.getText();
        String type = textType.getText();

        String date = String.valueOf(datePicker.getValue());
        String startHour = String.valueOf(comboStart.getSelectionModel().getSelectedItem());
        LocalDateTime startDateTime = convertToDateTime(date, startHour, radioStartPM.isSelected());
        String endHour = String.valueOf(comboEnd.getSelectionModel().getSelectedItem());
        LocalDateTime endDateTime = convertToDateTime(date, endHour, radioEndPM.isSelected());

        //create new Appointment
        Appointment newAppointment = new Appointment(appointmentID, customerID, contactID, userID, title, description, location, type, startDateTime, endDateTime);
        //insert new appointment into database if no matches are found
        DBInsert.insertAppointment(DBConnection.getConnection(), newAppointment);

        //pull ID from most recent DB entry
        newAppointment.setAppointmentID(DBSelect.getNewAppointmentID(DBConnection.getConnection(), newAppointment));

        //add new appointment to appointments list
        customer.addAppointment(newAppointment);


        //close window
        ((Stage)(((Button)click.getSource()).getScene().getWindow())).close();
    }

    public void cancel(ActionEvent click) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modify Appointment");
        alert.setHeaderText("Discard modifications to appointment?");
        alert.setContentText("Press Okay to discard changes or Cancel to continue.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            //close window
            ((Stage)(((Button)click.getSource()).getScene().getWindow())).close();
        }
    }

    /**
     * Sets the customerID textField according to customer selected in the comboBox.
     */
    @FXML
    private void setCustomerID() {
        String customerName = comboCustomer.getSelectionModel().getSelectedItem();
        for (Customer customer : Data.getAllCustomers()) {
            if (customerName.equals(customer.getName())) {
                textCustomerID.setText(String.valueOf(customer.getCustomerID()));
                break;
            }
        }
    }


    private LocalDateTime convertToDateTime(String date, String time, boolean pm) {
        //add seconds
        time += ":00";

        //if hour is 12:00, change to 00:00 to match 24 hour format for SQL
        if (time.startsWith("12")) {
            time = time.replaceFirst("12", "00");
        }
        String startTime = date + " " + time;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, dateTimeFormatter);
        if (pm) {
            startDateTime = startDateTime.plusHours(12);
        }
        return startDateTime;
    }
}
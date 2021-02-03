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
import model.database.DBUpdate;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The AppointmentFormController class manages the creation and modification of appointments. It checks that all fields are meet time and type criteria and prompts the user if criteria are not met
 */
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
    @FXML
    private Label labelBusinessHours;

    private final ZonedDateTime localStart = Data.getBusinessStart().withZoneSameInstant(Data.getUserZoneID());
    private final ZonedDateTime localEnd = Data.getBusinessEnd().withZoneSameInstant(Data.getUserZoneID());

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm");
    DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("HH");
    DateTimeFormatter hoursFormat = DateTimeFormatter.ofPattern("h:mm a");
    DateTimeFormatter hoursAndZoneFormat = DateTimeFormatter.ofPattern("h:mm a z");

    /**
     * Sets the comboboxes for customers, contacts, and appointment times in the appointment form and prints the local business hours into the interface.
     */
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

        labelBusinessHours.setText("Business Hours: " + localStart.format(hoursFormat) + " - " + localEnd.format(hoursAndZoneFormat));

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

    /**
     * Populates the fields with the data for the selected appointment.
     * @param appointment
     */
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


        datePicker.setValue(LocalDate.parse(appointment.getStartTime().format(dateFormat)));

        comboStart.setValue(appointment.getStartTime().format(timeFormat));
        int hour = Integer.parseInt(appointment.getStartTime().format(hourFormat));
        if (hour > 11) {
            radioStartPM.setSelected(true);
        }

        comboEnd.setValue(appointment.getEndTime().format(timeFormat));
        hour = Integer.parseInt(appointment.getEndTime().format(hourFormat));
        if (hour > 11) {
            radioEndPM.setSelected(true);
        }

    }

    /**
     * Saves the current data entered in the form to the database and adds the appointment to the appointment list.
     * @param click the button in the UI that was clicked.
     */
    @FXML
    private void save(ActionEvent click) {
        //collect data for appointment
        final int NO_ID = -1;

        int appointmentID = NO_ID;
        int customerID;
        int contactID = NO_ID;
        int userID;
        String title;
        String description;
        String location;
        String type;
        ZonedDateTime startTime;
        ZonedDateTime endTime;
        ZonedDateTime businessStart;
        ZonedDateTime businessEnd;
        userID = Data.getCurrentUser().getUserID();

        try {
            //get appointmentID and customerID
            if (!textAppointmentID.getText().isEmpty()) {
                appointmentID = Integer.parseInt(textAppointmentID.getText());
            }

            //get customerID
            customerID = Integer.parseInt(textCustomerID.getText());
            String contactName = comboContact.getValue();

            //get contactID
            for(Contact contact : Data.getAllContacts()) {
                if (contactName.equals(contact.getName())) {
                    contactID = contact.getContactID();
                    break;
                }
            }

            //get String data
            title = textTitle.getText();
            description = textDescription.getText();
            location = textLocation.getText();
            type = textType.getText();

            //get date and times
            String date = String.valueOf(datePicker.getValue());
            String startHour = String.valueOf(comboStart.getSelectionModel().getSelectedItem());
            startTime = convertToZonedDateTime(date, startHour, radioStartPM.isSelected());
            String endHour = String.valueOf(comboEnd.getSelectionModel().getSelectedItem());
            endTime = convertToZonedDateTime(date, endHour, radioEndPM.isSelected());

            //build business hours
            businessStart = startTime.withZoneSameInstant(Data.getBusinessZone()).withHour(8).withMinute(0).withSecond(0);
            businessEnd = endTime.withZoneSameInstant(Data.getBusinessZone()).withHour(22).withMinute(0).withSecond(0);

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No customer selected.");
            alert.setContentText("Please select a customer.");
            alert.showAndWait();
            return;

        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid date and time entered");
            alert.setContentText("Please check dates and times and try again.");
            alert.showAndWait();
            return;

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing contact");
            alert.setContentText("Please select a contact for this appointment.");
            alert.showAndWait();
            return;
        }


        try {
            //check for empty fields
            if (title.isBlank() || type.isBlank() || location.isBlank() || description.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing data");
                alert.setHeaderText("Unable to save appointment.");
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
                return;

                //check that start is before end and times
            } else if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid times entered");
                alert.setHeaderText("Start time must be before end time.");
                alert.showAndWait();
                return;

                //check that times are within business hours
            } else if (startTime.withZoneSameInstant(Data.getBusinessZone()).isBefore(businessStart) ||
                       endTime.withZoneSameInstant(Data.getBusinessZone()).isAfter(businessEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Times entered");
                alert.setHeaderText("Appointment time must be within headquarters business hours.");
                alert.setContentText("Business hours in local time are: " + localStart.format(hoursFormat) + " - " + localEnd.format(hoursAndZoneFormat));
                alert.showAndWait();
                return;

            }

            //check that times do not overlap with other appointments for the customer
            for (Appointment a : Data.getAllAppointments()) {
                //skip current appointment
                if (a.getAppointmentID() == appointmentID) {
                    continue;
                }

                if (a.getCustomerID() == customerID) {
                    //make sure appointment is not scheduled in the past
                    if (startTime.isBefore(ZonedDateTime.now())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Times entered");
                        alert.setHeaderText("Appointment cannot be scheduled in the past.");
                        alert.setContentText("Please select different times for this appointment.");
                        alert.showAndWait();
                        return;
                    }

                    if ( ((startTime.isEqual(a.getStartTime()) || endTime.isEqual(a.getEndTime()))     || //matching start or end times
                         ((startTime.isAfter(a.getStartTime()) && startTime.isBefore(a.getEndTime()))) || //start overlaps
                         ((endTime.isAfter(a.getStartTime()) && endTime.isBefore(a.getEndTime())))     || //end overlaps
                         ((startTime.isBefore(a.getStartTime()) && endTime.isAfter(a.getEndTime())))) ) { //complete overlap

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Times entered");
                        alert.setHeaderText("Appointment times must not overlap with customer's other appointments.");
                        alert.setContentText("Please select different times for this appointment.");
                        alert.showAndWait();
                        return;
                    }

                }
            }


            //save data into appointment
            Appointment newAppointment = new Appointment(appointmentID, customerID, contactID, userID, title, description, location, type, startTime, endTime);

            //if modifying existing appointment
            if (appointmentID != NO_ID) {
                //update in database and allAppointments list
                if (DBUpdate.updateAppointment(DBConnection.getConnection(), newAppointment)) {
                    Data.updateAppointment(Data.getAppointmentIndex(appointmentID), newAppointment);
                }

            } else { //if new appointment (appointmentID = NO_ID)
                //insert new appointment into database if no matches are found
                DBInsert.insertAppointment(DBConnection.getConnection(), newAppointment);

                //pull ID from most recent DB entry
                newAppointment.setAppointmentID(DBSelect.getNewAppointmentID(DBConnection.getConnection(), newAppointment));

                //add new appointment to appointments list
                Data.addAppointment(newAppointment);
            }



        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }

        //close window
        ((Stage)(((Button)click.getSource()).getScene().getWindow())).close();
    }

    /**
     * Displays a confirmation alert. Discards entered data if confirmed or returns to the form if not.
     * @param click the button in the UI that was clicked.
     */
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

    /**
     * Converts date and time Strings into a ZonedDateTime.
     * @param date the date in String format.
     * @param time the time in String format.
     * @param pm true if PM, false if AM.
     * @return a zonedDateTime in the system's zoneID.
     */
    private ZonedDateTime convertToZonedDateTime(String date, String time, boolean pm) {
        //add seconds
        time += ":00";

        //if hour is 12:00, change to 00:00 to match 24 hour format for SQL
        if (time.startsWith("12")) {
            time = time.replaceFirst("12", "00");
        }
        String startTime = date + " " + time;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localTime = LocalDateTime.parse(startTime, dateTimeFormatter);
        ZonedDateTime zoned = ZonedDateTime.of(localTime, Data.getUserZoneID());

        if (pm) {
            zoned = zoned.plusHours(12);
        }
        return zoned;
    }
}
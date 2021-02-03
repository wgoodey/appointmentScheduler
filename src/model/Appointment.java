package model;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The Appointment class defines the Appointment object.
 */
public class Appointment {

    /**
     * The appointment ID.
     */
    private int appointmentID;
    /**
     * The appointment's contactID.
     */
    private int customerID;
    /**
     * The contact ID of the appointment's contact.
     */
    private int contactID;
    /**
     * The appointment's user ID.
     */
    private int userID;
    /**
     * The appointment's title.
     */
    private String title;
    /**
     * The appointment's description.
     */
    private String description;
    /**
     * The appointment's location.
     */
    private String location;
    /**
     * The appointment's type.
     */
    private String type;
    /**
     * The appointment's start time.
     */
    private ZonedDateTime startTime;
    /**
     * The appointment's end time.
     */
    private ZonedDateTime endTime;

    /**
     * Constructor for the Appointment class.
     * @param appointmentID the ID of the appointment
     * @param customerID the ID of the customer associated with the appointment
     * @param contactID the ID of the contact associated with teh appointment
     * @param userID the ID of the user that created or modified the appointment
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of the appointment
     * @param type the type of appointment
     * @param start the appointment starting time
     * @param end the appointmenet ending time
     */
    public Appointment(int appointmentID, int customerID, int contactID, int userID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end) {

        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.contactID = contactID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = start;
        this.endTime = end;
    }

    /**
     * Get the appointmentID.
     * @return the appointmentID.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Set the appointmentID.
     * @param appointmentID the ID to set.
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Get the customer's ID.
     * @return the customerID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Get the appointment's contactID
     * @return the contactID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Get the appointment's user ID.
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Get the appointment's title.
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the appointment's description.
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the appointment's location.
     * @return the location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get the appointment's type.
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Get the appointment's startTime.
     * @return the startTime.
     */
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    /**
     * Get the appointment's endTime.
     * @return the endTime.
     */
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    /**
     * Get the appointment's startTime in String form for the appointment tableView.
     * @return the startTime as a String;
     */
    public String getStartString() {
        DateTimeFormatter table = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return startTime.format(table);
    }

    /**
     * Get the appointment's endTime in String form for the appointment tableView.
     * @return the endTime as a String.
     */
    public String getEndString() {
        DateTimeFormatter table = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return endTime.format(table);
    }

    /**
     * Get the customer's name.
     * @return the customer's name
     */
    public String getCustomerName() {
        for(Customer customer : Data.getAllCustomers()) {
            if (customer.getCustomerID() == customerID) {
                return customer.getName();
            }
        }
        return null;
    }

    /**
     * Get the contact's name.
     * @return the contact's name
     */
    public String getContactName() {
        for(Contact contact : Data.getAllContacts()) {
            if (contact.getContactID() == contactID) {
                return contact.getName();
            }
        }
        return null;
    }
}
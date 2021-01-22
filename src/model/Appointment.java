package model;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private int appointmentID;
    private int customerID;
    private int contactID;
    private int userID;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

    /**
     * Constructor for the Appointment class.
     * @param appointmentID
     * @param customerID
     * @param contactID
     * @param userID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
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
     * @return
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
     * @param id the id of the customer.
     * @return
     */
    public String getCustomerName(int id) {
        for(Customer customer : Data.getAllCustomers()) {
            if (getCustomerID() == id) {
                return customer.getName();
            }
        }
        return null;
    }
}
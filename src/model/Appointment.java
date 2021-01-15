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

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getContactID() {
        return contactID;
    }

    public int getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public String getStartString() {
        DateTimeFormatter table = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return startTime.format(table);
    }

    public String getEndString() {
        DateTimeFormatter table = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return endTime.format(table);
    }

    public String getCustomerName(int id) {
        for(Customer customer : Data.getAllCustomers()) {
            if (getCustomerID() == id) {
                return customer.getName();
            }
        }
        return null;
    }
}
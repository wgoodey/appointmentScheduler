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
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //use ZonedDateTime instead of LocalDateTime?
    private ZoneId timeZone;

    public Appointment(int appointmentID, int customerID, int contactID, int userID, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime) {

        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.contactID = contactID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //TODO remove once database auto-gen is working
    private void createID() {
        //temporarily generate random ID between 10 and 100
        customerID = (int)(Math.random() * (100 - 10 + 1) + 10);
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

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getStart() {
        DateTimeFormatter table = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return startTime.format(table);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getEnd() {
        DateTimeFormatter table = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return endTime.format(table);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getCustomerName(int id) {
        for(Customer customer : Data.getAllCustomers()) {
            if (getCustomerID() == id) {
                return customer.getName();
            }
        }
        return null;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }
}
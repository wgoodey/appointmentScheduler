package model;

import java.time.*;

public class Appointment {

    private int appointmentID;
    private int customerID;
    private int contactID;
    private int userID;
    private String title;
    private String description;
    private String location;
    private String type;
    //Mysql DATETIME type
    private LocalDateTime startTime;
    //Mysql DATETIME type
    private LocalDateTime endTime;
    private String createdBy;
    private LocalDateTime createDateTime;
    private String lastUpdateBy;
    private LocalDateTime lastUpdate;

    //use ZonedDateTime instead of LocalDateTime?
    private ZoneId timeZone;

    public Appointment(int appointmentID, int customerID, int contactID, int userID, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, String createdBy, LocalDateTime createDateTime, String lastUpdateBy, LocalDateTime lastUpdate) {

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
        this.createdBy = createdBy;
        this.createDateTime = createDateTime;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdate = lastUpdate;
    }

//    public Appointment(int appointmentID, int customerID, String title, String description, ZonedDateTime startTime, ZonedDateTime endTime) {
//        Customer customer = Lists.getAllCustomers().get(Lists.getCustomerIndex(customerID));
//
//        Country country = Lists.getCountry(customer.getCountry());
//        Division division = country.getDivision(customer.getDivision());
//        ZoneId timeZone = division.getTimeZone();
//
////        String zoneName = country.getDivision(customer.getCountry()).getTimeZone();
//
//        this.appointmentID = appointmentID;
//        this.customerID = customerID;
//        this.title = title;
//        this.description = description;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        //copy timeZone from customer->country->division instead of passing it?
//        this.timeZone = timeZone;
//    }

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

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }
}
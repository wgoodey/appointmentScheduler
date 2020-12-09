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
    private String createdBy;
    private String lastUpdatedBy;
    //Mysql DATETIME type
    private ZonedDateTime start;
    //Mysql DATETIME type
    private ZonedDateTime end;
    private ZoneId timeZone;

    //TODO finalize types here
    //Mysql DATETIME type
    private LocalDateTime createDate;
    //MYSQL TIMESTAMP
    private LocalTime lastUpdate;


//    public Appointment(int appointmentID, int customerID, int contactID, int userID, String title, String description, String location, String type, String createdBy, String lastUpdatedBy, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, LocalTime lastUpdate) {
//        this.appointmentID = appointmentID;
//        this.customerID = customerID;
//        this.contactID = contactID;
//        this.userID = userID;
//        this.title = title;
//        this.description = description;
//        this.location = location;
//        this.type = type;
//        this.createdBy = createdBy;
//        this.lastUpdatedBy = lastUpdatedBy;
//        this.start = start;
//        this.end = end;
//        this.createDate = createDate;
//        this.lastUpdate = lastUpdate;
//    }

    public Appointment(int appointmentID, int customerID, String title, String description, ZonedDateTime start, ZonedDateTime end) {
        Customer customer = Lists.getAllCustomers().get(Lists.getCustomerIndex(customerID));

        Country country = Lists.getCountry(customer.getCountry());
        Division division = country.getDivision(customer.getDivision());
        ZoneId timeZone = division.getTimeZone();

//        String zoneName = country.getDivision(customer.getCountry()).getTimeZone();

        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        //copy timeZone from customer->country->division instead of passing it?
        this.timeZone = timeZone;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
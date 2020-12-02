package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Customer {

    private int customerID;
    private String name;
    private String country;
    private String address;
    private String postalCode;
    private String division;
    private String phone;
    private String createdBy;
    private String lastUpdatedBy;
    //Mysql DATETIME
    private LocalDate createDate;
    //MYSQL TIMESTAMP
    private LocalDateTime lastUpdate;
    private ObservableList<Appointment> myAppointments = FXCollections.observableArrayList();


    public Customer(int customerID, String name, String country, String address, String postalCode, String division, String phone, String createdBy, String lastUpdatedBy, LocalDate createDate, LocalDateTime lastUpdate) {
        this.customerID = customerID;
        this.name = name;
        this.country = country;
        this.address = address;
        this.postalCode = postalCode;
        this.division = division;
        this.phone = phone;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public ObservableList<Appointment> getMyAppointments() {
        return myAppointments;
    }

    private void setAppointment(Appointment appointment) {
        myAppointments.add(appointment);
    }

    private void cancelAppointment(Appointment appointment) {
        myAppointments.remove(appointment);
    }

}

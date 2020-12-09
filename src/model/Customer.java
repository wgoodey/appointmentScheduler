package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {

    private int customerID;
    private String name;
    private String country;
    private String address;
    private String postalCode;
    private String division;
    private String phone;
    private String contact;
    private ObservableList<Appointment> myAppointments = FXCollections.observableArrayList();


    public Customer(int customerID, String name, String address, String division, String postalCode, String country, String phone) {
        this.customerID = customerID;
        this.name = name;
        this.country = country;
        this.address = address;
        this.postalCode = postalCode;
        this.division = division;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (customerID != customer.customerID) return false;
        if (!name.equals(customer.name)) return false;
        if (!country.equals(customer.country)) return false;
        if (!address.equals(customer.address)) return false;
        if (!postalCode.equals(customer.postalCode)) return false;
        if (!division.equals(customer.division)) return false;
        if (!phone.equals(customer.phone)) return false;
        return myAppointments.equals(customer.myAppointments);
    }

    @Override
    public int hashCode() {
        int result = customerID;
        result = 31 * result + name.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + postalCode.hashCode();
        result = 31 * result + division.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + myAppointments.hashCode();
        return result;
    }

    public Customer(Customer copy) {
        this.customerID = copy.getCustomerID();
        this.name = copy.getName();
        this.country = copy.getCountry();
        this.address = copy.getAddress();
        this.postalCode = copy.getPostalCode();
        this.division = copy.getDivision();
        this.phone = copy.getPhone();
        this.myAppointments.addAll(copy.getMyAppointments());
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = phone;
    }

    public ObservableList<Appointment> getMyAppointments() {
        return myAppointments;
    }

    public void setAppointment(Appointment appointment) {
        myAppointments.add(appointment);
    }


    /**
     * Deletes an appointment from the allAppointments list.
     * @param selectedAppointment the appointment to delete
     * @return
     */
    public boolean deleteAppointment(Appointment selectedAppointment) {
        for(Appointment appointment : myAppointments) {
            if (appointment.equals(selectedAppointment)) {
                myAppointments.remove(appointment);
                return true;
            }
        }
        return false;
    }

    public void updateAppointment(int index, Appointment appointment) {
        myAppointments.set(index, appointment);
    }

//    /**
//     *
//     * @param id
//     * @return
//     */
//    public static int getAppointmentIndex(int id) {
//        int index;
//        for(index = 0; index< myAppointments().size(); index++) {
//            if(id == myAppointments.get(index).getAppointmentID()) {
//                return index;
//            }
//        }
//        return -1;
//    }

}

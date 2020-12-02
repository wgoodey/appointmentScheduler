package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Lists {

    private static final ObservableList <Customer> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList <Appointment> allAppointments = FXCollections.observableArrayList();
    private static final ObservableList <Country> allCountries = FXCollections.observableArrayList();
//    private static final ObservableList <Division> allDivisions = FXCollections.observableArrayList();


    //TEMPORARY?
    public static void addCountry(Country country) {
        allCountries.add(country);
    }

    /**
     * @return the allCustomers ObservableList
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**
     * @return the allAppointments ObservableList
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     * @return the allCountries ObservableList
     */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    public static Country getCountry (String name) {
        Country match;
        for(int i = 0; i < getAllCountries().size(); i++) {
            if (allCountries.get(i).getName().equals(name)) {
                match = allCountries.get(i);
                return match;
            }
        }
        return null;
    }
    /**
     * @return the allCountries ObservableList
     */
    public static ObservableList<String> getCountryNames() {
        ObservableList<String> countryNames = FXCollections.observableArrayList();

        for (int i = 0; i < getAllCountries().size(); i++) {
            countryNames.add(allCountries.get(i).getName());
        }
        return countryNames;
    }

    //pull divisions from locally stored list
    public static ObservableList<String> getDivisionNames() {
        ObservableList<Division> divisions = FXCollections.observableArrayList(Country.getDivisions());
        ObservableList<String> divisionNames = FXCollections.observableArrayList();

        for (int i = 0; i < divisions.size(); i++) {
            divisionNames.add(divisions.get(i).getName());
        }
        return divisionNames;
    }

    public static ObservableList<String> getCountryDivisionNames(String countryName) {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        System.out.println("Search term: " + countryName);
        //loop through countries list
        for (int i = 0; i < getAllCountries().size(); i++) {
            System.out.println("Country " + i + ": " + getAllCountries().get(i).getName());
            //if countryName matches, copy country. then loop through divisions.
            if (getAllCountries().get(i).getName().equals(countryName)) {
                System.out.println(countryName +" = " + getAllCountries().get(i).getName());
                Country match = getAllCountries().get(i);
                System.out.println("Match = " + match.getName());
                //loop through divisions list and add
                for (int j = 0; j < match.getDivisions().size(); j++) {
                    System.out.println("Division " + j + ": " + match.getDivisions().get(j).getName());
                    divisionNames.add(match.getDivisions().get(j).getName());
                }
            }
        }
        return divisionNames;
    }

    /**
     *
     * @param id
     * @return
     */
    public static int getCustomerIndex(int id) {
        int index;
        for(index = 0; index<Lists.getAllCustomers().size(); index++) {
            if(id == Lists.allCustomers.get(index).getCustomerID()) {
                return index;
            }
        }
        return -1;
    }

    /**
     *
     * @param id
     * @return
     */
    public static int getAppointmentIndex(int id) {
        int index;
        for(index = 0; index<Lists.getAllAppointments().size(); index++) {
            if(id == Lists.allAppointments.get(index).getAppointmentID()) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Deletes a customer from the allCustomers list.
     * @param selectedCustomer the customer to delete
     * @return
     */
    public static boolean deleteCustomer(Customer selectedCustomer) {
        for(Customer customer : allCustomers) {
            if (customer == selectedCustomer) {
                allCustomers.remove(selectedCustomer);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes an appointment from the allAppointments list.
     * @param selectedAppointment the appointment to delete
     * @return
     */
    public static boolean deleteAppointment(Appointment selectedAppointment) {
        for(Appointment appointment : allAppointments) {
            if (appointment == selectedAppointment) {
                allAppointments.remove(appointment);
                return true;
            }
        }
        return false;
    }



    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    public static void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);
    }

    public void updateCustomer(int index, Customer customer) {
        allCustomers.set(index, customer);
    }

    public void updateAppointment(int index, Appointment appointment) {
        allAppointments.set(index, appointment);
    }

}
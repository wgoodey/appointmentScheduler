package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class Lists {

    private static final ObservableList <Customer> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList <Country> allCountries = FXCollections.observableArrayList();
    private static final ObservableList <Contact> allContacts = FXCollections.observableArrayList();
    private static final ObservableList <User> allUsers = FXCollections.observableArrayList();


    public static void addCountry(Country country) {
        allCountries.add(country);
    }

    public static void addContact(Contact contact) {
        allContacts.add(contact);
    }

    public static void addUser(User user) {
        allUsers.add(user);
    }

    /**
     * Deletes a country from the allCountries list.
     * @param selectedCountry the country to delete
     * @return
     */
    public boolean deleteCountry(Country selectedCountry) {
        for(Country appointment : allCountries) {
            if (appointment.equals(selectedCountry)) {
                allCountries.remove(appointment);
                return true;
            }
        }
        return false;
    }

    /**
     * @return the allCustomers ObservableList
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }


    /**
     * @return the allCountries ObservableList
     */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    /**
     * @return the allCountries ObservableList
     */
    public static ObservableList<String> getAllCountryNames() {
        ObservableList<String> countryNames = FXCollections.observableArrayList();

        for (int i = 0; i < getAllCountries().size(); i++) {
            countryNames.add(allCountries.get(i).getName());
        }
        Collections.sort(countryNames);
        return countryNames;
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


    public static int getCountryIndex(String name) {
        int index;
        for(index = 0; index<Lists.getAllCustomers().size(); index++) {
            if(Lists.allCountries.get(index).getName().equals(name)) {
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

    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    public static void updateCustomer(int index, Customer customer) {
        allCustomers.set(index, customer);
    }

}
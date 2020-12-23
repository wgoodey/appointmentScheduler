package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class Data {

    private static User currentUser;
    private static final ObservableList <Customer> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList <Country> allCountries = FXCollections.observableArrayList();
    private static final ObservableList <Contact> allContacts = FXCollections.observableArrayList();
    private static final ObservableList <User> allUsers = FXCollections.observableArrayList();

    /**
     * @return the current user of the program.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current User of the program.
     * @param currentUser the user to set
     */
    public static void setCurrentUser(User currentUser) {
        Data.currentUser = currentUser;
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
     * @return the allUsers ObservableList
     */
    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Adds a country to the allCountries list.
     * @param country the country to add
     */
    public static void addCountry(Country country) {
        allCountries.add(country);
    }

    /**
     * Adds a contact to the allContacts list.
     * @param contact the contact to add
     */
    public static void addContact(Contact contact) {
        allContacts.add(contact);
    }

    /**
     * Adds a user to the allUsers list.
     * @param user the user to add
     */
    public static void addUser(User user) {
        allUsers.add(user);
    }

    /**
     * Deletes a country from the allCountries list.
     * @param selectedCountry the country to delete
     * @return true if the country was deleted or false if not
     */
    public boolean deleteCountry(Country selectedCountry) {
        for(Country country : allCountries) {
            if (country.equals(selectedCountry)) {
                allCountries.remove(country);
                return true;
            }
        }
        return false;
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

    /**
     * Returns a country from the allCountries observableList.
     * @param name the name of the country to look for
     * @return the country or null if not found
     */
    public static Country getCountry(String name) {
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
     * Returns the ID of a Division from the allCountries observableList.
     * @param country the name of the country that the division belongs to
     * @param name the name of the division to look for
     * @return the ID of the division or -1 if not found
     */
    public static int getDivisionID(String country, String name) {
        Country myCountry = getCountry(country);
        System.out.println(myCountry.getName());
        for (Division division : myCountry.getAllDivisions()) {
            if (division.getName().equals(name)) {
                return division.getID();
            }
        }
        return -1;
    }

    /**
     * Returns the index of a customer in the allCustomers observableList.
     * @param id the id of the customer
     * @return the index of the customer or -1 if not found
     */
    public static int getCustomerIndex(int id) {
        int index;
        for(index = 0; index< Data.getAllCustomers().size(); index++) {
            if(id == Data.allCustomers.get(index).getCustomerID()) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Returns the index of a country in the allCountries observableList.
     * @param name the name of the country to look for
     * @return the index of the country if found or -1 if not
     */
    public static int getCountryIndex(String name) {
        int index;
        for(index = 0; index< Data.getAllCustomers().size(); index++) {
            if(Data.allCountries.get(index).getName().equals(name)) {
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
     * Adds a customer to the allCustomers observableList.
     * @param customer the customer to add
     */
    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    /**
     * Updates a customer in the allCustomers observableList.
     * @param index the index of the customer in the allCustomers list
     * @param customer the new customer to replace the old one
     */
    public static void updateCustomer(int index, Customer customer) {
        allCustomers.set(index, customer);
    }

}
package model;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The Customer class defines the Customer object.
 */
public class Customer {

    private int customerID;
    private String name;
    private String country;
    private String address;
    private String postalCode;
    private String division;
    private String phone;


    /**
     * Constructor for the customer class.
     * @param customerID
     * @param name
     * @param address
     * @param division
     * @param postalCode
     * @param country
     * @param phone
     */
    public Customer(int customerID, String name, String address, String division, String postalCode, String country, String phone) {
        this.customerID = customerID;
        this.name = name;
        this.country = country;
        this.address = address;
        this.postalCode = postalCode;
        this.division = division;
        this.phone = phone;
    }

    /**
     * Copy constructor for the customer class.
     * @param copy the customer to copy.
     */
    public Customer(Customer copy) {
        this.customerID = copy.getCustomerID();
        this.name = copy.getName();
        this.country = copy.getCountry();
        this.address = copy.getAddress();
        this.postalCode = copy.getPostalCode();
        this.division = copy.getDivision();
        this.phone = copy.getPhone();
    }

    /**
     * Override of the equals() method for easy customer comparison.
     * @param o the customer to compare.
     * @return true if the customers are the same and false if not.
     */
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
        return phone.equals(customer.phone);
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
        return result;
    }

    /**
     * Get the ID of the customer.
     * @return customerID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Set the ID of the customer.
     * @param customerID the ID to set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Get the name of the customer.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the customer.
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the customer's country.
     * @return country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the customer's country.
     * @param country the name of the country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get the customer's address.
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the customer's address.
     * @param address the address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the customer's postal code.
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Set the customer's postal code.
     * @param postalCode the postal code to set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Get the customer's division.
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set the customer's division.
     * @param division the division name to set.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get the customer's phone number.
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the customer's phone number.
     * @param phone the phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

}

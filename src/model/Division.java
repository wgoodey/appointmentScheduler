package model;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The Division class defines the Division object.
 */
public class Division {

    /**
     * The division's ID.
     */
    int ID;
    /**
     * The division's country ID.
     */
    int countryID;
    /**
     * The division's name.
     */
    String name;

    /**
     * Constructor for the division class.
     * @param ID the ID of the division
     * @param countryID the ID of the country the division is in
     * @param name the name of the division
     */
    public Division(int ID, int countryID, String name) {
        this.ID = ID;
        this.countryID = countryID;
        this.name = name;
    }

    /**
     * Get the division's ID.
     * @return the ID.
     */
    public int getID() {
        return ID;
    }

    /**
     * Set the division's ID.
     * @param ID the ID to set.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Get the division's countryID.
     * @return the countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Set the division's country ID.
     * @param countryID the countryID to set.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Get the name of the division.
     * @return the division's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the division's name.
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}

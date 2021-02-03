package model;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The Contact class defines the Contact object.
 */
public class Contact {
    /**
     * The contact's ID.
     */
    private final int contactID;
    /**
     * The contact's name.
     */
    private final String name;
    /**
     * The contact's email address.
     */
    private final String email;

    /**
     * The constructor for the Contact class
     * @param contactID the ID of the contact
     * @param name the name of the contact
     * @param email the email address of the contact
     */
    public Contact(int contactID, String name, String email) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }

    /**
     * Get the ID for the contact.
     * @return the contactID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Get the name of the contact.
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the email of the contact.
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

}

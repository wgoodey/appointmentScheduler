package model;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The Contact class defines the Contact object.
 */
public class Contact {
    private final int contactID;
    private final String name;
    private final String email;

    /**
     * The constructor for the Contact class
     * @param contactID
     * @param name
     * @param email
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

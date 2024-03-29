package model;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The User class defines the User object.
 */
public class User {

    /**
     * The user's ID.
     */
    private final int userID;
    /**
     * The user's username.
     */
    private final String username;
    /**
     * The user's password.
     */
    private final String password;

    /**
     * Constructor for the user class.
     * @param userID the user's ID
     * @param username the user's username
     * @param password the user's password
     */
    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /**
     * Get the user's ID.
     * @return the user's ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Get the user's username.
     * @return the user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the user's password.
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }
}

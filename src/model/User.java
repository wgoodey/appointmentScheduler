package model;

import java.time.LocalDateTime;

public class User {
    private int userID;
    private String username;
    private String password;
    private String createdBy;
    private LocalDateTime createDateTime;
    private String lastUpdateBy;
    private LocalDateTime lastUpdate;

    public User(int userID, String username, String password, String createdBy, LocalDateTime createDateTime, String lastUpdateBy, LocalDateTime lastUpdate) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.createdBy = createdBy;
        this.createDateTime = createDateTime;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdate = lastUpdate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}

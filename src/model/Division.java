package model;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Division {

    int ID;
    int countryID;
    String name;
    //TODO fix timeZone issue. Right now LA time is hardcoded.
    private ZoneId timeZone;
    private String createdBy;
    private LocalDateTime createDateTime;
    private String lastUpdateBy;
    private LocalDateTime lastUpdate;


    public Division(int ID, int countryID, String name, ZoneId timeZone, String createdBy, LocalDateTime createDateTime, String lastUpdateBy, LocalDateTime lastUpdate) {
        this.ID = ID;
        this.countryID = countryID;
        this.name = name;
        this.timeZone = timeZone;
        this.createdBy = createdBy;
        this.createDateTime = createDateTime;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdate = lastUpdate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
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

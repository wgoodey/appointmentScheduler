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

    public void setID(int newID) {
        this.ID = newID;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }
}

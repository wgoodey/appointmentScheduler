package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Division {

    int ID;
    String name;
    private ZoneId timeZone;
    String created_By;
    String lastUpdatedBy;
    LocalDate createDate;
    LocalDateTime Last_Update;

    public Division(int ID, String name, ZoneId timeZone) {
        this.ID = ID;
        this.name = name;
        this.timeZone = timeZone;
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

package model;

import java.time.ZoneId;

public class Division {

    int ID;
    int countryID;
    String name;
    //TODO fix timeZone issue. Right now LA time is hardcoded.
    private ZoneId timeZone;


    public Division(int ID, int countryID, String name, ZoneId timeZone) {
        this.ID = ID;
        this.countryID = countryID;
        this.name = name;
        this.timeZone = timeZone;
    }

    public Division(String name, int countryID) {
        this.name = name;
        this.countryID = countryID;
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
}

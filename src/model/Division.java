package model;

public class Division {

    int ID;
    int countryID;
    String name;


    public Division(int ID, int countryID, String name) {
        this.ID = ID;
        this.countryID = countryID;
        this.name = name;
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
}

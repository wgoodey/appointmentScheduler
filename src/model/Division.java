package model;

public class Division {

    int ID;
    String name;
    private int countryID;
    private String countryName;

    public Division(int ID, String name, Country country) {
        this.ID = ID;
        this.name = name;
        this.countryID = country.getID();
        this.countryName = country.getName();
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

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}

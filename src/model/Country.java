package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {

    private int ID;
    private String name;
    private static ObservableList<Division> divisions = FXCollections.observableArrayList();

    public Country(int ID, String name) {
        this.ID = ID;
        this.name = name;
        //TODO pull divisions from database to add to the list
    }

    //copy constructor
    public Country(Country copy) {
        this.ID = copy.getID();
        this.name = copy.getName();
        this.divisions.addAll(copy.getDivisions());
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ObservableList<Division> getDivisions() {
        return divisions;
    }

    //TODO modify so it pulls this from the database
    public static void addDivision(Division division) {
        divisions.add(division);
    }
}

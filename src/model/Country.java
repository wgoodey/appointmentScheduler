package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {

    private int ID;
    private String name;
    private ObservableList<Division> divisions = FXCollections.observableArrayList();

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

    public ObservableList<Division> getDivisions() {
        return divisions;
    }

    //TODO loop through countries and add each division
    //pull divisions from locally stored list
    public ObservableList<String> getDivisionNames() {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();

        for (int i = 0; i < getDivisions().size(); i++) {
            divisionNames.add(divisions.get(i).getName());
        }
        return divisionNames;
    }

    //TODO modify so it pulls this from the database
    public void addDivision(Division division) {
        divisions.add(division);
    }
}

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
    }

    public Country(String name) {
        this.name = name;
    }

    //copy constructor
    public Country(Country copy) {
        this.ID = copy.getID();
        this.name = copy.getName();
        this.divisions.addAll(copy.getAllDivisions());
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableList<Division> getAllDivisions() {
        return divisions;
    }

    //pull divisions from locally stored list
    public ObservableList<String> getDivisionNames() {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();

        for (int i = 0; i < getAllDivisions().size(); i++) {
            divisionNames.add(divisions.get(i).getName());
        }
        return divisionNames;
    }

    public Division getDivision(String divisionName) {
        for(Division division : divisions) {
            if (division.getName() == divisionName) {
                return division;
            }
        }
        return null;
    }

//    public ObservableList<String> getDivisionNames() {
//        ObservableList<String> divisionNames = FXCollections.observableArrayList();
//
//        for (Division division : divisions) {
//            divisionNames.add(division.getName());
//        }
//        return divisionNames;
//    }

    public boolean deleteDivision(Division selectedDivision) {
        for(Division division : divisions) {
            if (division.equals(selectedDivision)) {
                divisions.remove(division);
                return true;
            }
        }
        return false;
    }


    public void addDivision(Division division) {
        divisions.add(division);
    }
}

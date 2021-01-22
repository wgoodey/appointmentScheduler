package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {

    private int ID;
    private String name;
    private final ObservableList<Division> divisions = FXCollections.observableArrayList();

    /**
     * Constructor for the country class.
     * @param ID
     * @param name
     */
    public Country(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    /**
     * Get the ID of the country.
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * Get the name of the country.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the country.
     * @return
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the observableList of the divisions for the country.
     * @return the list of divisions for the country.
     */
    public ObservableList<Division> getAllDivisions() {
        return divisions;
    }

    /**
     * Get the observableList of the division names for the country.
     * @return a list of the division names only.
     */
    public ObservableList<String> getDivisionNames() {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();

        for (int i = 0; i < getAllDivisions().size(); i++) {
            divisionNames.add(divisions.get(i).getName());
        }
        return divisionNames;
    }


    /**
     * Adds a division to the countries divisions list.
     * @param division the division to add.
     */
    public void addDivision(Division division) {
        divisions.add(division);
    }
}

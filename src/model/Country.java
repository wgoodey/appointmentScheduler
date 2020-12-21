package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Country {

    private int ID;
    private String name;
    private String createdBy;
    private LocalDateTime createDateTime;
    private String lastUpdateBy;
    private LocalDateTime lastUpdate;
    private ObservableList<Division> divisions = FXCollections.observableArrayList();

    public Country(int ID, String name, String createdBy, LocalDateTime createDateTime, String lastUpdateBy, LocalDateTime lastUpdate) {
        this.ID = ID;
        this.name = name;
        this.createdBy = createdBy;
        this.createDateTime = createDateTime;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdate = lastUpdate;
        //TODO pull divisions from database to add to the list

    }

    //copy constructor
    public Country(Country copy) {
        this.ID = copy.getID();
        this.name = copy.getName();
        this.createdBy = copy.getCreatedBy();
        this.createDateTime = copy.getCreateDateTime();
        this.lastUpdateBy = copy.getLastUpdateBy();
        this.lastUpdate = copy.getLastUpdate();
        this.divisions.addAll(copy.getAllDivisions());
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

    //TODO modify so it pulls this from the database
    public void addDivision(Division division) {
        divisions.add(division);
    }
}

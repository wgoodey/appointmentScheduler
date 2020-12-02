package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Division {

    int ID;
    String name;
    String created_By;
    String lastUpdatedBy;
    LocalDate createDate;
    LocalDateTime Last_Update;

    public Division(int ID, String name) {
        this.ID = ID;
        this.name = name;
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
}

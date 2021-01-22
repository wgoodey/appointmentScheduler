package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import model.Appointment;
import model.Customer;
import model.Data;

import java.text.Collator;
import java.util.Collections;

public class ReportWindowController {

    @FXML
    private ComboBox<String> comboCustomer;
    @FXML
    private ComboBox<String> comboType;
    @FXML
    private RadioButton radioAll;
    @FXML
    private RadioButton radioMonth;



    /**
     * Sets the comboBoxes for customers and appointment types.
     */
    public void initialize() {

        //set customer combobox
        ObservableList<String> customers = FXCollections.observableArrayList();

        for (Customer customer : Data.getAllCustomers()) {
            customers.add(customer.getName());
        }

        Collections.sort(customers, Collator.getInstance());
        customers.add(0, "All Customers");
        comboCustomer.setItems(customers);
        comboCustomer.getItems().addAll();
        comboCustomer.setValue("All Customers");


        //set type combobox
        ObservableList<String> type = FXCollections.observableArrayList();

        TempInterface check = s -> {
            for (String str : type) {
                if (str.equals(s)) {
                    return false;
                }
            }
            return true;
        };

        for (Appointment appointment : Data.getAllAppointments()) {
            if (check.checkUnique(appointment.getType())) {
                type.add(appointment.getType());
            }
        }


        Collections.sort(type, Collator.getInstance());
        type.add(0, "All");
        comboType.setItems(type);
        comboType.getItems().addAll();
        comboType.setValue("All");

    }

    private void generateReport() {
        //TODO write this code

    }

    private interface TempInterface {
        boolean checkUnique(String s);
    }
}

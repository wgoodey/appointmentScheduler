package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;
import model.Data;

import java.util.Collections;
import java.util.Optional;

public class CustomerFormController {

    @FXML
    private TextField textCustomerID;
    @FXML
    private TextField textName;
    @FXML
    private ComboBox<String> comboCountry;
    @FXML
    private TextField textAddress;
    @FXML
    private TextField textPostalCode;
    @FXML
    private ComboBox<String> comboDivision;
    @FXML
    private TextField textPhone;

    private Customer tempCustomer;

    public void initialize() {
        //set country combobox
        ObservableList<String> countries = FXCollections.observableArrayList(Data.getAllCountryNames());
//        Collections.sort(countries);
        comboCountry.setItems(countries);
        comboCountry.getItems().addAll();

    }

    public void initialize(Customer selectedCustomer) {
        initialize();

        tempCustomer = new Customer(selectedCustomer);

        //populate fields
        //TODO
        textCustomerID.setText(String.valueOf(selectedCustomer.getCustomerID()));
        textName.setText(selectedCustomer.getName());
        textAddress.setText(selectedCustomer.getAddress());
        textPostalCode.setText(selectedCustomer.getPostalCode());
        textPhone.setText(selectedCustomer.getPhone());
        comboCountry.getSelectionModel().select(selectedCustomer.getCountry());
        comboDivision.getSelectionModel().select(selectedCustomer.getDivision());
        buildDivBox();

    }

    public void save(ActionEvent click) {
        //TODO write checks for data entered and format exceptions

        int customerID;
        String name = textName.getText().trim();
        String country = comboCountry.getValue();
        String address = textAddress.getText().trim();
        String postalCode = textPostalCode.getText().trim();
        String division = comboDivision.getValue();
        String phone = textPhone.getText().trim();

        if (!textCustomerID.getText().isEmpty()) {
            customerID = Integer.parseInt(textCustomerID.getText());
        } else {
            customerID = -1;
        }
        //TODO change to constructor that includes create/update info
        Customer newCustomer = new Customer(customerID, name, address, division, postalCode, country, phone);

        if (customerID != -1) {
            //check that no changes have been made
            if (tempCustomer.equals(newCustomer)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cancelling save...");
                alert.setHeaderText("As no changes have been made to the customer data, no save will occur.");
                alert.showAndWait();

            } else {
                Data.updateCustomer(Data.getCustomerIndex(customerID), newCustomer);
            }
        } else { //if customerID = -1
            //iterate through all customers to check for customer with that name
            for (int i = 0; i < Data.getAllCustomers().size(); i++) {
                Customer existingCustomer = Data.getAllCustomers().get(i);
                if (name.equals(Data.getAllCustomers().get(i).getName())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Matching customer found");
                    alert.setHeaderText(
                            "Potential duplicate customer - at least one customer with this name already exists in the database." + '\n' +
                            "ID: " + existingCustomer.getCustomerID() + '\n' +
                            "Name: " + existingCustomer.getName() + '\n' +
                            "Country: " + existingCustomer.getCountry() + '\n' +
                            "Address: " + existingCustomer.getAddress() + '\n' +
                            "Postal code: " + existingCustomer.getPostalCode() + '\n' +
                            "Division: " + existingCustomer.getDivision() + '\n' +
                            "Phone: " + existingCustomer.getPhone());

                    alert.setContentText("Press Okay to create new customer or Cancel to discard.");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() == ButtonType.CANCEL)) {
                        //close window
                        ((Stage)(((Button)click.getSource()).getScene().getWindow())).close();
                        return;
                    }
                    break;
                }
            }
            //add new customer to the list if no matches are found
            //TODO figure out how to deal with auto-generated ID in database
            //temporarily generate random ID between 10 and 100
            newCustomer.setCustomerID((int)(Math.random() * (100 - 10 + 1) + 10));
            Data.addCustomer(newCustomer);
            //close window
        }
        ((Stage)(((Button)click.getSource()).getScene().getWindow())).close();

        //TODO save to database

    }

    public void cancel(ActionEvent click) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modify Customer");
        alert.setHeaderText("Discard modifications to Customer?");
        alert.setContentText("Press Okay to discard changes or Cancel to continue.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            //close window
            ((Stage)(((Button)click.getSource()).getScene().getWindow())).close();
        }
    }

    public void buildDivBox() {
        //TODO look in Lists for country and get divisions
        String selectedCountry = comboCountry.getSelectionModel().getSelectedItem();
        ObservableList<String> divisions = FXCollections.observableArrayList(Data.getCountry(selectedCountry).getDivisionNames());
        Collections.sort(divisions);
        comboDivision.setItems(divisions);
        comboDivision.getItems().addAll();
    }

}
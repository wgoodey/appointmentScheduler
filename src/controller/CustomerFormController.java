package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;
import model.Data;
import model.database.DBConnection;
import model.database.DBInsert;
import model.database.DBSelect;
import model.database.DBUpdate;

import java.sql.SQLException;
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
        comboCountry.setItems(countries);
        comboCountry.getItems().addAll();

    }

    public void initialize(Customer customer) {
        initialize();

        tempCustomer = new Customer(customer);

        //populate fields
        textCustomerID.setText(String.valueOf(customer.getCustomerID()));
        textName.setText(customer.getName());
        textAddress.setText(customer.getAddress());
        textPostalCode.setText(customer.getPostalCode());
        textPhone.setText(customer.getPhone());
        comboCountry.getSelectionModel().select(customer.getCountry());
        comboDivision.getSelectionModel().select(customer.getDivision());
        buildDivBox();

    }

    public void save(ActionEvent click) throws SQLException {

        //collect data for customer
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

        //check that all fields are filled
        if (name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || division.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing data");
            alert.setHeaderText("Unable to save customer.");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        //save data into customer
        Customer newCustomer = new Customer(customerID, name, address, division, postalCode, country, phone);

        //if modifying a customer
        if (customerID != -1) {
            //check that no changes have been made
            if (tempCustomer.equals(newCustomer)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cancelling save...");
                alert.setHeaderText("As no changes have been made to the customer data, no save will occur.");
                alert.showAndWait();

            } else {
                //update in database and allCustomers list
                if (DBUpdate.updateCustomer(DBConnection.getConnection(), newCustomer)) {
                    Data.updateCustomer(Data.getCustomerIndex(customerID), newCustomer);
                }
            }

        } else { //if new customer (customerID = -1)
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

            //insert new customer into database if no matches are found
            DBInsert.insertCustomer(DBConnection.getConnection(), newCustomer);

            //pull ID from most recent DB entry
            newCustomer.setCustomerID(DBSelect.getNewCustomerID(DBConnection.getConnection(), newCustomer));

            //add new customer to allCustomers
            Data.addCustomer(newCustomer);

        }
        //close window
        ((Stage)(((Button)click.getSource()).getScene().getWindow())).close();

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
        String selectedCountry = comboCountry.getSelectionModel().getSelectedItem();
        ObservableList<String> divisions = FXCollections.observableArrayList(Data.getCountry(selectedCountry).getDivisionNames());
        Collections.sort(divisions);
        comboDivision.setItems(divisions);
        comboDivision.getItems().addAll();
    }

}
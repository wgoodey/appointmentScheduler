package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;
import model.Lists;

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
    @FXML
    private ComboBox<String> comboCustomerContact;

    private Customer tempCustomer;

    public void initialize() {
        //set country combobox
        ObservableList<String> countries = FXCollections.observableArrayList(Lists.getAllCountryNames());
        comboCountry.setItems(countries);
        comboCountry.getItems().addAll();
        //TODO set contact combobox

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
        comboCustomerContact.getSelectionModel().select(selectedCustomer.getContact());
        buildDivBox();

    }

    public void save(ActionEvent click) {
        //TODO save contact
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
            Customer newCustomer = new Customer(customerID, name, address, division, postalCode, country, phone);

            //check that no changes have been made
            if (tempCustomer.equals(newCustomer)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cancelling save...");
                alert.setHeaderText("As no changes have been made to the customer data, no save will occur.");
                alert.showAndWait();

            } else {
                Lists.updateCustomer(Lists.getCustomerIndex(customerID), newCustomer);
            }
            //close window
            ((Stage)(((Button)click.getSource()).getScene().getWindow())).close();
        }

        //TODO not saving new customers that don't match a duplicate
        else {
            //TODO check to make sure customer with that name doesn't exist already
            for (int i = 0; i < Lists.getAllCustomers().size(); i++) {
                Customer existingCustomer = Lists.getAllCustomers().get(i);
                if (name.equals(Lists.getAllCustomers().get(i).getName())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Matching customer found");
                    alert.setHeaderText(
                            "Potential duplicate customer - a customer with this name already exists in the database." + '\n' +
                            "ID: " + existingCustomer.getCustomerID() + '\n' +
                            "Name: " + existingCustomer.getName() + '\n' +
                            "Country: " + existingCustomer.getCountry() + '\n' +
                            "Address: " + existingCustomer.getAddress() + '\n' +
                            "Postal code: " + existingCustomer.getPostalCode() + '\n' +
                            "Division: " + existingCustomer.getDivision() + '\n' +
                            "Phone: " + existingCustomer.getPhone());

                    alert.setContentText("Press Okay to create new customer or Cancel to discard.");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() == ButtonType.OK)) {
                        //TODO figure out how to deal with auto-generated ID in database
                        //temporarily generate random ID between 10 and 100
                        customerID = (int)(Math.random() * (100 - 10 + 1) + 10);
                        Customer newCustomer = new Customer(customerID, name, address, division, postalCode, country, phone);
                        Lists.addCustomer(newCustomer);
                        //close window
                        ((Stage)(((Button)click.getSource()).getScene().getWindow())).close();
                    }
                    break;
                }
            }
        }

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
        ObservableList<String> divisions = FXCollections.observableArrayList(Lists.getCountry(selectedCountry).getDivisionNames());
        comboDivision.setItems(divisions);
        comboDivision.getItems().addAll();
    }

}
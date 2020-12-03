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

    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;


    public void initialize() {
        //set country combobox
        ObservableList<String> countries = FXCollections.observableArrayList(Lists.getCountryNames());
        comboCountry.setItems(countries);
        comboCountry.getItems().addAll();

    }

    public void initialize(Customer selectedCustomer) {
        initialize();

        //set fields
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

        int customerID;
        String name = textName.getText().trim();
        String country = comboCountry.getValue();
        String address = textAddress.getText().trim();
        String postalCode = textPostalCode.getText().trim();
        String division = comboDivision.getValue();
        String phone = textPhone.getText().trim();


        if (!textCustomerID.getText().isEmpty()) {
            customerID = Integer.parseInt(textCustomerID.getText());
            Lists.updateCustomer(Lists.getCustomerIndex(customerID), new Customer(customerID, name, address, division, postalCode, country, phone));
        }
        else {
            //TODO figure out how to deal with auto-generated ID in database
            //temporarily generate random ID between 10 and 100
            customerID = (int)(Math.random() * (100 - 10 + 1) + 10);
            Lists.addCustomer(new Customer(customerID, name, address, division, postalCode, country, phone));
        }

        //TODO save to database
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
        //TODO look in Lists for country and get divisions
        String selectedCountry = comboCountry.getSelectionModel().getSelectedItem();
        ObservableList<String> divisions = FXCollections.observableArrayList(Lists.getCountry(selectedCountry).getDivisionNames());
        comboDivision.setItems(divisions);
        comboDivision.getItems().addAll();
    }

}
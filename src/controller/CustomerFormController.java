package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;
import model.Lists;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private TextField textEmail;
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

        //figure out how to refer to divisions inside of allCountries list
        ObservableList<String> divisions = FXCollections.observableArrayList(Lists.getCountryDivisionNames("France"));
//        ObservableList<String> divisions = FXCollections.observableArrayList(Lists.getDivisionNames());
        comboDivision.setItems(divisions);
        comboDivision.getItems().addAll();
    }

    public void save(ActionEvent click) {
        //TODO save to database
        boolean flag = false;


        //TODO figure out how to pull auto-generated ID
        int customerID;
        String name = textName.getText().trim();
        String address = textName.getText().trim();
        String postalCode = textName.getText().trim();
        String country = comboCountry.getValue();
        String division = comboDivision.getValue();
        String phone = textName.getText().trim();
        String createdBy = "";
        String lastUpdatedBy = "";
        //for testing only
        LocalDate createDate = LocalDate.now();
        LocalDateTime lastUpdate = LocalDateTime.now();

        Customer tempCustomer = new Customer(1, name, address, postalCode, phone, country, division,createdBy, lastUpdatedBy, createDate, lastUpdate);
        Lists.addCustomer(tempCustomer);
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


}

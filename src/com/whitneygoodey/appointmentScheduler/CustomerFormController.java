package com.whitneygoodey.appointmentScheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class CustomerFormController {


    @FXML
    TextField custID;
    @FXML
    TextField name;
    @FXML
    ComboBox country;
    @FXML
    TextField address;
    @FXML
    TextField postalCode;
    @FXML
    ComboBox state_province;
    @FXML
    TextField phone;
    @FXML
    TextField email;
    @FXML
    ComboBox custContact;


    @FXML
    Button addButton;
    @FXML
    Button cancelButton;



    public void save() {
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


}

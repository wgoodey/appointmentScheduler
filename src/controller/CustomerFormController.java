package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class CustomerFormController {

    @FXML
    private TextField customerID;
    @FXML
    private TextField name;
    @FXML
    private ComboBox country;
    @FXML
    private TextField address;
    @FXML
    private TextField postalCode;
    @FXML
    private ComboBox state_province;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private ComboBox customerContact;


    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;



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

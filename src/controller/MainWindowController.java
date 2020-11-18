package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {

    Stage stage;
    Parent root;

    @FXML
    private TextField searchBar;
    @FXML
    private TableView customerTable;
    @FXML
    private TableColumn customerIDCol;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn addressCol;
    @FXML
    private TableColumn stateCol;
    @FXML
    private TableColumn phoneCol;
    @FXML
    private Button newButton;
    @FXML
    private Button modifyButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;


    @FXML
    private void handleButtonClick(ActionEvent event) throws IOException {
        Object clickedButton = event.getSource();

        if (clickedButton.equals(newButton)) {
            openCustomerForm();
        }
    }

    private void openCustomerForm() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/customerForm.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add New Customer");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

}

package controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.Lists;

import java.io.IOException;
import java.util.Optional;

public class MainWindowController {

    Stage stage;
    Parent root;

    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIDCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> countryCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, String> postalCol;
    @FXML
    private TableColumn<Customer, String> divisionCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private Button newButton;
    @FXML
    private Button modifyButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;



    /**
     * Loads customer tableView into the main window.
     */
    @FXML
    public void initialize() {
        //set tableView for all customers in the list
        loadCustomerTable(customerTable, customerIDCol, nameCol, countryCol, addressCol, postalCol, divisionCol, phoneCol);

        //wrap observable list in a filtered list
        FilteredList<Customer> filteredCustomers = new FilteredList<>(Lists.getAllCustomers(), p -> true);

        //configure listener for customer searchbar
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCustomers.setPredicate(customer -> {
                if((newValue == null) || (newValue.isEmpty())) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase();

                if(customer.getName().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (String.valueOf((customer.getCustomerID())).contains(lowerCase)) {
                    return true;
                }
                return false;
            });
        });

        //wrap the filtered list in a sorted list
        SortedList<Customer> sortedCustomers = new SortedList(filteredCustomers);
        //bind sortedParts comparator to partsTable comparator
        sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());
        //add data to the TableView
        customerTable.setItems(sortedCustomers);
    }


    public void loadCustomerTable(TableView<Customer> customerTable,
                                  TableColumn<Customer, Integer> customerIDCol,
                                  TableColumn<Customer, String> nameCol,
                                  TableColumn<Customer, String> countryCol,
                                  TableColumn<Customer, String> addressCol,
                                  TableColumn<Customer, String> postalCol,
                                  TableColumn<Customer, String> divisionCol,
                                  TableColumn<Customer, String> phoneCol)    {

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customerTable.setPlaceholder(new Label("No customers found."));
    }

    @FXML
    private void handleButtonClick(ActionEvent event) throws IOException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        Object clickedButton = event.getSource();

        if (clickedButton.equals(newButton)) {
            openCustomerForm();
        }

        else if (clickedButton.equals(modifyButton)) {
            openCustomerForm(selectedCustomer);
        }

        else if (clickedButton.equals(deleteButton)) {
            //TODO add alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete customer");
            alert.setHeaderText("Delete customer " + selectedCustomer.getName() + "?");
            alert.setContentText("Press Okay to confirm or Cancel to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                Lists.deleteCustomer(selectedCustomer);
            }

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

    private void openCustomerForm(Customer selectedCustomer) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customerForm.fxml"));
        root = fxmlLoader.load();
        CustomerFormController controller = fxmlLoader.getController();
        controller.initialize(selectedCustomer);
        Stage stage = new Stage();
        stage.setTitle("Modify customer");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

}

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
import model.Appointment;
import model.Customer;
import model.Data;
import model.database.DBConnection;
import model.database.DBDelete;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class MainWindowController {

    Stage stage;
    Parent root;

    @FXML
    private TextField customerSearchBar;
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
    private TextField appSearchBar;
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appIDCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, Integer> contactCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> startCol;
    @FXML
    private TableColumn<Appointment, String> endCol;
    @FXML
    private TableColumn<Appointment, Integer> custIDCol;
    @FXML
    private Button newCustomerButton;
    @FXML
    private Button modifyCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button newAppointmentButton;
    @FXML
    private Button modifyAppointmentButton;
    @FXML
    private Button deleteAppointmentButton;



    /**
     * Loads customer tableView into the main window.
     */
    @FXML
    public void initialize() {

        //set tableView for all customers in the list
        loadCustomerTable(customerTable, customerIDCol, nameCol, countryCol, addressCol, postalCol, divisionCol, phoneCol);

        //wrap observable list in a filtered list
        FilteredList<Customer> filteredCustomers = new FilteredList<>(Data.getAllCustomers(), p -> true);

        //configure listener for customer searchbar
        customerSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
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
        //bind sortedCustomer comparator to customerTable comparator
        sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());
        //add data to the TableView
        customerTable.setItems(sortedCustomers);




        //TODO figure out why appointments listener doesn't work
        //set tableview for appointments
        loadAppointmentTable(appointmentTable, appIDCol, titleCol, descriptionCol, locationCol, contactCol, typeCol, startCol, endCol, custIDCol);

        //wrap observable list in a filtered list
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(Data.getAllAppointments(), p -> true);

        //configure listener for appointment searchbar
        appSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAppointments.setPredicate(appointment -> {
                if((newValue == null) || (newValue.isEmpty())) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase();

                if(lowerCase.equals(appointment.getAppointmentID())) {
                    return true;
                } else if (String.valueOf((appointment.getCustomerID())).contains(lowerCase)) {
                    return true;
                }
                return false;
            });
        });

        //wrap the filtered list in a sorted list
        SortedList<Appointment> sortedAppointments = new SortedList(filteredAppointments);
        //bind sortedAppointments comparator to AppointmentTable comparator
        sortedAppointments.comparatorProperty().bind(appointmentTable.comparatorProperty());
        //add data to the TableView
        appointmentTable.setItems(sortedAppointments);
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

    public void loadAppointmentTable(TableView<Appointment> appointmentTable,
                                     TableColumn<Appointment, Integer> appIDCol,
                                     TableColumn<Appointment, String> titleCol,
                                     TableColumn<Appointment, String> descriptionCol,
                                     TableColumn<Appointment, String> locationCol,
                                     TableColumn<Appointment, Integer> contactCol,
                                     TableColumn<Appointment, String> typeCol,
                                     TableColumn<Appointment, String> startCol,
                                     TableColumn<Appointment, String> endCol,
                                     TableColumn<Appointment, Integer> custIDCol) {

        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        appointmentTable.setPlaceholder(new Label("No appointments found."));

    }

    @FXML
    private void handleCustomerButtonClick(ActionEvent event) throws IOException, SQLException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        Object clickedButton = event.getSource();

        if (clickedButton.equals(newCustomerButton)) {
            openCustomerForm();
        }

        else if (clickedButton.equals(modifyCustomerButton)) {
            openCustomerForm(selectedCustomer);
        }

        else if (clickedButton.equals(deleteCustomerButton)) {
            //TODO add alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete customer");
            alert.setHeaderText("Delete customer " + selectedCustomer.getName() + "?");
            alert.setContentText("Press Okay to confirm or Cancel to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                //delete selected customer from database and if successful, delete from allCustomers
                if(DBDelete.deleteCustomer(DBConnection.getConnection(), selectedCustomer)) {
                    Data.deleteCustomer(selectedCustomer);
                }
            }

        }
    }

    @FXML
    private void handleAppointmentButtonClick(ActionEvent event) throws IOException, SQLException {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        Object clickedButton = event.getSource();

        if (clickedButton.equals(newAppointmentButton)) {
            openAppointmentForm();
        }

        else if (clickedButton.equals(modifyAppointmentButton)) {
            openAppointmentForm(selectedAppointment);
        }

        else if (clickedButton.equals(deleteAppointmentButton)) {
            //TODO add alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete appointment");
            alert.setHeaderText("Delete appointment " + selectedAppointment.getAppointmentID() + "?");
            alert.setContentText("Press Okay to confirm or Cancel to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                //delete selected customer from database and if successful, delete from allCustomers
                if(DBDelete.deleteAppointment(DBConnection.getConnection(), selectedAppointment)) {
                    //get customer from customerID in appointment and delete appointment in the list

                }
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

    private void openAppointmentForm() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/appointmentForm.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add New Appointment");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void openAppointmentForm(Appointment selectedAppointment) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/appointmentForm.fxml"));
        root = fxmlLoader.load();
        AppointmentFormController controller = fxmlLoader.getController();
        controller.initialize(selectedAppointment);
        Stage stage = new Stage();
        stage.setTitle("Modify customer");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

}

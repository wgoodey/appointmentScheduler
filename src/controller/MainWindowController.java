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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class MainWindowController {

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
    private ToggleGroup appFilter;
    @FXML
    private RadioButton radioAll;
    @FXML
    private RadioButton radioMonth;
    @FXML
    private RadioButton radioWeek;
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

    private DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-M");
    private DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("yyyy-w");
    private FilteredList<Appointment> filteredAppointments;
    private SortedList<Appointment> sortedAppointments;



    /**
     * Loads customer tableView and appointment tableview into the main window.
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



        //set tableview for appointments
        loadAppointmentTable(appointmentTable, appIDCol, titleCol, descriptionCol, locationCol, contactCol, typeCol, startCol, endCol, custIDCol);

        //wrap observable list in a filtered list
        filteredAppointments = new FilteredList<>(Data.getAllAppointments(), p -> true);


        //configure listener for appointment searchbar
        appSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAppointments.setPredicate(appointment -> {
                if((newValue == null) || (newValue.isEmpty())) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase();

                if(appointment.getTitle().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (appointment.getLocation().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if(appointment.getType().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (String.valueOf((appointment.getCustomerID())).contains(lowerCase)) {
                    return true;
                }
                return false;
            });
        });


        //wrap the filtered list in a sorted list
        sortedAppointments = new SortedList(filteredAppointments);
        //bind sortedAppointments comparator to AppointmentTable comparator
        sortedAppointments.comparatorProperty().bind(appointmentTable.comparatorProperty());
        //add data to the TableView
        appointmentTable.setItems(sortedAppointments);
    }

    /**
     * Sets the TableView for the customer table.
     */
    public void loadCustomerTable(TableView<Customer> customerTable,
                                  TableColumn<Customer, Integer> customerIDCol,
                                  TableColumn<Customer, String> nameCol,
                                  TableColumn<Customer, String> countryCol,
                                  TableColumn<Customer, String> addressCol,
                                  TableColumn<Customer, String> postalCol,
                                  TableColumn<Customer, String> divisionCol,
                                  TableColumn<Customer, String> phoneCol) {

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customerTable.setPlaceholder(new Label("No customers found."));
    }

    /**
     * Sets the TableView for the appointments table.
     */
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
        startCol.setCellValueFactory(new PropertyValueFactory<>("startString"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endString"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        appointmentTable.setPlaceholder(new Label("No appointments found."));

        //check for appointments
        checkForAppointments();
    }

    /**
     * Event handler that handles button clicks on the customer button bar.
     * @param event The button that is clicked in the user interface.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void handleCustomerButtonClick(ActionEvent event) throws IOException, SQLException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        Object clickedButton = event.getSource();

        if (clickedButton.equals(newCustomerButton)) {
            openCustomerForm();
        }

        else if (clickedButton.equals(modifyCustomerButton)) {
            openCustomerForm(selectedCustomer);
        }

        else if (clickedButton.equals(deleteCustomerButton)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete customer");
            alert.setHeaderText("Delete customer " + selectedCustomer.getName() + " and all associated appointments?");
            alert.setContentText("Press Okay to confirm or Cancel to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {

                //remove all of customer's appointments from database
                for (Appointment appointment : Data.getAllAppointments()) {
                    if (appointment.getCustomerID() == selectedCustomer.getCustomerID()) {
                        DBDelete.deleteAppointment(DBConnection.getConnection(), appointment);
                    }
                }

                //remove customer's appointments from list
                Data.getAllAppointments().removeIf(a -> a.getCustomerID() == selectedCustomer.getCustomerID());

                //delete selected customer from database and if successful, delete from allCustomers
                if (DBDelete.deleteCustomer(DBConnection.getConnection(), selectedCustomer)) {
                    Data.deleteCustomer(selectedCustomer);
                }

                //Display message of successful deletion
                Dialog<String> dialog = new Dialog<>();
                String dialogMessage = "Customer named " + selectedCustomer.getName() + " successfully deleted.";
                dialog.setTitle("Deleted customer");
                dialog.setContentText(dialogMessage);
                ButtonType okay = new ButtonType("Okay");
                dialog.getDialogPane().getButtonTypes().add(okay);
                dialog.showAndWait();
            }

        }
    }

    /**
     * Event handler that handles button clicks on the appointment button bar.
     * @param event The button that is clicked in the user interface.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void handleAppointmentButtonClick(ActionEvent event) throws IOException, SQLException {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        Object clickedButton = event.getSource();

        if (clickedButton.equals(newAppointmentButton)) {
            openAppointmentForm();
        }

        else if (clickedButton.equals(modifyAppointmentButton)) {
            openAppointmentForm(selectedAppointment);
        }

        else if (clickedButton.equals(deleteAppointmentButton)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete appointment");
            alert.setHeaderText("Delete appointment " + selectedAppointment.getAppointmentID() + "?");
            alert.setContentText("Press Okay to confirm or Cancel to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                //delete selected appointment from database and if successful, delete from allAppointments
                if(DBDelete.deleteAppointment(DBConnection.getConnection(), selectedAppointment)) {
                    //get customer from customerID in appointment and delete appointment in the list
                    Data.deleteAppointment(selectedAppointment);
                }

                Dialog<String> dialog = new Dialog<>();
                String dialogMessage = "Successfully deleted " + selectedAppointment.getType() + " appointment with ID " + selectedAppointment.getAppointmentID() + ".";
                dialog.setTitle("Deleted appointment");
                dialog.setContentText(dialogMessage);
                ButtonType okay = new ButtonType("Okay");
                dialog.getDialogPane().getButtonTypes().add(okay);
                dialog.showAndWait();
            }

        }
    }

    /**
     * Loads the customer form where the user can enter data to create a new customer.
     * @throws IOException
     */
    private void openCustomerForm() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/customerForm.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add New Customer");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Loads the customer form and populates it with data from an existing customer in order to make modifications.
     * @param selectedCustomer the customer to be modified.
     * @throws IOException
     */
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

    /**
     * Loads the appointment form where the user can enter data to create a new appointment.
     * @throws IOException
     */
    private void openAppointmentForm() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/appointmentForm.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add New Appointment");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Loads the appointment form and populates it with data from an existing appointment in order to make modifications.
     * @param selectedAppointment the appointment to be modified.
     * @throws IOException
     */
    private void openAppointmentForm(Appointment selectedAppointment) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/appointmentForm.fxml"));
        root = fxmlLoader.load();
        AppointmentFormController controller = fxmlLoader.getController();
        controller.initialize(selectedAppointment);
        Stage stage = new Stage();
        stage.setTitle("Modify appointment");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Checks to see if there is an appointment scheduled for the current user in the next 15 minutes and displays an alert notifying the user of the results.
     */
    private void checkForAppointments() {
        ZonedDateTime now = ZonedDateTime.now();

        AtomicReference<Appointment> first = new AtomicReference<>(Data.getAllAppointments().get(0));

        Data.getAllAppointments().forEach(a -> {
            if (a.getUserID() == Data.getCurrentUser().getUserID() &&
                    a.getStartTime().isAfter(now)) {
                if (a.getStartTime().isBefore(first.get().getStartTime())) {
                    first.set(a);
                }
            }
        });

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming appointments");
        alert.setHeaderText("Upcoming appointments for user " + Data.getCurrentUser().getUsername() + ".");
        alert.setContentText("You have no appointments scheduled in the next 15 minutes.");

        if (first.get().getStartTime().isAfter(now) &&
                first.get().getStartTime().isBefore(now.plusMinutes(15))) {
            alert.setContentText("Your next appointment:\n" +
                    "AppointmentID: " + first.get().getAppointmentID() + "\n\n" +
                    "Scheduled time: " + first.get().getStartString());
        }
        alert.showAndWait();
    }


    /**
     * Checks the setting in the main window and filters the appointments in the table according to user setting (All, appointments for the current month, and appointments for the current week.
     */
    public void setFilteredAppointments() {

        filteredAppointments = new FilteredList<>(Data.getAllAppointments(), p -> true);
        sortedAppointments = new SortedList(filteredAppointments);

        if (appFilter.getSelectedToggle().equals(radioMonth)) {
            //current month's appointments
            filteredAppointments = new FilteredList<>(Data.getAllAppointments(), p -> p.getStartTime().format(monthFormatter).equals(ZonedDateTime.now().format(monthFormatter)));
            sortedAppointments = new SortedList(filteredAppointments);
        } else if (appFilter.getSelectedToggle().equals(radioWeek)) {
            //current week's appointments
            filteredAppointments = new FilteredList<>(Data.getAllAppointments(), p -> p.getStartTime().format(weekFormatter).equals(ZonedDateTime.now().format(weekFormatter)));
            sortedAppointments = new SortedList(filteredAppointments);
        }
        appointmentTable.setItems(sortedAppointments);
    }
}
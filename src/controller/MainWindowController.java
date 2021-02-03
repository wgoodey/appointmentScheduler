package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Data;
import model.database.DBConnection;
import model.database.DBDelete;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Collator;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The MainWindowController class manages the loading of all tables and report generation. Customer and appointment creation, modification, and deletion are initiated here.
 */
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
    private CheckBox pastCheckBox;
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

    @FXML
    private TableView customerReportTable;
    @FXML
    private TableColumn<Appointment, Integer> crCustIDCol;
    @FXML
    private TableColumn<Appointment, String> crNameCol;
    @FXML
    private TableColumn<Appointment, Integer> crIDCol;
    @FXML
    private TableColumn<Appointment, String> crTitleCol;
    @FXML
    private TableColumn<Appointment, String> crTypeCol;
    @FXML
    private TableColumn<Appointment, String> crStartCol;

    @FXML
    private TableView contactReportTable;
    @FXML
    private TableColumn<Appointment, Integer> corContactID;
    @FXML
    private TableColumn<Appointment, String> corNameCol;
    @FXML
    private TableColumn<Appointment, Integer> corIDCol;
    @FXML
    private TableColumn<Appointment, String> corTitleCol;
    @FXML
    private TableColumn<Appointment, String> corTypeCol;
    @FXML
    private TableColumn<Appointment, String> corDescrCol;
    @FXML
    private TableColumn<Appointment, String> corStartCol;
    @FXML
    private TableColumn<Appointment, String> corEndCol;
    @FXML
    private TableColumn<Appointment, Integer> corCustIDCol;


    @FXML
    private TableView timeReportTable;
    @FXML
    private TableColumn<Appointment, Integer> timeContactIDCol;
    @FXML
    private TableColumn<Appointment, Integer> timeIDCol;
    @FXML
    private TableColumn<Appointment, String> timeTitleCol;
    @FXML
    private TableColumn<Appointment, String> timeTypeCol;
    @FXML
    private TableColumn<Appointment, String> timeDescrCol;
    @FXML
    private TableColumn<Appointment, String> timeStartCol;
    @FXML
    private TableColumn<Appointment, String> timeEndCol;
    @FXML
    private TableColumn<Appointment, Integer> timeCustIDCol;

    @FXML
    private Accordion accordion;
    @FXML
    private TitledPane customerPane;
    @FXML
    private TitledPane contactPane;
    @FXML
    private TitledPane timePane;
    @FXML
    private ComboBox<String> comboCustomer;
    @FXML
    private ComboBox<String> comboType;
    @FXML
    private RadioButton radioMonthCustomer;
    @FXML
    private ComboBox<String> comboContact;
    @FXML
    private RadioButton radioMonthContact;
    @FXML
    private CheckBox checkBoxPastReport;
    @FXML
    private ComboBox<String> comboTimeCustomer;
    @FXML
    private ComboBox<String> comboTimeContact;
    @FXML
    private RadioButton radioMonthTime;

    @FXML
    private VBox reportInfoBox;


    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-M");
    private final DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("yyyy-w");

    private FilteredList<Appointment> filteredAppointments;
    private SortedList<Appointment> sortedAppointments;
    private final ObservableList<Appointment> customerReport = FXCollections.observableArrayList();
    private final ObservableList<Appointment> contactReport = FXCollections.observableArrayList();
    private final ObservableList<Appointment> timeReport = FXCollections.observableArrayList();

    private final Predicate<Appointment> clearFilter = appointment -> true;
    private final Predicate<Appointment> future = appointment -> appointment.getStartTime().isAfter(ZonedDateTime.now());
    private final Predicate<Appointment> month = appointment -> appointment.getStartTime().format(monthFormatter).equals(ZonedDateTime.now().format(monthFormatter));
    private final Predicate<Appointment> week = appointment -> appointment.getStartTime().format(weekFormatter).equals(ZonedDateTime.now().format(weekFormatter));
    private Predicate<Appointment> pastCheck = clearFilter;
    private Predicate<Appointment> timeRange = clearFilter;


    private final String ALL_CUSTOMERS = "All Customers";
    private final String ALL_TYPES = "All Types";
    private final String ALL_CONTACTS = "All Contacts";



    /**
     * Loads tableViews and comboBoxes into the main window.
     */
    @FXML
    public void initialize() {

        //set tableView for all customers in the list
        loadCustomerTable(customerTable, customerIDCol, nameCol, countryCol, addressCol, postalCol, divisionCol, phoneCol);

        //wrap observable list in a filtered list
        FilteredList<Customer> filteredCustomers = new FilteredList<>(Data.getAllCustomers(), p -> true);


        /**
         * discussion of lambda
         * This lambda expression adds a listener to the customer searchBar and sets the predicate on the filteredCustomers list based on the text in the searchBar.
         */
        //configure listener for customer searchbar
        customerSearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> filteredCustomers.setPredicate(customer -> {
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
        }));

        //wrap the filtered list in a sorted list
        SortedList<Customer> sortedCustomers = new SortedList(filteredCustomers);
        //bind sortedCustomer comparator to customerTable comparator
        sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());
        //add data to the TableView
        customerTable.setItems(sortedCustomers);
        customerTable.getSortOrder().add(nameCol);



        //set tableview for appointments
        loadAppointmentTable(appointmentTable, appIDCol, titleCol, descriptionCol, locationCol, contactCol, typeCol, startCol, endCol, custIDCol);

        //wrap observable list in a filtered list
        filteredAppointments = new FilteredList<>(Data.getAllAppointments(), p -> true);



        /**
         * discussion of lambda
         * This lambda expression adds a listener to the searchBar and sets the predicate on the filteredAppointments list based on the text in the appointment searchBar, taking into account the time scope selected by the user.
         */
        //configure listener for appointment searchbar
        appSearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> filteredAppointments.setPredicate(timeRange.and(pastCheck.and(appointment -> {

            if((newValue == null) || (newValue.isEmpty())) {
                return true;
            }
            String lowerCase = newValue.toLowerCase();

            if(appointment.getTitle().toLowerCase().contains(lowerCase)) {
                return true;
            } else if (appointment.getDescription().toLowerCase().contains(lowerCase)) {
                return true;
            }
            return false;
        }))));



        /**
         * discussion of lambda
         * This lambda expression adds  a listener to the pastCheckBox and sets the predicate on the filteredAppointments list based on the time scope selected by the user.
         */
        //configure listener for the Past checkbox
        pastCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            //clear searchBar
            appSearchBar.clear();

            if (!pastCheckBox.isSelected()) {
                pastCheck = future;
            } else {
                pastCheck = clearFilter;
            }
            filteredAppointments.setPredicate(timeRange.and(pastCheck));
        });

        /**
         * discussion of lambda
         * This lambda expression adds  a listener to the toggleGroup and sets the predicate on the filteredAppointments list based on the time scope selected by the user.
         */
        //configure listener for the toggleGroup
        appFilter.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            //clear searchBar
            appSearchBar.clear();

            if (appFilter.getSelectedToggle().equals(radioMonth)) {
                //current month's appointments
                timeRange = month;
            } else if (appFilter.getSelectedToggle().equals(radioWeek)) {
                //current week's appointments
                timeRange = week;
            } else if (appFilter.getSelectedToggle().equals(radioAll)) {
                timeRange = clearFilter;
            }
            try {
                filteredAppointments.setPredicate(timeRange.and(pastCheck));
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        });



        //wrap the filtered list in a sorted list
        sortedAppointments = new SortedList(filteredAppointments);
        //bind sortedAppointments comparator to AppointmentTable comparator
        sortedAppointments.comparatorProperty().bind(appointmentTable.comparatorProperty());
        //add data to the TableView
        appointmentTable.setItems(sortedAppointments);
        appointmentTable.getSortOrder().add(startCol);


        //set up the report tab
        buildReportComboBoxes();

        //configure listener for accordion
        accordion.expandedPaneProperty().addListener(
                (ObservableValue<? extends TitledPane> ov, TitledPane old_val, TitledPane new_val) -> {
                    if (new_val != null) {
                        bringTableToFront();
                    }
                }
        );

        customerReportTable.toFront();

        //set tableView for customers reports
        loadCustomerReportTable(customerReportTable, crIDCol, crCustIDCol, crNameCol, crTitleCol, crTypeCol, crStartCol);
        customerReportTable.setItems(customerReport);

        //set tableView for contact reports
        loadContactReportTable(contactReportTable, corContactID, corNameCol, corIDCol, corTitleCol, corTypeCol, corDescrCol, corStartCol, corEndCol, corCustIDCol);
        contactReportTable.setItems(contactReport);

        //set tableView for contact reports
        loadTimeReportTable(timeReportTable, timeContactIDCol, timeIDCol, timeTitleCol, timeTypeCol, timeDescrCol, timeStartCol, timeEndCol, timeCustIDCol);
        timeReportTable.setItems(timeReport);

//        sortedCustomers.comparatorProperty().bind(customerTable.comparatorProperty());
    }

    /**
     * Loads the TableView for the customer table.
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
     * Loads the TableView for the appointments table.
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
     * Loads the TableView for the customer report table.
     */
    public void loadCustomerReportTable(TableView<Appointment> customerReportTable,
                                  TableColumn<Appointment, Integer> crIDCol,
                                  TableColumn<Appointment, Integer> crCustIDCol,
                                  TableColumn<Appointment, String> crNameCol,
                                  TableColumn<Appointment, String> crTitleCol,
                                  TableColumn<Appointment, String> crTypeCol,
                                  TableColumn<Appointment, String> crStartCol) {

        crIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        crCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        crNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName" ));
        crTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        crTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        crStartCol.setCellValueFactory(new PropertyValueFactory<>("startString"));

        customerReportTable.setPlaceholder(new Label("No appointments found."));
    }

    /**
     * Loads the TableView for the contact report table.
     */
    public void loadContactReportTable(TableView<Appointment> contactReportTable,
                                        TableColumn<Appointment, Integer> corContactID,
                                        TableColumn<Appointment, String> corName,
                                        TableColumn<Appointment, Integer> corIDCol,
                                        TableColumn<Appointment, String> corTitleCol,
                                        TableColumn<Appointment, String> corTypeCol,
                                        TableColumn<Appointment, String> corDescrCol,
                                        TableColumn<Appointment, String> corStartCol,
                                        TableColumn<Appointment, String> corEndCol,
                                        TableColumn<Appointment, Integer> corCustIDCol) {

        corContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        corName.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        corIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        corTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        corTypeCol.setCellValueFactory(new PropertyValueFactory<>("type" ));
        corDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        corStartCol.setCellValueFactory(new PropertyValueFactory<>("startString"));
        corEndCol.setCellValueFactory(new PropertyValueFactory<>("endString"));
        corCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        contactReportTable.setPlaceholder(new Label("No appointments found."));
    }

    /**
     * Loads the TableView for the temp report table.
     */
    public void loadTimeReportTable(TableView<Appointment> timeReportTable,
                                    TableColumn<Appointment, Integer> corContactID,
                                    TableColumn<Appointment, Integer> corIDCol,
                                    TableColumn<Appointment, String> corTitleCol,
                                    TableColumn<Appointment, String> corTypeCol,
                                    TableColumn<Appointment, String> corDescrCol,
                                    TableColumn<Appointment, String> corStartCol,
                                    TableColumn<Appointment, String> corEndCol,
                                    TableColumn<Appointment, Integer> corCustIDCol) {

        corContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        corIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        corTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        corTypeCol.setCellValueFactory(new PropertyValueFactory<>("type" ));
        corDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        corStartCol.setCellValueFactory(new PropertyValueFactory<>("startString"));
        corEndCol.setCellValueFactory(new PropertyValueFactory<>("endString"));
        corCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        timeReportTable.setPlaceholder(new Label("No appointments found."));
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
                Data.getAllAppointments().removeIf(appointment -> appointment.getCustomerID() == selectedCustomer.getCustomerID());

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

        Data.getAllAppointments().forEach(appointment -> {
            if (appointment.getUserID() == Data.getCurrentUser().getUserID() &&
                    appointment.getStartTime().isAfter(now)) {
                if (appointment.getStartTime().isBefore(first.get().getStartTime())) {
                    first.set(appointment);
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
     * Populates the customer, type, and contact comboBoxes on the reports tab.
     */
    private void buildReportComboBoxes() {

        //set customer combobox
        ObservableList<String> customers = FXCollections.observableArrayList();

        for (Customer customer : Data.getAllCustomers()) {
            customers.add(customer.getName());
        }

        Collections.sort(customers, Collator.getInstance());
        customers.add(0, ALL_CUSTOMERS);
        comboCustomer.setItems(customers);
        comboCustomer.getItems().addAll();
        comboCustomer.setValue(ALL_CUSTOMERS);

        //set customer comboBox on time pane
        comboTimeCustomer.setItems(customers);
        comboTimeCustomer.getItems().addAll();
        comboTimeCustomer.setValue(ALL_CUSTOMERS);

        buildTypeComboBox();

        //set contact combobox
        ObservableList<String> contacts = FXCollections.observableArrayList();
        for (Contact contact : Data.getAllContacts()) {
            contacts.add(contact.getName());
        }
        Collections.sort(contacts, Collator.getInstance());
        contacts.add(0, ALL_CONTACTS);
        comboContact.setItems(contacts);
        comboContact.getItems().addAll();
        comboContact.setValue(ALL_CONTACTS);

        //set contact comboBox on time pane
        comboTimeContact.setItems(contacts);
        comboTimeContact.getItems().addAll();
        comboTimeContact.setValue(ALL_CONTACTS);

    }

    /**
     * Generates a table with customer data from the allAppointments list based on selected criteria in the reports form. Displays an alert with a summary of information related to customer/appointment type.
     */
    @FXML
    private void generateCustomerReport() {

        customerReport.clear();
        reportInfoBox.getChildren().clear();

        String selectedCustomer = comboCustomer.getSelectionModel().getSelectedItem();
        customerReport.addAll(Data.getAllAppointments());

        int customerID = -1;

        //remove appointments that don't match selected customer
        if (!comboCustomer.getValue().equals(ALL_CUSTOMERS)) {
            for (Customer c : Data.getAllCustomers()) {
                if (c.getName().equals(selectedCustomer)) {
                    customerID = c.getCustomerID();
                    break;
                }
            }
            int finalCustomerID = customerID;

            if (customerID != -1) {
                customerReport.removeIf(appointment -> appointment.getCustomerID() != finalCustomerID);
            }
        }

        //remove appointments that don't match selected type
        if (!comboType.getValue().equals(ALL_TYPES)) {
            customerReport.removeIf(appointment -> !appointment.getType().equals(comboType.getValue()));
        }

        //remove appointments that don't match the current month
        if (radioMonthCustomer.isSelected()) {
            customerReport.removeIf(appointment -> !appointment.getStartTime().format(monthFormatter).equals(ZonedDateTime.now().format(monthFormatter)));
        }

        //generate details for the reportInfoBox
        ObservableList<String> types = comboType.getItems();
        String appointmentInfo = selectedCustomer + ": " + customerReport.size() + " appointment(s).";
//        Label nameLabel = new Label(appointmentInfo);
//        nameLabel.setStyle("-fx-font-weight: bold");
//        reportInfoBox.getChildren().add(nameLabel);

        String typeInfo = "";

        for(String s : types) {
            int count = 0;

            for (Appointment a : customerReport) {
                if (s.equals(ALL_TYPES)) {
                    continue;
                } else if (a.getType().equals(s)) {
                    count++;
                }

            }
            if (count > 0) {
                typeInfo += s + ": " + count + " appointment(s)" + "\n";
            }
        }
//        typeInfo += "\n";
//        Label label = new Label(typeInfo);
//        label.setStyle("-fx-font-weight: bold");
//        reportInfoBox.getChildren().add(label);

        contactReportTable.getSortOrder().clear();
        contactReportTable.getSortOrder().add(crTypeCol);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer Report");
        alert.setHeaderText(appointmentInfo);
        alert.setContentText(typeInfo);
        alert.showAndWait();

    }

    /**
     * Generates a table with contact data from the allAppointments list based on selected criteria in the reports form.
     */
    @FXML
    private void generateContactReport() {
        contactReport.clear();
        reportInfoBox.getChildren().clear();

        contactReport.addAll(Data.getAllAppointments());
        String contactName = comboContact.getValue();
        int contactID = 0;
        for (Contact c : Data.getAllContacts()) {
            if (c.getName().equals(contactName)) {
                contactID = c.getContactID();
                break;
            }
        }


        int finalContactID = contactID;

        //remove appointments that don't match the selected contact
        if (!comboContact.getValue().equals(ALL_CONTACTS)) {
            contactReport.removeIf(appointment -> appointment.getContactID() != finalContactID);
        }

        //remove appointments in the past
        if(!checkBoxPastReport.isSelected()) {
            contactReport.removeIf(appointment -> appointment.getEndTime().isBefore(ZonedDateTime.now()));
        }

        //remove appointments that don't match the current month
        if (radioMonthContact.isSelected()) {
            contactReport.removeIf(appointment -> !appointment.getStartTime().format(monthFormatter).equals(ZonedDateTime.now().format(monthFormatter)));
        }

//        ObservableList<String> contacts = comboContact.getItems();
//        String info = contactName + ": " + contactReport.size() + " appointment(s). \n";
//        Label nameLabel = new Label(info);
//        reportInfoBox.getChildren().add(nameLabel);

        contactReportTable.getSortOrder().clear();
        contactReportTable.getSortOrder().add(corNameCol);
        contactReportTable.getSortOrder().add(corStartCol);

    }

    /**
     * Generates a table with data from the allAppointments list based on selected criteria in the reports form. Displays an alert with a summary of time-related information.
     */
    @FXML
    private void generateTimeReport() {
        timeReport.clear();
        reportInfoBox.getChildren().clear();

        //get selections
        String selectedContact = comboTimeContact.getValue();
        String selectedCustomer = comboTimeCustomer.getValue();
        int customerID = -1;

        //Add all appointments to list
        timeReport.addAll(Data.getAllAppointments());

        //get contact ID
        int contactID = -1;
        for (Contact c : Data.getAllContacts()) {
            if (c.getName().equals(selectedContact)) {
                contactID = c.getContactID();
                break;
            }
        }

        //get customer ID
        for (Customer c : Data.getAllCustomers()) {
            if (c.getName().equals(selectedCustomer)) {
                customerID = c.getCustomerID();
                break;
            }
        }
        int finalCustomerID = customerID;
        int finalContactID = contactID;

        //remove appointments that don't match the selected contact
        if (!comboTimeContact.getValue().equals(ALL_CONTACTS)) {
            timeReport.removeIf(appointment -> appointment.getContactID() != finalContactID);
        }

        //remove appointments that don't match the selected customer
        if (!comboTimeCustomer.getValue().equals(ALL_CUSTOMERS)) {
            timeReport.removeIf(appointment -> appointment.getCustomerID() != finalCustomerID);
        }

        //remove appointments that don't match the current month
        if (radioMonthTime.isSelected()) {
            timeReport.removeIf(appointment -> !appointment.getStartTime().format(monthFormatter).equals(ZonedDateTime.now().format(monthFormatter)));
        }


        //generate details for the reportInfoBox
        ObservableList<String> contacts = comboTimeContact.getItems();
        String contactInfo = selectedContact + ": " + timeReport.size() + " appointment(s).";
//        Label nameLabel = new Label(contactInfo);
//        nameLabel.setStyle("-fx-font-weight: bold");
//        reportInfoBox.getChildren().add(nameLabel);

        Duration totalDuration = Duration.ZERO;

        String durationInfo = "";
        //all contacts
        if (selectedContact.equals(ALL_CONTACTS)) {
            //calculate total duration of all appointments
            for (Appointment a : timeReport) {
                totalDuration = totalDuration.plus(Duration.between(a.getStartTime(), a.getEndTime()));
            }
            durationInfo += "Total duration of all appointments: " + totalDuration.toHoursPart() + " hours, " + totalDuration.toMinutesPart() + " minutes\n\n";
            ;

            for (String c : contacts) {
                Duration contactDuration = Duration.ZERO;
                //get contact ID
                int ID = -1;
                for (Contact contact : Data.getAllContacts()) {
                    if (contact.getName().equals(c)) {
                        ID = contact.getContactID();
                        break;
                    }
                }

                int finalID = ID;

                //iterate through appointments list and add duration information for each contact to contactDuration string
                for (Appointment a : timeReport) {
                    if (a.getContactID() == ID) {
                        contactDuration = contactDuration.plus(Duration.between(a.getStartTime(), a.getEndTime()));
                    }
                }
                totalDuration = totalDuration.plus(contactDuration);

                if (!contactDuration.isZero()) {
                    durationInfo += c + ": " + contactDuration.toHoursPart() + " hours, " + contactDuration.toMinutesPart() + " minutes\n";
                }

            }
        } else {
            //iterate through contacts list
            for (String c : contacts) {
                Duration contactDuration = Duration.ZERO;
                //get contact ID
                int ID = -1;
                for (Contact contact : Data.getAllContacts()) {
                    if (contact.getName().equals(c)) {
                        ID = contact.getContactID();
                        break;
                    }
                }

                //iterate through appointments list and add duration of appointments that match contactID
                for (Appointment a : timeReport) {
                    if (a.getContactID() == ID) {
                        contactDuration = contactDuration.plus(Duration.between(a.getStartTime(), a.getEndTime()));
                    }
                }
                totalDuration = totalDuration.plus(contactDuration);

                //add duration info to string if duration > 0
                if (!contactDuration.isZero()) {
                    durationInfo += "Total duration of appointments: " + contactDuration.toHoursPart() + " hours, " + contactDuration.toMinutesPart() + " minutes\n";

                }
            }
        }


        for(String s : contacts) {
            int count = 0;

            for (Appointment a : timeReport) {
                if (s.equals(ALL_TYPES)) {
                    continue;
                } else if (a.getType().equals(s)) {
                    count++;
                }

            }
            if (count > 0) {
                durationInfo += "\n" + s + ": " + count + " appointment(s)";
            }
        }

//        durationInfo += "\n";
//        Label label = new Label(durationInfo);
//        label.setStyle("-fx-font-weight: bold");
//        reportInfoBox.getChildren().add(label);

        timeReportTable.getSortOrder().add(timeContactIDCol);
        timeReportTable.getSortOrder().add(timeCustIDCol);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Time Report");
        alert.setHeaderText(contactInfo);
        alert.setContentText(durationInfo);
        alert.showAndWait();

    }


    /**
     * Brings the appropriate report table to the front.
     */
    @FXML
    private void bringTableToFront() {
        reportInfoBox.getChildren().clear();

        if (accordion.getExpandedPane().equals(customerPane)) {
            customerReportTable.toFront();
        } else if (accordion.getExpandedPane().equals(contactPane)) {
            contactReportTable.toFront();
        } else if (accordion.getExpandedPane().equals(timePane)){
            timeReportTable.toFront();
        }
    }


    /**
     * Builds the type comboBox on the reports tab.
     */
    @FXML
    private void buildTypeComboBox() {
        String selectedCustomer = comboCustomer.getSelectionModel().getSelectedItem();
        int customerID = -1;
        for (Customer c : Data.getAllCustomers()) {
            if (c.getName().equals(selectedCustomer)) {
                customerID = c.getCustomerID();
                break;
            }
        }

        //set type combobox
        ObservableList<String> type = FXCollections.observableArrayList();

        /**
         * discussion of lambda
         * This lambda expression compares a string to all strings in an ObservableList and returns true if it is unique or false if found in the list.
         */
        Unique check = s -> {
            for (String str : type) {
                if (str.equals(s)) {
                    return false;
                }
            }
            return true;
        };

        for (Appointment appointment : Data.getAllAppointments()) {
            if (selectedCustomer.equals(ALL_CUSTOMERS)) {
                if (check.checkUnique(appointment.getType()) ) {
                    type.add(appointment.getType());
                }
            } else {
                if (appointment.getCustomerID() == customerID && check.checkUnique(appointment.getType()) ) {
                    type.add(appointment.getType());
                }
            }
        }

        Collections.sort(type, Collator.getInstance());
        type.add(0, ALL_TYPES);
        comboType.setItems(type);
        comboType.getItems().addAll();
        comboType.setValue(ALL_TYPES);

    }

    /**
     * Build customer list on time report pane in the main window.
     */
    @FXML
    private void buildCustomerComboBox() {
        String selectedContact = comboTimeContact.getSelectionModel().getSelectedItem();
        int contactID = -1;
        for (Contact c : Data.getAllContacts()) {
            if (c.getName().equals(selectedContact)) {
                contactID = c.getContactID();
                break;
            }
        }

        //set customer combobox
        ObservableList<String> customer = FXCollections.observableArrayList();

        Unique check = s -> {
            for (String str : customer) {
                if (str.equals(s)) {
                    return false;
                }
            }
            return true;
        };


        for (Appointment appointment : Data.getAllAppointments()) {
            if (selectedContact.equals(ALL_CONTACTS)) {
                if (check.checkUnique(appointment.getCustomerName()) ) {
                    customer.add(appointment.getCustomerName());
                }
            } else {
                if (appointment.getContactID() == contactID && check.checkUnique(appointment.getCustomerName()) ) {
                    customer.add(appointment.getCustomerName());
                }
            }
        }

        Collections.sort(customer, Collator.getInstance());
        customer.add(0, ALL_CUSTOMERS);
        comboTimeCustomer.setItems(customer);
        comboTimeCustomer.getItems().addAll();
        comboTimeCustomer.setValue(ALL_CUSTOMERS);
    }


    private interface Unique {
        boolean checkUnique(String s);
    }

}
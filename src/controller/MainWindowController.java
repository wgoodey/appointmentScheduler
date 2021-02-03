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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

/**
 * @author Whitney Goodey
 * @version 1.0
 * @since 1.0
 * <p>
 * The MainWindowController class manages the loading of all tables and report generation. Customer and appointment creation, modification, and deletion are initiated here.
 * <p>
 * For discussion of lambda expressions see {@link #initialize} and {@link #buildTypeComboBox}.
 */
public class MainWindowController {

    Parent root;

    /**
     * The searchbar for the customer table.
     */
    @FXML
    private TextField customerSearchBar;

    /**
     * The table for customer data.
     */
    @FXML
    private TableView<Customer> customerTable;
    /**
     * The customerID column of the customer table
     */
    @FXML
    private TableColumn<Customer, Integer> customerIDCol;
    /**
     * The customer name column of the customer table
     */
    @FXML
    private TableColumn<Customer, String> nameCol;
    /**
     * The country column of the customer table
     */
    @FXML
    private TableColumn<Customer, String> countryCol;
    /**
     * The address column of the customer table
     */
    @FXML
    private TableColumn<Customer, String> addressCol;
    /**
     * The postal code column of the customer table
     */
    @FXML
    private TableColumn<Customer, String> postalCol;
    /**
     * The division column of the customer table
     */
    @FXML
    private TableColumn<Customer, String> divisionCol;
    /**
     * The phone number column of the customer table
     */
    @FXML
    private TableColumn<Customer, String> phoneCol;
    /**
     * The searchbar for the appointment table.
     */
    @FXML
    private TextField appSearchBar;
    /**
     * The checkbox that includes/excludes appointments in the appointment table.
     */
    @FXML
    private CheckBox pastCheckBox;
    /**
     * The toggle group for the {@link #radioAll}, {@link #radioMonth}, and {@link #radioWeek} radio buttons.
     */
    @FXML
    private ToggleGroup appFilter;
    /**
     * The radio button that shows all appointments in the appointment table.
     */
    @FXML
    private RadioButton radioAll;
    /**
     * The radio button that shows only this month's appointments in the appointment table.
     */
    @FXML
    private RadioButton radioMonth;
    /**
     * The radio button that shows only this week's appointments in the appointment table.
     */
    @FXML
    private RadioButton radioWeek;
    /**
     * The table for appointment data.
     */
    @FXML
    private TableView<Appointment> appointmentTable;
    /**
     * The appointment ID column of the appointment table
     */
    @FXML
    private TableColumn<Appointment, Integer> appIDCol;
    /**
     * The appointment title column of the appointment table
     */
    @FXML
    private TableColumn<Appointment, String> titleCol;
    /**
     * The appointment description column of the appointment table
     */
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    /**
     * The appointment location column of the appointment table
     */
    @FXML
    private TableColumn<Appointment, String> locationCol;
    /**
     * The appointment contact column of the appointment table
     */
    @FXML
    private TableColumn<Appointment, Integer> contactCol;
    /**
     * The appointment type column of the appointment table
     */
    @FXML
    private TableColumn<Appointment, String> typeCol;
    /**
     * The appointment start time column of the appointment table
     */
    @FXML
    private TableColumn<Appointment, String> startCol;
    /**
     * The appointment end time column of the appointment table
     */
    @FXML
    private TableColumn<Appointment, String> endCol;
    /**
     * The appointment customer ID column of the appointment table
     */
    @FXML
    private TableColumn<Appointment, Integer> custIDCol;
    /**
     * The button to click if adding a customer.
     */
    @FXML
    private Button newCustomerButton;
    /**
     * The button to click if modifying a customer.
     */
    @FXML
    private Button modifyCustomerButton;
    /**
     * The button to click if deleting a customer.
     */
    @FXML
    private Button deleteCustomerButton;
    /**
     * The button to click if adding an appointment.
     */
    @FXML
    private Button newAppointmentButton;
    /**
     * The button to click if modifying an appointment.
     */
    @FXML
    private Button modifyAppointmentButton;
    /**
     * The button to click if deleting an appointment.
     */
    @FXML
    private Button deleteAppointmentButton;

    /**
     * The table for customer report data.
     */
    @FXML
    private TableView customerReportTable;
    /**
     * The customer ID column for the customer report table.
     */
    @FXML
    private TableColumn<Appointment, Integer> crCustIDCol;
    /**
     * The customer name column for the customer report table.
     */
    @FXML
    private TableColumn<Appointment, String> crNameCol;
    /**
     * The appointment ID column for the customer report table.
     */
    @FXML
    private TableColumn<Appointment, Integer> crIDCol;
    /**
     * The title column for the customer report table.
     */
    @FXML
    private TableColumn<Appointment, String> crTitleCol;
    /**
     * The type column for the customer report table.
     */
    @FXML
    private TableColumn<Appointment, String> crTypeCol;
    /**
     * The start time column for the customer report table.
     */
    @FXML
    private TableColumn<Appointment, String> crStartCol;


    /**
     * The table for contact report data.
     */
    @FXML
    private TableView contactReportTable;
    /**
     * The contact ID column for the contact report table.
     */
    @FXML
    private TableColumn<Appointment, Integer> corContactID;
    /**
     * The contact name column for the contact report table.
     */
    @FXML
    private TableColumn<Appointment, String> corNameCol;
    /**
     * The appointment ID column for the contact report table.
     */
    @FXML
    private TableColumn<Appointment, Integer> corIDCol;
    /**
     * The title column for the contact report table.
     */
    @FXML
    private TableColumn<Appointment, String> corTitleCol;
    /**
     * The type column for the contact report table.
     */
    @FXML
    private TableColumn<Appointment, String> corTypeCol;
    /**
     * The description column for the contact report table.
     */
    @FXML
    private TableColumn<Appointment, String> corDescrCol;
    /**
     * The start time column for the contact report table.
     */
    @FXML
    private TableColumn<Appointment, String> corStartCol;
    /**
     * The end time column for the contact report table.
     */
    @FXML
    private TableColumn<Appointment, String> corEndCol;
    /**
     * The customer ID column for the contact report table.
     */
    @FXML
    private TableColumn<Appointment, Integer> corCustIDCol;


    /**
     * The table for time report data.
     */
    @FXML
    private TableView timeReportTable;
    /**
     * The contact ID column for the time report table.
     */
    @FXML
    private TableColumn<Appointment, Integer> timeContactIDCol;
    /**
     * The start time column for the time report table.
     */
    @FXML
    private TableColumn<Appointment, Integer> timeIDCol;
    /**
     * The title column for the time report table.
     */
    @FXML
    private TableColumn<Appointment, String> timeTitleCol;
    /**
     * The type column for the time report table.
     */
    @FXML
    private TableColumn<Appointment, String> timeTypeCol;
    /**
     * The description column for the time report table.
     */
    @FXML
    private TableColumn<Appointment, String> timeDescrCol;
    /**
     * The start time column for the time report table.
     */
    @FXML
    private TableColumn<Appointment, String> timeStartCol;
    /**
     * The end time column for the time report table.
     */
    @FXML
    private TableColumn<Appointment, String> timeEndCol;
    /**
     * The customer ID column for the time report table.
     */
    @FXML
    private TableColumn<Appointment, Integer> timeCustIDCol;


    /**
     * The accordion menu for the report pane.
     */
    @FXML
    private Accordion accordion;
    /**
     * The customer pane for the accordion.
     */
    @FXML
    private TitledPane customerPane;
    /**
     * The contact pane for the accordion.
     */
    @FXML
    private TitledPane contactPane;
    /**
     * The time pane for the accordion.
     */
    @FXML
    private TitledPane timePane;


    /**
     * The comboBox for customers.
     */
    @FXML
    private ComboBox<String> comboCustomer;
    /**
     * The comboBox for appointment types.
     */
    @FXML
    private ComboBox<String> comboType;
    /**
     * The radio for filtering appointments by month on the customer pane.
     */
    @FXML
    private RadioButton radioMonthCustomer;
    /**
     * The comboBox for contacts.
     */
    @FXML
    private ComboBox<String> comboContact;
    /**
     * The radio for filtering appointments by month on the contact pane.
     */
    @FXML
    private RadioButton radioMonthContact;
    /**
     * The checkbox that includes/excludes past appointments in the contact schedules.
     */
    @FXML
    private CheckBox checkBoxPastReport;
    /**
     * The comboBox for customers in the time pane.
     */
    @FXML
    private ComboBox<String> comboTimeCustomer;
    /**
     * The comboBox for contacts in the time pane.
     */
    @FXML
    private ComboBox<String> comboTimeContact;
    /**
     * The radio for filtering appointments by month on the time pane.
     */
    @FXML
    private RadioButton radioMonthTime;

    @FXML
    private VBox reportInfoBox;


    /**
     * Formats times for the current month.
     */
    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-M");
    /**
     * Formats times for the current week.
     */
    private final DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("yyyy-w");


    /**
     * Filtered list of appointments for the appointment table.
     */
    private FilteredList<Appointment> filteredAppointments;
    /**
     * Sorted list of appointments for the appointment table.
     */
    private SortedList<Appointment> sortedAppointments;
    /**
     * Collection of appointments for the customer report table.
     */
    private final ObservableList<Appointment> customerReport = FXCollections.observableArrayList();
    /**
     * Collection of appointments for the contact report table.
     */
    private final ObservableList<Appointment> contactReport = FXCollections.observableArrayList();
    /**
     * Collection of appointments for the time report table.
     */
    private final ObservableList<Appointment> timeReport = FXCollections.observableArrayList();


    /**
     * Predicate that clears filters.
     */
    private final Predicate<Appointment> clearFilter = appointment -> true;
    /**
     * Predicate that filters to future appointments.
     */
    private final Predicate<Appointment> future = appointment -> appointment.getStartTime().isAfter(ZonedDateTime.now());
    /**
     * Predicate that filters to current month's appointments.
     */
    private final Predicate<Appointment> month = appointment -> appointment.getStartTime().format(monthFormatter).equals(ZonedDateTime.now().format(monthFormatter));
    /**
     * Predicate that filters to current week's appointments.
     */
    private final Predicate<Appointment> week = appointment -> appointment.getStartTime().format(weekFormatter).equals(ZonedDateTime.now().format(weekFormatter));
    /**
     * Predicate that holds whether past appointments are included/excluded.
     */
    private Predicate<Appointment> pastCheck = clearFilter;
    /**
     * Predicate that filters appointments based on user needs.
     */
    private Predicate<Appointment> timeRange = clearFilter;


    private final String ALL_CUSTOMERS = "All Customers";
    private final String ALL_TYPES = "All Types";
    private final String ALL_CONTACTS = "All Contacts";



    /**
     * Loads tableViews and comboBoxes into the main window.
     * <p>
     * discussion of lambda {customerSearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> filteredCustomers.setPredicate(customer ->}<br>
     * This lambda expression adds a listener to the customer searchBar and sets the predicate on the filteredCustomers list based on the text in the searchBar.
     * <p>
     * discussion of lambda {appSearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> filteredAppointments.setPredicate(timeRange.and(pastCheck.and(appointment ->}<br>
     * This lambda expression adds a listener to the searchBar and sets the predicate on the filteredAppointments list based on the text in the appointment searchBar, taking into account the time scope selected by the user.
     * <p>
     * discussion of lambda {pastCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) ->}<br>
     * This lambda expression adds a listener to the pastCheckBox and sets the predicate on the filteredAppointments list based on the time scope selected by the user.
     * <p>
     * discussion of lambda {appFilter.selectedToggleProperty().addListener((observableValue, toggle, t1) ->}<br>
     * This lambda expression adds a listener to the toggleGroup and sets the predicate on the filteredAppointments list based on the time scope selected by the user.
     */
    @FXML
    public void initialize() {

        //set tableView for all customers in the list
        loadCustomerTable(customerTable, customerIDCol, nameCol, countryCol, addressCol, postalCol, divisionCol, phoneCol);

        //wrap observable list in a filtered list
        FilteredList<Customer> filteredCustomers = new FilteredList<>(Data.getAllCustomers(), p -> true);

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
     * @param customerTable the table to load.
     * @param customerIDCol the customerID column of the table.
     * @param nameCol the name column of the table.
     * @param countryCol the country column of the table.
     * @param addressCol the address column of the table.
     * @param postalCol the postal code column of the table.
     * @param divisionCol the division column of the table.
     * @param phoneCol the phone column of the table.
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
     * @param appointmentTable the table to be loaded.
     * @param appIDCol the appointment ID column of the table.
     * @param titleCol the title column of the table.
     * @param descriptionCol the description column of the table.
     * @param locationCol the location column of the table.
     * @param contactCol the contact column of the table.
     * @param typeCol the the type column of the table.
     * @param startCol the start column of the table.
     * @param endCol the end column of the table.
     * @param custIDCol the customerID column of the table.
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
     * @param customerReportTable the table to load.
     * @param crIDCol the appointmentID column of the table.
     * @param crCustIDCol the customerID column of the table.
     * @param crNameCol the customer name column of the table.
     * @param crTitleCol the appointment title column of the table.
     * @param crTypeCol the appointment type column of the table.
     * @param crStartCol the appointment start time column of the table.
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
     * @param contactReportTable the table to load.
     * @param corContactID the contactID column of the table.
     * @param corName the contact name column of the table.
     * @param corIDCol the appointmentID column of the table.
     * @param corTitleCol the appointment title column of the table.
     * @param corTypeCol the appointment type column of the table.
     * @param corDescrCol the appointment description column of the table.
     * @param corStartCol the appointment start time column of the table.
     * @param corEndCol the appointment start time column of the table.
     * @param corCustIDCol the customer ID column of the table.
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
     * @param timeReportTable the table to load.
     * @param corContactID the contactID column of the table.
     * @param corIDCol the appointment ID column of the table.
     * @param corTitleCol the appointment title column of the table.
     * @param corTypeCol the appointment type column of the table.
     * @param corDescrCol the appointment description column of the table.
     * @param corStartCol the appointment start time column of the table.
     * @param corEndCol the appointment end time column of the table.
     * @param corCustIDCol the customer ID column of the table.
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
     * @throws IOException if IO error occurs
     * @throws SQLException if the database query cannot be executed.
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
     * @throws IOException if IO error occurs.
     * @throws SQLException if the database query cannot be executed.
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
     * @throws IOException if IO error occurs.
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
     * @throws IOException if IO error occurs.
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
     * @throws IOException if IO error occurs.
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
     * @throws IOException if IO error occurs.
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

        customers.sort(Collator.getInstance());
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
        contacts.sort(Collator.getInstance());
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
                if (a.getType().equals(s)) {
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
                if (a.getType().equals(s)) {
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
     * <p>
     * discussion of lambda {Unique check = s ->}<br>
     * This lambda expression compares a string to all strings in an ObservableList and returns true if it is unique or false if found in the list.
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

        type.sort(Collator.getInstance());
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

        customer.sort(Collator.getInstance());
        customer.add(0, ALL_CUSTOMERS);
        comboTimeCustomer.setItems(customer);
        comboTimeCustomer.getItems().addAll();
        comboTimeCustomer.setValue(ALL_CUSTOMERS);
    }

    /**
     * A general interface to check true/false against a String.
     */
    private interface Unique {
        boolean checkUnique(String s);
    }

}
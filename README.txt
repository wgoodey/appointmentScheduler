Appointment Scheduler
Version 1.0 (1.0)

Author: Whitney Goodey
email: wgoodey@wgu.edu


The Appointment Scheduler application allows the user to manage a database of customers and their associated appointments. Customer and appointment data in the application are synchronized with the user's database. The application is able to generate various reports related to customers and appointments.

Operation

A login form is loaded at application start. If a correct username and password combination is supplied, the main application will load. Usernames and passwords are managed at the database level. A log of all login attempts is recorded in the file login_activity.txt.

Upon logging in an alert will inform the user whether or not they have an appointment in the next 15 minutes. The main window has three tabs: Customers, Appointments, and Reports

Customers tab:
This tab shows all customer information. Customers can be filtered by customer name or ID. Customer information can be added, modified, and deleted from this tab. If a customer is deleted all associated appointments will also be deleted. Customer country and regional information are managed at the database level. All fields on the form are required to create a customer record.

Appointments tab:
This tab shows all customer appointment information. Appointments can be filtered by title or description. The appointments shown can be also be further filtered by time range, selected by the user. The Month and week radios will show all appointments in the current month or week. Appointments can be added, modified, and deleted from this tab. A customer must be in the system before an appointment can be created for them. When creating a new appointment, all fields on the form are required and the appointment time must be within headquarters business hours to create an appointment record.

Reports tab:
This tab allows the user to generate three types of reports. The user selects the type of report from the accordion menu on the left. Related appointment records are displayed in a table on the right.

The Customer pane generates reports based on the selected customer(s) and appointment type(s). An alert will be displayed with a summary of the data displayed in the table. Related appointment records are displayed in a table on the right.

The Contacts pane generates a schedule for the selected contact(s). The Scope radio can be used to show all associated appointments or filter appointments to the current month. By default, the table displays future appointments, but past appointments can be included by checking the the Include Past checkbox. Results are sorted by Contact Name first and Start Time second by default.

The Time pane generates reports based on the selected contact(s) and customer(s). The Scope radio can be used to show all associated appointments or filter appointments to the current month. An alert will be displayed with a summary of the total number of appointments and total duration spent in appointments. Related appointment records are displayed in a table on the right.


Development information
IDE: IntelliJ IDEA 2020.3.2 (Ultimate Edition)
JDK: Oracle JDK 11 (Java SE 11.0.8)
JFX: JavaFX Windows SDK 11.0.2
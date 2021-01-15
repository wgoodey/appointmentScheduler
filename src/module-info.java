module appointmentScheduler {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;

    opens com.whitneygoodey.appointmentScheduler;
    opens view;
    opens controller;
    opens model;
}
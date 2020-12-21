module appointmentScheduler {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens com.whitneygoodey.appointmentScheduler;
    opens view;
    opens controller;
    opens model;
}
module appointmentScheduler {
    requires javafx.fxml;
    requires javafx.controls;

    opens com.whitneygoodey.appointmentScheduler;
    opens view;
    opens controller;
    opens model;
}
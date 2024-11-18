module lk.gdse.hotelmanagement.crystalwave {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires jbcrypt;
    requires static lombok;

    exports lk.gdse.hotelmanagement.crystalwave; // Export the root package
    exports lk.gdse.hotelmanagement.crystalwave.controller.HomeControllers; // Export the controllers package
    exports lk.gdse.hotelmanagement.crystalwave.dto.tm; // Export the controllers package
    opens lk.gdse.hotelmanagement.crystalwave.controller.HomeControllers to javafx.fxml;
    exports lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;
    opens lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers to javafx.fxml;
    exports lk.gdse.hotelmanagement.crystalwave.controller.ReceptionistControllers;
    opens lk.gdse.hotelmanagement.crystalwave.controller.ReceptionistControllers to javafx.fxml;
    exports lk.gdse.hotelmanagement.crystalwave.Database;
    opens lk.gdse.hotelmanagement.crystalwave.Database to javafx.fxml; // Open the controllers package to javafx.fxml for reflection
}

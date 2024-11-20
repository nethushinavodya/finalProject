module lk.gdse.hotelmanagement.crystalwave {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires jbcrypt;
    requires spring.security.crypto;
    requires static lombok;
    requires net.sf.jasperreports.core;

    exports lk.gdse.hotelmanagement.crystalwave;
    exports lk.gdse.hotelmanagement.crystalwave.controller.HomeControllers;
    exports lk.gdse.hotelmanagement.crystalwave.dto.tm;
    opens lk.gdse.hotelmanagement.crystalwave.controller.HomeControllers to javafx.fxml;
    exports lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers;
    opens lk.gdse.hotelmanagement.crystalwave.controller.AdminControllers to javafx.fxml;
    exports lk.gdse.hotelmanagement.crystalwave.controller.ReceptionistControllers;
    opens lk.gdse.hotelmanagement.crystalwave.controller.ReceptionistControllers to javafx.fxml;
}

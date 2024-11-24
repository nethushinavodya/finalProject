package lk.gdse.hotelmanagement.crystalwave;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.gdse.hotelmanagement.crystalwave.controller.HomeControllers.LoginController;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

       /* Parent load = FXMLLoader.load(getClass().getResource("/AdminView/InventoryManagement.fxml"));
*/
/*

        Parent load = FXMLLoader.load(getClass().getResource("/ReceptionistView/ReceptionistDashboard.fxml"));
*/        /*Parent load = FXMLLoader.load(getClass().getResource("/ReceptionistView/ServiceReservation.fxml"));
*/

        Parent load = FXMLLoader.load(getClass().getResource("/HomeView/LoginPage.fxml"));
        Scene scene = new Scene(load);
        stage.setTitle("CrystalWave Hotel Login");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}

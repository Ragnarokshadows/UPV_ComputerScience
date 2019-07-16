/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managementapplication;

import DBAccess.ClinicDBAccess;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Stéphane
 */
public class ManagementApplication extends Application {
    
    private ButtonType buttonTypeOK;
    private ButtonType buttonTypeCancel;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("ManagementApplication.fxml"));
        
        Parent root = myLoader.load();
        
        ManagementApplicationController managementApplicationController = myLoader.<ManagementApplicationController>getController();
        managementApplicationController.initStage(stage);
        
        ClinicDBAccess clinic =  ClinicDBAccess.getSingletonClinicDBAccess();
        
        Scene scene = new Scene(root);
        stage.setOnCloseRequest((WindowEvent event) ->{
            Alert conf = new Alert(AlertType.CONFIRMATION);
            conf.setTitle("Confirmation");
            conf.setHeaderText("You are about to exit");
            conf.setContentText("Do you want to continue?");
            
            buttonTypeOK = new ButtonType("OK", ButtonData.OK_DONE);
            buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            conf.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
            
            Optional<ButtonType> result = conf.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeOK){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(clinic.getClinicName());
                alert.setHeaderText("Saving data in DB");
                alert.setContentText("The application is saving the changes in the data into the database. This action can expend some minutes.");
                alert.getButtonTypes().setAll(buttonTypeOK);
                alert.show();
                clinic.saveDB();
            } else {
                event.consume();
            }
        });
        stage.setScene(scene);
        stage.setMinWidth(783);
        stage.setMinHeight(640);

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

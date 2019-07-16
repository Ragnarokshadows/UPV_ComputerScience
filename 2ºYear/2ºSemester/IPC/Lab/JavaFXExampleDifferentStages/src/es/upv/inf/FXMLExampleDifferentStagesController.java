/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upv.inf;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author svalero
 */
public class FXMLExampleDifferentStagesController implements Initializable {
    
    @FXML
    private Button buttonOpenWin1;
    @FXML
    private Button butonClose;
    
    private Stage primaryStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void initMainWindow(Stage stage){
            primaryStage = stage;
    
    }
    @FXML
    private void handleOnActionButtonOpenWin1(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLWindow1.fxml"));
        VBox root = (VBox) myLoader.load();
        FXMLWindow1Controller win1Controller = myLoader.<FXMLWindow1Controller>getController();
        
        Stage winStage = new Stage();
        win1Controller.initWindow1(winStage);
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        winStage.setScene(scene);
        winStage.setTitle("Window1");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
        
        
        
    }

    @FXML
    private void handleOnActionButtonClose(ActionEvent event) {
        primaryStage.close();
    }
    
    
}

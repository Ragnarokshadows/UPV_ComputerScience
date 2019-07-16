/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upv.inf;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author svalero
 */
public class FXMLWindow2Controller implements Initializable {

    @FXML
    private Button buttonBaclk;
    private Stage winStage;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void initWindow2(Stage stage){
        winStage = stage;

    }
    @FXML
    private void handleOnActionButtonBack(ActionEvent event) {
   
        winStage.hide();
    }
    
}

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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author svalero
 */
public class FXMLWindow1Controller implements Initializable {

    @FXML
    private Button buttonOpenWin2;
    @FXML
    private Button buttonBaclk;

    private Stage winStage;
    private Scene primaryScene;
    private String primaryTitle;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    //Gets inital data from main window
    public void initWindow1(Stage stage){
        winStage = stage;

        
        
    
    }
    @FXML
    private void handleOnActionButtonOpenWin2(ActionEvent event) throws IOException {
        //Load UI objects
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLWindow2.fxml"));
        VBox root = (VBox)myLoader.load();
        
        //Access to wind2 controller and init win2
        FXMLWindow2Controller win2Controller = myLoader.<FXMLWindow2Controller>getController();
        
        Stage win2Stage = new Stage();
        win2Controller.initWindow2(win2Stage);
        
        //Show win2
        Scene scene = new Scene(root);
        win2Stage.setScene(scene);
        win2Stage.setTitle("Window 2");
        win2Stage.initModality(Modality.APPLICATION_MODAL);
        win2Stage.show();
        
       
        
        
        
        
    }

    @FXML
    private void handleOnActionButtonBack(ActionEvent event) {
            winStage.hide();
    }
    
}

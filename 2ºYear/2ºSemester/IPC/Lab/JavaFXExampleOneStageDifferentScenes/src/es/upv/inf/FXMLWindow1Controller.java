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

    private Stage primaryStage;
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
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        
        
    
    }
    @FXML
    private void handleOnActionButtonOpenWin2(ActionEvent event) throws IOException {
        //Load UI objects
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLWindow2.fxml"));
        VBox root = (VBox)myLoader.load();
        
        //Access to wind2 controller and init win2
        FXMLWindow2Controller win2Controller = myLoader.<FXMLWindow2Controller>getController();
        win2Controller.initWindow2(primaryStage);
        
        //Show win2
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Window 2");
        primaryStage.show();
        
       
        
        
        
        
    }

    @FXML
    private void handleOnActionButtonBack(ActionEvent event) {
            primaryStage.setScene(primaryScene);
            primaryStage.setTitle(primaryTitle); 
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upv.inf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author svalero
 */


public class JavaFXExampleDifferentStages extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLExampleDifferentStages.fxml"));
        //We load the UI ojects
        Parent root = myLoader.load();  
        
        //Now we can get the controller of the main window
        FXMLExampleDifferentStagesController mainWinController = myLoader.<FXMLExampleDifferentStagesController>getController();
        //Store the current stage as mainStage
         mainWinController.initMainWindow(stage);
        
        //We create and load the main scene
        Scene scene = new Scene(root);
        stage.setTitle("Example one stage multiple scenes");
        stage.setScene(scene);
       //Show the stage/window
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

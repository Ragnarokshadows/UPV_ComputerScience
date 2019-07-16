/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upv.inf;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author svalero
 */
public class FXMLSliderTextFieldController implements Initializable {

    @FXML
    private VBox vbContainer;
    @FXML
    private TextField textFieldValue;
    @FXML
    private Slider sliderValue;
    @FXML
    private Label labelValue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Enlazamaos el valor de la label a lo que valga el campo de edición
        //Bind the label value with the edit field
        labelValue.textProperty().bind(textFieldValue.textProperty());
        
        //El campo de edición y el slider están conectados, un cambio en el valor de uno repercute en el otro
        //The edit field and the slider are connected, a change in the value of them affects the other one
        textFieldValue.textProperty().bindBidirectional(sliderValue.valueProperty(), new NumberStringConverter());
        
   
              
      
        
    }    
    
}

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
        
      /*
        textFieldValue.textProperty().addListener(new ChangeListener(){
          @Override
          public void changed(ObservableValue o,Object oldVal, Object newVal){
              labelValue.setText(newVal +"");
          }
      });
        */  
      textFieldValue.textProperty().addListener((observable,oldValue,newValue) -> {labelValue.setText(newValue +"");});
      
      sliderValue.valueProperty().addListener((observable,oldValue,newValue) ->{labelValue.setText(newValue+"");});
        
    }    
    
}

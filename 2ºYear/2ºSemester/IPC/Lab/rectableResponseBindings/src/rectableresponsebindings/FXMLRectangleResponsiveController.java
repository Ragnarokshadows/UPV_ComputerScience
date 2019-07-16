/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectableresponsebindings;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Soledad
 */
public class FXMLRectangleResponsiveController implements Initializable {
    
    @FXML
    private StackPane stackPane;
    @FXML
    private Rectangle responsiveRectangle;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        responsiveRectangle.widthProperty().bind(
                Bindings.divide(stackPane.widthProperty(),2));
        responsiveRectangle.heightProperty().bind(
                Bindings.divide(stackPane.heightProperty(),2)
        );
        
    }    
    
}

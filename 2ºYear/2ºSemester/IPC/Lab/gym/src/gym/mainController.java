/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gym;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Stéphane
 */
public class mainController implements Initializable {
    
    @FXML
    private Button startButton;
    @FXML
    private Button templateButton;
    @FXML
    private Button groupButton;
    @FXML
    private Button dataButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonHandler(ActionEvent event) {
    }
    
}

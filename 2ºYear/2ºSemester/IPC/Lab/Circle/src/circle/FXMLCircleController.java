/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package circle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Stéphane
 */
public class FXMLCircleController implements Initializable {
    @FXML
    private GridPane grid;
    @FXML
    private Circle purpleCircle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void move(KeyEvent event) {
        switch(event.getCode()){
            case UP: grid.setRowIndex(purpleCircle,(GridPane.getRowIndex(purpleCircle) + 4)%5);break;
            case DOWN: grid.setRowIndex(purpleCircle,(GridPane.getRowIndex(purpleCircle) + 1)%5);break;
            case RIGHT: grid.setColumnIndex(purpleCircle,(GridPane.getColumnIndex(purpleCircle) + 1)%5);break;
            case LEFT: grid.setColumnIndex(purpleCircle,(GridPane.getColumnIndex(purpleCircle) + 4)%5);break;
                
        }
    }

    @FXML
    private void moveClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        double size = 600/5;
        
        if(y >=0 && y < size) grid.setRowIndex(purpleCircle,0);
        if(y >=size && y < 2*size) grid.setRowIndex(purpleCircle,1);
        if(y >=2*size && y < 3*size) grid.setRowIndex(purpleCircle,2);
        if(y >=3*size && y < 4*size) grid.setRowIndex(purpleCircle,3);
        if(y >=4*size && y <= 5*size) grid.setRowIndex(purpleCircle,4);
        
        if(x >=0 && x < size) grid.setColumnIndex(purpleCircle,0);
        if(x >=size && x < 2*size) grid.setColumnIndex(purpleCircle,1);
        if(x >=2*size && x < 3*size) grid.setColumnIndex(purpleCircle,2);
        if(x >=3*size && x < 4*size) grid.setColumnIndex(purpleCircle,3);
        if(x >=4*size && x <= 5*size) grid.setColumnIndex(purpleCircle,4);
    }    
}

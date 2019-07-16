/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Stéphane
 */
public class FXMLCalculatorController implements Initializable {
    @FXML
    private TextField result;
    private String operation;
    private double hide;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hide = 0;
        operation="";
    }    

    @FXML
    private void reset(ActionEvent event) {
        result.setText("0");
    }

    @FXML
    private void division(ActionEvent event) {
        operation+="/";
        result.setText(operation);
    }

    @FXML
    private void multiplication(ActionEvent event) {
        operation+="x";
        result.setText(operation);
    }

    @FXML
    private void substract(ActionEvent event) {
        operation+="-";
        result.setText(operation);}

    @FXML
    private void add(ActionEvent event) {
        operation+="+";
        result.setText(operation);
    }

    @FXML
    private void result(ActionEvent event) {
        result.setText("" + hide);
    }

    @FXML
    private void zero(ActionEvent event) {
        operation+="0";
        result.setText(operation);
    }

    @FXML
    private void coma(ActionEvent event) {
        operation+=",";
    }

    @FXML
    private void one(ActionEvent event) {
        operation+="1";
        result.setText(operation);
    }

    @FXML
    private void four(ActionEvent event) {
        operation+="4";
        result.setText(operation);
    }

    @FXML
    private void seven(ActionEvent event) {
        operation+="7";
        result.setText(operation);
    }

    @FXML
    private void eight(ActionEvent event) {
        operation+="8";
        result.setText(operation);
    }

    @FXML
    private void nine(ActionEvent event) {
        operation+="9";
        result.setText(operation);
    }

    @FXML
    private void five(ActionEvent event) {
        operation+="5";
        result.setText(operation);
    }

    @FXML
    private void two(ActionEvent event) {
        operation+="2";
        result.setText(operation);
    }

    @FXML
    private void six(ActionEvent event) {
        operation+="6";
        result.setText(operation);
    }

    @FXML
    private void three(ActionEvent event) {
        operation+="3";
        result.setText(operation);
    }
    
    private void calculate(){
    }
    
    
}

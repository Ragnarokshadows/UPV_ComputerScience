/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.upv.inf;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Stéphane
 */
public class FXMLDetailsViewController implements Initializable {
    
    private ObservableList<Persona> observableListPersonData;
    private int selectedIndex;
    private boolean edit = false;
    
    @FXML
    private TextField editName;
    @FXML
    private TextField editSurname;
    @FXML
    private Button buttonOk;
    @FXML
    private Button buttonCancel;
    
    public void initData ( ObservableList<Persona> listaPersonas , int index){
        observableListPersonData = listaPersonas;
        if(index>=0){
            editName.textProperty().setValue(observableListPersonData.get(index).getNombre());
            editSurname.textProperty().setValue(observableListPersonData.get(index).getApellidos());       
            selectedIndex = index;
            edit = true;
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionButtonOk(ActionEvent event) {
        if(((Node)event.getSource()).getId().equals("buttonOk") && !editName.getText().equals("") && !editSurname.getText().equals("")){
            Persona a = new Persona(editName.getText(), editSurname.getText());
            if(edit)
                observableListPersonData.set(selectedIndex, a);
            else 
                observableListPersonData.add(a);
        } 
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
}

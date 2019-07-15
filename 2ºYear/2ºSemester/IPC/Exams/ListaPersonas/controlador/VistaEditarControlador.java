/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListaPersonas.controlador;

import ListaPersonas.modelo.Persona;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author carlos
 */
public class VistaEditarControlador implements Initializable {

    @FXML
    private TextField nombreText;
    @FXML
    private TextField apellidosText;
    private ObservableList<Persona> list;
    private int index;
    private Persona persona;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    protected void setList(ObservableList list) {
        setList(list, -1);
    }
    
    protected void setList(ObservableList<Persona> list, int index) {
        this.list = list; // Save attributes correctly
        this.index = index;
        // Copy current values to textViews
        // and initialize persona to hold the current object in edition
        if (index < 0) persona = new Persona("","");
        else {
            nombreText.setText(list.get(index).getNombre());
            apellidosText.setText(list.get(index).getApellidos());
            persona = new Persona(list.get(index));
        }
        persona.NombreProperty().bind(nombreText.textProperty());
        persona.ApellidosProperty().bind(apellidosText.textProperty());
    }
    
    @FXML
    private void aceptarClick(ActionEvent event) {
        if (index < 0) {
            if (!persona.isValid())
                return;
            list.add(persona);
        } else {
            // Update the list with the modified object
            list.set(index, persona);
            
        }
        cancelarClick(event);
    }

    @FXML
    private void cancelarClick(ActionEvent event) {
        ((Stage) ((Node) event.getSource())
                .getScene().getWindow())
                        .close();
    }
}

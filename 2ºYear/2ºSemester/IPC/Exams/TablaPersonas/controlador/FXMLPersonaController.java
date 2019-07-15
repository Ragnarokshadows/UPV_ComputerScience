/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TablaPersonas.controlador;

import TablaPersonas.modelo.Persona;
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
 * @author jsoler
 */
public class FXMLPersonaController implements Initializable {

    @FXML
    private TextField nombreText;
    @FXML
    private TextField apellidosText;

    ObservableList<Persona> list;
    Persona persona;
    int index = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    protected void setList(ObservableList<Persona> list) {
        this.list = list;
        persona = new Persona("", "");
        persona.ApellidosProperty().bind(apellidosText.textProperty());
        persona.NombreProperty().bind(nombreText.textProperty());
    }

    protected void setList(ObservableList<Persona> list, int index) {
        this.index = index;
        this.setList(list);
        apellidosText.setText(list.get(index).getApellidos());
        nombreText.setText(list.get(index).getNombre());
    }

    @FXML
    protected void okAction(ActionEvent event) {
        if (nombreText.getText().isEmpty() || apellidosText.getText().isEmpty()) return;
        if (index == -1)
            list.add(persona);
        else
            list.set(index, persona);
        cancelAction(event);
    }

    @FXML
    protected void cancelAction(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}

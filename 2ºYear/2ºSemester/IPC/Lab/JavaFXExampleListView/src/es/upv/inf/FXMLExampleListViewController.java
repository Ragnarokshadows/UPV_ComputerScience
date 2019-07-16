/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upv.inf;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author svalero
 */
public class FXMLExampleListViewController implements Initializable {

    @FXML
    private TextField editNombre;
    @FXML
    private TextField editApellidos;
    @FXML
    private ListView<Persona> listViewPerson;
    @FXML
    private Button buttonAnyadir;
    @FXML
    private Button buttonBorrar;
    private ObservableList<Persona> observablePersonData = null;

    @FXML
    private void handleOnActionButtonAnyadir(ActionEvent event) {
        observablePersonData.add(new Persona(editNombre.textProperty().getValue(),editApellidos.textProperty().getValue()));
        editApellidos.textProperty().setValue("");
        editNombre.textProperty().setValue("");
        
    }

    @FXML
    private void handleOnActionButtonBorrar(ActionEvent event) {
        int index = listViewPerson.getSelectionModel().selectedIndexProperty().getValue();
        if (index>=0)
            observablePersonData.remove(index);
        
    }
    
    class PersonListCell extends ListCell<Persona> {
        @Override
        protected void updateItem(Persona item, boolean empty){
            super.updateItem(item, empty);
            if(item==null || empty)
                setText(null);
            else
                setText(item.getApellidos() + "," + item.getNombre());
            
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ArrayList<Persona> personData = new ArrayList<Persona>();
        personData.add(new Persona ("Pepe","Navarro"));
        personData.add(new Persona ("Amparo","García"));
        observablePersonData = FXCollections.observableArrayList(personData);
        listViewPerson.setItems(observablePersonData);
        listViewPerson.setCellFactory((c)->{return new PersonListCell();});
        
        //Boton de borrar solo está disponible cuando un elemento está seleccionado
        buttonBorrar.disableProperty().bind(Bindings.equal(-1,listViewPerson.getSelectionModel().selectedIndexProperty()));
        
        //Botón añadir solo disponible cuando han introducido nombre y apelidos
        buttonAnyadir.disableProperty().bind(Bindings.or(editNombre.textProperty().isEmpty(),editApellidos.textProperty().isEmpty()));
        
    }    
    
}

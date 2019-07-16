/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.upv.inf;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Stéphane
 */
public class FXMLExampleListViewController implements Initializable {
    private TextField editNombre;
    private TextField editApellidos;
    
    @FXML
    private ListView<Persona> listViewPerson;
    
    private ObservableList<Persona> observablePersonData;
    
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private Button buttonEdit;
    
    class PersonListCell extends ListCell<Persona> {
        @Override
        protected void updateItem(Persona item, boolean empty){
            super.updateItem(item, empty);
            if(item==null || empty)
                setText(null);
            else
                setText(item.getNombre() + " " + item.getApellidos());
            
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
        buttonEdit.disableProperty().bind(Bindings.equal(-1,listViewPerson.getSelectionModel().selectedIndexProperty()));
        
        buttonRemove.disableProperty().bind(Bindings.equal(-1,listViewPerson.getSelectionModel().selectedIndexProperty()));
    }   

    @FXML
    private void handleOnActionButtonDetails(ActionEvent event) throws IOException {
        //Get the selected item index
        int index = listViewPerson.getSelectionModel().selectedIndexProperty().getValue();
        switch(((Node)event.getSource()).getId()){
            case "buttonAdd": createWindow(-1);break;
            case "buttonEdit": createWindow(index);break;
            case "buttonRemove": observablePersonData.remove(index);break;
        } 
    }
    
    private void createWindow(int index) throws IOException{
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLAddEditView.fxml"));
        Pane root = (Pane) myLoader.load();
            
        //Get the controller of the UI
        FXMLDetailsViewController editController = myLoader.<FXMLDetailsViewController>getController();
        
        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        editController.initData(observablePersonData,index);
        if (index >=0){
            stage.setTitle("Edit person details");
        } else {
            stage.setTitle("Add a new person");
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.show();
  
    }
    
}

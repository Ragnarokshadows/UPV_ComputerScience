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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Stéphane
 */
public class FXMLExampleListViewController implements Initializable {
    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, Integer> idColumn;
    @FXML
    private TableColumn<Person, String> fullNameColumn;
    @FXML
    private TableColumn<Person, Residence> residenceColumn;
    @FXML
    private TableColumn<Person, String> imageColumn;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private Button buttonEdit;
    
    private ObservableList<Person> myData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tableView.setItems(myData);
        myData.add(new Person(999,"John Doe",new Residence("Museros", "Valencia"),"/images/Lloroso.png"));
               
        idColumn.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
        
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("fullName"));
        
        residenceColumn.setCellValueFactory(c -> c.getValue().residenceProperty());
        
        residenceColumn.setCellFactory(v -> new TableCell<Person, Residence>() {
            @Override
            protected void updateItem(Residence item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                setText(null);
                } else {
                setText(item.getCity().toUpperCase());
                }
            }
        });
        
        imageColumn.setCellValueFactory(c -> c.getValue().pathImageProperty());
        
        imageColumn.setCellFactory(c -> new TableCell<Person, String>() {
            private ImageView view = new ImageView();
            @Override protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {setGraphic(null);}
                else {
                Image image= new Image(FXMLExampleListViewController.class.getResourceAsStream(item),40, 40, true, true);
                view.setImage(image);
                setGraphic(view);
                }
            }
        });
        
        buttonEdit.disableProperty().bind(Bindings.equal(-1,tableView.getSelectionModel().selectedIndexProperty()));
        
        buttonRemove.disableProperty().bind(Bindings.equal(-1,tableView.getSelectionModel().selectedIndexProperty()));
       
    }    

    @FXML
    private void handleOnActionButtonDetails(ActionEvent event) throws IOException {
        int index = tableView.getSelectionModel().selectedIndexProperty().getValue();
        switch(((Node)event.getSource()).getId()){
            case "buttonAdd": createWindow(-1);break;
            case "buttonEdit": createWindow(index);break;
            case "buttonRemove": myData.remove(index);break;
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
        editController.initData(myData,index);
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

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
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Stéphane
 */
public class FXMLDetailsViewController implements Initializable {
    @FXML
    private TextField editName;
    @FXML
    private TextField editSurname;
    @FXML
    private TextField editCity;
    @FXML
    private TextField editProvince;
    @FXML
    private ToggleButton cryButton;
    @FXML
    private ToggleButton angryButton;
    @FXML
    private ToggleButton smileButton;
    @FXML
    private Button buttonOk;
    @FXML
    private Button buttonCancel;
    
    private ObservableList<Person> observableListPersonData;
    private int selectedIndex;
    private boolean edit = false;
    private String fullName;
    private String name;
    private String surname;
    private String imagePath;
    private int id;
    private final Image imageCry = new Image(getClass().getResourceAsStream("/images/Lloroso.png"),40, 40, true, true);
    private final Image imageAngry = new Image(getClass().getResourceAsStream("/images/Pregunta.png"),40, 40, true, true);
    private final Image imageSmile = new Image(getClass().getResourceAsStream("/images/Sonriente.png"),40, 40, true, true);
    
    
    
    public void initData ( ObservableList<Person> listaPersonas , int index){
        observableListPersonData = listaPersonas;
        if(index>=0){
            fullName = observableListPersonData.get(index).getFullName();
            int i = fullName.indexOf(" ");
            name = fullName.substring(0, i);
            surname = fullName.substring(i+1);
            editName.textProperty().setValue(name);
            editSurname.textProperty().setValue(surname);
            editCity.textProperty().setValue(observableListPersonData.get(index).getResidence().getCity());
            editProvince.textProperty().setValue(observableListPersonData.get(index).getResidence().getProvince());
            imagePath = observableListPersonData.get(index).getPathImage();
            selectedIndex = index;
            edit = true;
            
            switch(imagePath){
                case "/images/Lloroso.png": cryButton.setSelected(true);break;
                case "/images/Pregunta.png": angryButton.setSelected(true);break;
                case "/images/Sonriente.png": smileButton.setSelected(true);break;
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cryButton.setGraphic(new ImageView(imageCry));
        angryButton.setGraphic(new ImageView(imageAngry));
        smileButton.setGraphic(new ImageView(imageSmile));
        id = 1000;
    }    

    @FXML
    private void imageButton(ActionEvent event) {
        switch(((Node)event.getSource()).getId()) {
            case "cryButton": imagePath ="/images/Lloroso.png";
                cryButton.setSelected(true);
                angryButton.setSelected(false);
                smileButton.setSelected(false);
                break;
            case "angryButton": imagePath ="/images/Pregunta.png";
                cryButton.setSelected(false);
                angryButton.setSelected(true);
                smileButton.setSelected(false);
                break;
            case "smileButton": imagePath ="/images/Sonriente.png";
                cryButton.setSelected(false);
                angryButton.setSelected(false);
                smileButton.setSelected(true);
                break;
        }
    }

    @FXML
    private void onActionButtonOk(ActionEvent event) {
        if(((Node)event.getSource()).getId().equals("buttonOk") && !editName.getText().equals("") && !editSurname.getText().equals("")){
            Residence res = new Residence(editCity.getText(), editProvince.getText());
            Person a;
            if(edit){
                a = new Person(observableListPersonData.get(selectedIndex).getId(), editName.getText() +" "+ editSurname.getText(), res, imagePath);
                observableListPersonData.set(selectedIndex, a);
            }
            else {
                a = new Person(JavaFXListView.id++, editName.getText() +" "+ editSurname.getText(), res, imagePath);
                observableListPersonData.add(a);
            }
        } 
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
}

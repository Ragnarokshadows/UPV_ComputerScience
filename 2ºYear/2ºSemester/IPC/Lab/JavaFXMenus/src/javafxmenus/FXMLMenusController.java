/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javafxmenus;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Stéphane
 */
public class FXMLMenusController implements Initializable {
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem exitMenu;
    @FXML
    private MenuItem amazonMenu;
    @FXML
    private MenuItem blogMenu;
    @FXML
    private MenuItem ebayMenu;
    @FXML
    private MenuItem facebookMenu;
    @FXML
    private MenuItem googleMenu;
    @FXML
    private RadioMenuItem radioAmazon;
    @FXML
    private ToggleGroup buy;
    @FXML
    private RadioMenuItem radioEbay;
    @FXML
    private Label message;
    @FXML
    private Button amazonButton;
    @FXML
    private Button blogButton;
    @FXML
    private Button ebayButton;
    @FXML
    private Button facebookButton;
    @FXML
    private Button googleButton;

    private Dialog alert;
    
    List<String> choices = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choices.add("Athos' blog");
        choices.add("Porthos' blog");
        choices.add("Aramis' blog");
        choices.add("D'Artagnan blog");
    }    

    @FXML
    private void menuHandler(ActionEvent event) {
        switch(((MenuItem)event.getSource()).getId()){
            case "exitMenu": 
                createDialog(0, null, false);break;
                
            case "blogMenu": 
                createDialog(1, null, false);break;
                
            case "faebookMenu":
                createDialog(2, "Facebook", false);break;
                
            case "googleMenu":
                createDialog(2, "Google+", false);break;
                
            case "amazonMenu":
                createDialog(3, "Amazon", false);break;
                
            case "ebayMenu":
                createDialog(3, "Ebay", true);break;
        }
    }

    @FXML
    private void buttonHandler(ActionEvent event) {
        switch(((Node)event.getSource()).getId()){
            case "blogButton": 
                createDialog(1, null, false);break;
                
            case "facebookButton": 
                createDialog(2, "Facebook", false);break;
            
            case "googleButton":
                createDialog(2, "Google+", false);break;
                
            case "amazonButton":
                createDialog(3, "Amazon", false);break;
                
            case "ebayButton":
                createDialog(3, "Ebay", true);break;
        }
    }
    private void createDialog(int id, String optional, boolean ebay){
        switch(id){
            case 0: 
                alert = new Alert(AlertType.CONFIRMATION);
                createDialog("Confirmation", "You are about lo leave the the program", "Are you sure you want lo leave?", 0);break;
                
            case 1:
                alert = new ChoiceDialog<String>("Athos' blog", choices);
                createDialog("Select a blog", "Which blog do you want to visit?", "Choose: ", 1);break;
                
            case 2:
                alert = new TextInputDialog("John");
                createDialog("Introduce your name", "Which user do you want to use to write in " + optional, "Enter your name: ", 2);break;
                
            case 3:
                if((radioAmazon.isSelected() && !ebay) || (radioEbay.isSelected() && ebay)){
                    alert = new Alert(AlertType.INFORMATION);
                    createDialog("Confirmation", "You have completed your purchase", "You have bought in " + optional, 3);
                } else {
                    alert = new Alert(AlertType.ERROR);
                    createDialog("Selection error", "You cannot buy in " + optional, "Please change your current selection in the Options menu", 3);
                } break;
                    
        }
    }
    
    private void createDialog(String title, String header, String content, int id){
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        
        switch(id){
            case 0:
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    ((Node) menuBar).getScene().getWindow().hide();
                } break;
                
            case 1:
                Optional<String> result2 = alert.showAndWait();
                result2.ifPresent(blog-> message.setText("Visiting " + blog));
                break;
                
            case 2:
                Optional<String> result3 = alert.showAndWait();
                result3.ifPresent(user-> message.setText("Message sent as: " + user));
                break;
                
            case 3:
                alert.showAndWait();break;
        }
    };
}
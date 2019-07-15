/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipc_lab_exam;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author dabarre
 */
public class BoggleFXMLController implements Initializable {

    @FXML
    private Button acceptButton;
    @FXML
    private Button eraseButton;
    @FXML
    private TextField editText;
    @FXML
    private Text counter;
    @FXML
    
    private ListView<String> listView;    
    private int noWords;
    private ObservableList<String> data = FXCollections.observableArrayList(
            "ALAS", "ATINA", "MANITA"
    );
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.setItems(data);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        BooleanBinding noString = 
                Bindings.isEmpty(editText.textProperty());
        acceptButton.disableProperty().bind(noString);
        eraseButton.disableProperty().bind(noString);
        
        noWords = listView.getItems().toArray().length;
        counter.setText(String.valueOf(noWords));
    }    

    @FXML
    private void addLetter(ActionEvent event) {
        String letter = ((Button) event.getSource()).getText();
        editText.setText(editText.getText() + letter);        
    }
    
    @FXML
    private void addWord(ActionEvent event) {
        data.add(editText.getText());
        eraseWord(null);
        counter.setText(String.valueOf(++noWords));
    }

    @FXML
    private void eraseWord(ActionEvent event) {
        editText.setText("");
    }

    @FXML
    private void newGame(ActionEvent event) {        
        listView.getItems().clear();
        eraseWord(null);
        noWords = 0;
        counter.setText("0");
    }
}

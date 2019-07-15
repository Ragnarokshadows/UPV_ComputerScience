package cms;

import accesoaBD.AccesoaBD;
import java.io.File;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modelo.Alumno;

public class StudentEditFXMLController implements Initializable {
    
    @FXML
    private TextField dniText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnamesText;
    @FXML
    private TextField ageText;
    @FXML
    private TextField addressText;
    @FXML
    private DatePicker dateText;
    @FXML
    private TextField imageSelectorText;
    @FXML
    private Button imageSelector;
    @FXML
    private Button okB;    
    @FXML
    private Button cancelB;
    
    private AccesoaBD db;
    private ObservableList<Alumno> studentsOb;
    private Image image;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            imageSelectorText.setDisable(true);
            
            okB.disableProperty().bind(Bindings.or(dniText.textProperty().isEmpty(), 
            Bindings.or(nameText.textProperty().isEmpty(),
            Bindings.or(surnamesText.textProperty().isEmpty(),
            Bindings.or(ageText.textProperty().isEmpty(),
            Bindings.or(addressText.textProperty().isEmpty(),
            Bindings.or(dateText.valueProperty().isNull(),
                    imageSelectorText.textProperty().isEmpty()                     
        )))))));
    }
    
    public void initWindow(AccesoaBD db, ObservableList<Alumno> studentsOb) {
        this.db = db;
        this.studentsOb = studentsOb;
    }
    
    @FXML
    private void imageSelect(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
           //mainStage.display(selectedFile);
           imageSelectorText.setText(selectedFile.getName());
           image = new Image(selectedFile.toURI().toString());
        }        
    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
        if (event.getSource() == okB) {            
            try {              
                studentsOb.add(addPerson());
                db.salvar();
                ((Node) event.getSource()).getScene().getWindow().hide();
                addPersonSuccessful();
            } catch (NumberFormatException | DateTimeException e) {
                addPersonUnsuccessful(e.getMessage());
            }
        }
        if (event.getSource() == cancelB) { 
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
    }
    
    private Alumno addPerson() {
        int age = Integer.parseInt(ageText.getText());
        LocalDate date = dateText.getValue();
        Alumno a = new Alumno(dniText.getText(), 
                            (nameText.getText() + ", " + surnamesText.getText()), 
                            age, addressText.getText(), date, image);
        return a;
    }
    
    private void addPersonSuccessful() {
        String name = (nameText.getText() + ", " + surnamesText.getText());
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Confirmation");
        alert.setHeaderText("The new student was added successfully.");
        alert.setContentText(name + " is now on the database.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait(); 
    }
    
    private void addPersonUnsuccessful(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
        alert.setHeaderText("Illegal format error.");
        alert.setContentText("Please check the form fields.\n" + error);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
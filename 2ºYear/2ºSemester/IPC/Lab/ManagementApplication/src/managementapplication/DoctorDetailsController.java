/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managementapplication;

import DBAccess.ClinicDBAccess;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.Days;
import model.Doctor;
import model.ExaminationRoom;
import model.Person;

/**
 * FXML Controller class
 *
 * @author Stéphane & Jorge
 */
public class DoctorDetailsController implements Initializable {
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField identifierField;
    @FXML
    private Label identifierError;
    @FXML
    private TextField nameField;
    @FXML
    private Label nameError;
    @FXML
    private TextField surnameField;
    @FXML
    private Label surnameError;
    @FXML
    private TextField telephoneField;
    @FXML
    private Label telephoneError;
    @FXML
    private TextField visitDaysField;
    @FXML
    private Label visitDaysError;
    @FXML
    private TextField startingTimeField;
    @FXML
    private Label startingTimeError;
    @FXML
    private TextField endingTimeField;
    
    @FXML
    private Label endingTimeError;
    @FXML
    private Button searchButton;
    @FXML
    private ImageView imageView;

    private ArrayList<Doctor> doctors;
    
    private ObservableList<Person> persons;
    
    private int index;
    
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    
    private boolean error = false;
    
    private String contentText = "";
    @FXML
    private Text title;
    
    private ButtonType buttonTypeOK;
    
    private boolean errorVisitDays = false;
    
    private ArrayList<ExaminationRoom> examinations;
    
    private ClinicDBAccess clinic;
    
    private boolean errorNumberRoom;
    
    private boolean errorNotRoom = true;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField equipmentField;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        equipmentField.setEditable(false);
        comboBox.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
            int value = newSelection.intValue();
            if(value != -1){
                equipmentField.setText(examinations.get(value).getEquipmentDescription());
            } else {
                equipmentField.setText("");
            }
        });
    }    
    
    public void initData(ObservableList<Person> persons, ArrayList<Doctor> doctors, int index) throws FileNotFoundException{
        clinic = ClinicDBAccess.getSingletonClinicDBAccess();
        this.index = index;
        this.doctors = doctors;
        this.persons = persons;
        
        
        
        if (index >= 0){
            title.setText("Doctor's Details");
            Doctor doctor = doctors.get(index);
            identifierField.setText(doctor.getIdentifier());
            identifierField.setEditable(false);
            nameField.setText(doctor.getName());
            nameField.setEditable(false);
            surnameField.setText(doctor.getSurname());
            surnameField.setEditable(false);
            telephoneField.setText(doctor.getTelephon());
            telephoneField.setEditable(false);
            ArrayList<Days> visitDays = doctor.getVisitDays();
            String res = "";
            int size = visitDays.size();
            for(int i=0;i<size;i++) {
                res += visitDays.get(i).toString();
                if(i != size-1) res += ", ";
            } 
            visitDaysField.setText(res);
            visitDaysField.setEditable(false);
            startingTimeField.setText(""+doctor.getVisitStartTime());
            startingTimeField.setEditable(false);
            endingTimeField.setText(""+doctor.getVisitEndTime());
            endingTimeField.setEditable(false);
            comboBox.setValue(""+doctor.getExaminationRoom().getIdentNumber());
            comboBox.setDisable(true);
            equipmentField.setText(doctor.getExaminationRoom().getEquipmentDescription());
            Image image = doctor.getPhoto();
            imageView.imageProperty().setValue(image);
            searchButton.setVisible(false);
        } else{
            title.setText("New Doctor");
            examinations = clinic.getExaminationRooms();
            ArrayList<String> combo = new ArrayList();
            for(int i=0; i<examinations.size();i++){
                String s = "" + examinations.get(i).getIdentNumber();
                combo.add(s);
            }
            ObservableList<String> options = FXCollections.observableArrayList(combo);
            comboBox.setItems(options);
        }
    }    

    @FXML
    private void buttonHandler(ActionEvent event) throws FileNotFoundException {
        boolean errorIdentifier = false;
        boolean errorTelephone = false;
        boolean errorName = false;
        boolean errorSurname = false;
        errorVisitDays = false;
        boolean errorStartingTime = false;
        boolean errorEndingTime = false;
        errorNumberRoom = true;  
        
        buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
        ((Button) errorAlert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
        
        switch(((Node) event.getSource()).getId()){
            
            case "searchButton":
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open file");    
                fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg") );    
                File selectedFile = fileChooser.showOpenDialog( ((Node)event.getSource()).getScene().getWindow());    
                if (selectedFile != null) {
                    String path = selectedFile.getAbsolutePath();
                    Image image = new Image ( new FileInputStream (path));
                    imageView.imageProperty().setValue( image );
                } break;
            
            case "okButton":
                
                if(index == -1){
                    String identifier = identifierField.getText();
                    String name = nameField.getText();
                    String surname = surnameField.getText();
                    String telephone = telephoneField.getText();
                    String startingTime = startingTimeField.getText();
                    String endingTime = endingTimeField.getText();;

                    errorIdentifier = checkField(identifier, "[0-9A-Z]+", 
                            "You can only use numbers and capital letters in the IDENTIFIER field" + "\n" + "\n", identifierError);

                    errorName = checkField(name, "[^0-9]+", "You can only use letters in the NAME field" + "\n" + "\n", nameError);;

                    errorSurname = checkField(surname, "[^0-9]+", "You can only use letters in the SURNAME field" + "\n" + "\n", surnameError);

                    errorTelephone = checkField(telephone, "[0-9]+", "You can only use numbers in the TELEPHONE field" + "\n" + "\n", telephoneError);

                    ArrayList<Days> days = new ArrayList<Days>();
                    String visitDays = visitDaysField.getText() + " ";
                    String day = "";
                    for(int i = 0;i < visitDays.length() && !errorVisitDays;i++){
                        char c = visitDays.charAt(i);
                        if (c != ',' && c != ' ')
                            day += "" + c;
                        else {
                            switch(day.toLowerCase()){
                                case "monday": days.add(Days.Monday);clear();break;
                                case "tuesday": days.add(Days.Tuesday);clear();break;
                                case "wednesday": days.add(Days.Wednesday);clear();break;
                                case "thursday": days.add(Days.Thursday);clear();break;
                                case "friday": days.add(Days.Friday);clear();break;
                                case "saturday": days.add(Days.Saturday);clear();break;
                                case "sunday": days.add(Days.Sunday);clear();break;    
                                default: 
                                    errorVisitDays = true; 
                                    visitDaysError.setText("Not valid");
                                    contentText+="The days must be separeted by commas or spaces and must be valid" + "\n" +
                                            "Example: Monday, Tuesday, Wednesday" + "\n" + "\n";
                            }
                            day="";
                        }
                    }

                    errorStartingTime = checkField(startingTime, "([0-1][0-9]|2[0-3]):([03]0|[14]5)",
                            "You can only use times ended in 00, 15, 30, 45 in the STARTING TIME field" + "\n" + "\n", startingTimeError);

                    errorEndingTime = checkField(endingTime, "([0-1][0-9]|2[0-3]):([03]0|[14]5)", 
                            "You can only use times ended in 00, 15, 30, 45 in the ENDING TIME field" + "\n" + "\n", endingTimeError);
                    if(!errorEndingTime && !errorStartingTime && !LocalTime.parse(endingTime).isAfter(LocalTime.parse(startingTime))){
                        errorEndingTime = false;
                        contentText += "The ENDING TIME must be after the STARTING TIME" + "\n" + "\n"; 
                        endingTimeError.setText("Not valid");
                    }
                    
                    if(comboBox.getSelectionModel().getSelectedIndex() != -1){
                        errorNumberRoom = false;
                    } else {
                        contentText += "You have to select a room" + "\n" + "\n"; 
                        errorNumberRoom = true;
                    }

                    error = errorIdentifier || errorTelephone || errorName || errorSurname || errorVisitDays || errorStartingTime ||
                            errorEndingTime || errorNumberRoom;

                    if(error){
                        errorAlert.setContentText(contentText);
                        errorAlert.showAndWait();
                        contentText = "";
                    } else {
                        ExaminationRoom e = examinations.get(comboBox.getSelectionModel().getSelectedIndex());
                        Doctor d = new Doctor(e, null, LocalTime.parse(startingTime), LocalTime.parse(endingTime),
                            identifier, name, surname, telephone, imageView.getImage());
                        d.setVisitDays(days);
                        doctors.add(d);
                        persons.add(d);

                        alert.setTitle("Information");
                        alert.setHeaderText("You have added a doctor");
                        alert.setContentText("The doctor was succesfully added to the data base.");

                        alert.showAndWait();
                        exit();
                    }
                } else {
                    exit();
                } break;
            
            case "cancelButton":
                exit();
                break;              
        }  
    }
    
    private boolean checkField(String data, String regex, String content, Label error){
        
        boolean res = false;
        
        if(!data.matches(regex)){
            error.setText("Not valid");
            contentText += content;
            res = true;
        } else {
            error.setText("");
        }
        
        return res;
    }
    
    private void exit(){
        okButton.getScene().getWindow().hide();
    }
    
    private void clear(){
        errorVisitDays = false;
        visitDaysError.setText("");
    }
    
    private void checkExamination(int n){
        boolean found = false;
        for(int i = 0; i < examinations.size() && !found;i++){
            ExaminationRoom e = examinations.get(i);
            if(e.getIdentNumber() == n){
                errorNotRoom = false;
                found = true;
                equipmentField.setText(e.getEquipmentDescription());
            } else {
                equipmentField.setText("");
                errorNotRoom = true;
            }
        }
    }
    
}

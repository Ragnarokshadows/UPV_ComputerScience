/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managementapplication;

import DBAccess.ClinicDBAccess;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static managementapplication.ManagementApplicationController.APPOINTMENT_MODE;
import static managementapplication.ManagementApplicationController.DOCTOR_MODE;
import static managementapplication.ManagementApplicationController.PATIENT_MODE;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.Person;

/**
 * FXML Controller class
 *
 * @author Stéphane & Jorge
 */
public class TableViewController implements Initializable {
    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, String> surnameColumn;
    @FXML
    private TableColumn<Person, String> dniColumn;
    @FXML
    private Button viewButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button appointmentButton;
    @FXML
    private Button returnButton;
    
    private ArrayList<Patient> patients;
    private ArrayList<Doctor> doctors;
    private ObservableList<Person> persons = FXCollections.observableArrayList();
    
    private ClinicDBAccess clinic;
    
    private int mode = 0;
    
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private Alert remove = new Alert(Alert.AlertType.CONFIRMATION);
    
    private Stage primaryStage;
    
    @FXML
    private MenuItem doctorsMenu;
    @FXML
    private MenuItem patientsMenu;
    @FXML
    private MenuItem appointmentsMenu;
    @FXML
    private MenuItem addDoctorMenu;
    @FXML
    private MenuItem addPatientMenu;
    @FXML
    private MenuItem addAppMenu;
    @FXML
    private MenuItem closeMenu;

    private int index;
    
    private ManagementApplicationController mac;
    
    private AppTableViewController atvc;    
    
    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuBar menuBar;

    @FXML
    private Text title;
    @FXML
    private Text instructions;
    
    private ButtonType buttonTypeOK;
    private ButtonType buttonTypeCancel;
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Initialization of the columns of the TableView
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
        dniColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("identifier"));
        
        //Disable delete button
        deleteButton.disableProperty().bind(Bindings.equal(-1,tableView.getSelectionModel().selectedIndexProperty()));
        
        //Disable view details button
        viewButton.disableProperty().bind(Bindings.equal(-1,tableView.getSelectionModel().selectedIndexProperty()));
        
        //Disable appointments button
        appointmentButton.disableProperty().bind(Bindings.equal(-1,tableView.getSelectionModel().selectedIndexProperty()));
        
        remove.setTitle("Confirmation Dialog");
        remove.setHeaderText("Remove an element");
        remove.setContentText("Do you want to continue?");
        
        buttonTypeOK = new ButtonType("OK", ButtonData.OK_DONE);
        buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        remove.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        
        alert.getButtonTypes().setAll(buttonTypeOK);
        
        nameColumn.prefWidthProperty().bind(Bindings.divide(3, tableView.widthProperty()));
        surnameColumn.prefWidthProperty().bind(Bindings.divide(3, tableView.widthProperty()));
        dniColumn.prefWidthProperty().bind(Bindings.divide(3, tableView.widthProperty()));
    }
    
    public void createDetailsWindow(int index, int mode) throws IOException{
        FXMLLoader myLoader = null; 
        Pane root = null;
        Stage stage = new Stage();
        
        switch(mode){
            case PATIENT_MODE: 
                myLoader = new FXMLLoader(getClass().getResource("PatientDetails.fxml"));
                root = (Pane) myLoader.load();
                PatientDetailsController patientController = myLoader.<PatientDetailsController>getController();
                patientController.initData(persons, patients, index);
                stage.setMinWidth(791);stage.setMinHeight(513);
                break;
            case DOCTOR_MODE: 
                myLoader = new FXMLLoader(getClass().getResource("DoctorDetails.fxml"));
                root = (Pane) myLoader.load();
                DoctorDetailsController doctorController = myLoader.<DoctorDetailsController>getController();
                doctorController.initData(persons, doctors, index);
                stage.setMinWidth(600);stage.setMinHeight(649);
                break;
        }
        
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.setMinWidth(791);
        stage.setMinHeight(513);
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
  
    }
    
    public void initData(int mode){
        this.mode = mode;
        this.clinic = ClinicDBAccess.getSingletonClinicDBAccess();
        switch(mode){
            case PATIENT_MODE:
                    title.setText("List of Patients");
                    setButtonTitle("Patient");
                    patients = clinic.getPatients();
                    persons = FXCollections.observableList( changeClass(patients) );
                    tableView.setItems(persons);
                    break;
            case DOCTOR_MODE: 
                    title.setText("List of Doctors");
                    setButtonTitle("Doctor");
                    doctors = clinic.getDoctors();
                    persons = FXCollections.observableList( changeClass(doctors) );
                    tableView.setItems(persons);
                    break;
                
        }
    }
    
    private ArrayList<Person> changeClass(ArrayList list){
        ArrayList<Person> persons = new ArrayList<Person>();
        for(int i = 0;i < list.size();i++){
            persons.add((Person) list.get(i));
        }
        
        return persons;
    }
    
    @FXML
    private void buttonHandler(ActionEvent event) throws IOException {
        index = tableView.getSelectionModel().selectedIndexProperty().getValue();
        boolean removed = false;
        boolean continueRemove = false;
        switch(((Node)event.getSource()).getId()){
            case "addButton": createDetailsWindow(-1, mode);break;
            case "viewButton": createDetailsWindow(index, mode);break;
            case "deleteButton": 
                
                if(mode == PATIENT_MODE && !clinic.hasAppointments(clinic.getPatients().get(index))){
                    if(delete()){
                        patients.remove(index);
                        removed = true;
                    }
                }
                else if(mode == DOCTOR_MODE && !clinic.hasAppointments(clinic.getDoctors().get(index))) {
                    if(delete()){
                        doctors.remove(index);
                        removed = true;
                    }
                } else {
                    alert.setTitle("Error");
                    if(mode == PATIENT_MODE){
                        alert.setHeaderText("You cannot remove this patient");
                        alert.setContentText("This patient has some appointments.");
                    }
                    else {
                        alert.setHeaderText("You cannot remove this doctor");
                        alert.setContentText("This doctor has some appointments.");
                    }
                    alert.showAndWait();
                }
                if(removed){
                    persons.remove(index);
                }
                break;
            case "appointmentButton": 
                if(mode == PATIENT_MODE) {
                    createAppointmentWindow(PATIENT_MODE, patients.get(index).getIdentifier(), null);
                } else {
                    createAppointmentWindow(DOCTOR_MODE, null, doctors.get(index).getIdentifier());
                }
                break;
                
            case "returnButton": exit();break;
        }
        
    }
    
    private void createAppointmentWindow(int mode, String patientID, String doctorID) throws IOException{
        mac.createTable(APPOINTMENT_MODE, patientID, doctorID, index);
    }

    public void initStage(Stage stage) {
        primaryStage = stage;
    }
    
    private void exit(){
        primaryStage.setScene(mac.getMainScene());
    }
    
    private boolean delete(){
        boolean res = false;
        Optional<ButtonType> result = remove.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeOK){
                res = true;
            }
            
         return res;
    }
    
    @FXML
    private void menuHandler(ActionEvent event) throws IOException {
        switch(((MenuItem)event.getSource()).getId()){
            case "closeMenu": mac.exit();break;
                
            case "doctorsMenu": mac.createTable(DOCTOR_MODE, null, null, -1);break;
                
            case "patientsMenu": mac.createTable(PATIENT_MODE, null, null, -1);break;
                
            case "appointmentsMenu": mac.createTable(APPOINTMENT_MODE, null, null, -1);break;
            
            case "addDoctorMenu": createDetailsWindow(-1, DOCTOR_MODE);break;
            
            case "addPatientMenu": createDetailsWindow(-1, PATIENT_MODE);break;
                
            case "addAppMenu": 
                atvc.initStage(primaryStage);
                atvc.initData(APPOINTMENT_MODE, null, null, -1);
                atvc.createAddWindow();break;
        }
    }
    
    public void passControllers(ManagementApplicationController mac, AppTableViewController atvc){
        this.mac = mac;
        this.atvc = atvc;
    }
    
    private void setButtonTitle(String mode){
        viewButton.setText(mode + " Data");
        addButton.setText("New " + mode);
        deleteButton.setText("Delete " + mode);
        
        String aux = mode.toLowerCase();
        
        instructions.setText("Select a row to see the "+ aux + "'s info, remove the "+ aux +" or see its scheduled appointments. ");
    }
}

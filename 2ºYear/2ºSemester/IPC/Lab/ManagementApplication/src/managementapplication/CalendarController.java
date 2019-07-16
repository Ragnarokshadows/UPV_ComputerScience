/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managementapplication;

import DBAccess.ClinicDBAccess;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Days;
import model.Doctor;
import model.Patient;
import static utils.SlotAppointmentsWeek.getAppointmentsWeek;
import utils.SlotWeek;

/**
 * FXML Controller class
 *
 * @author jorge & steph
 */
public class CalendarController implements Initializable {
    @FXML
    private Button beforeWeek;
    @FXML
    private Label weekNumberLabel;
    @FXML
    private Button nextWeek;
    @FXML
    private TableView<SlotWeek> tableView;
    @FXML
    private TableColumn<SlotWeek, LocalTime> timeColumn;
    @FXML
    private TableColumn<SlotWeek, String> mondayColumn;
    @FXML
    private TableColumn<SlotWeek, String> tuesdayColumn;
    @FXML
    private TableColumn<SlotWeek, String> wednesdayColumn;
    @FXML
    private TableColumn<SlotWeek, String> thursdayColumn;
    @FXML
    private TableColumn<SlotWeek, String> fridayColumn;
    @FXML
    private TableColumn<SlotWeek, String> saturdayColumn;
    @FXML
    private TableColumn<SlotWeek, String> sundayColumn;
    @FXML
    private TextField timeField;
    @FXML
    private TextField dateField;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    
    private ArrayList<SlotWeek> doctorWeek;
    
    private int currentWeek;
    
    private int numDayNow;
    
    private ClinicDBAccess clinic;
    
    private int week;
    
    private ObservableList<SlotWeek> slots;
    
    private Doctor doctor;
    
    private ArrayList<Days> days;
    
    private LocalTime start;
    
    private LocalTime end;
    
    private String id;
    
    private ArrayList<Appointment> appointments;
    
    private LocalDate date;
    
    private int weekDay;
    
    private LocalTime time;
    
    private boolean dateValid = false;
    
    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    
    AddAppointmentController controller;
    
    private Patient patient;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorAlert.setContentText("Please select a free day");
         
        //Initialization of the columns of the TableView
        timeColumn.setCellValueFactory(new PropertyValueFactory<SlotWeek, LocalTime>("slot"));
        mondayColumn.setCellValueFactory(new PropertyValueFactory<SlotWeek, String>("mondayAvailability"));
        tuesdayColumn.setCellValueFactory(new PropertyValueFactory<SlotWeek, String>("tuesdayAvailability"));
        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<SlotWeek, String>("wednesdayAvailability"));
        thursdayColumn.setCellValueFactory(new PropertyValueFactory<SlotWeek, String>("thursdayAvailability"));
        fridayColumn.setCellValueFactory(new PropertyValueFactory<SlotWeek, String>("fridayAvailability"));
        saturdayColumn.setCellValueFactory(new PropertyValueFactory<SlotWeek, String>("saturdayAvailability"));
        sundayColumn.setCellValueFactory(new PropertyValueFactory<SlotWeek, String>("sundayAvailability"));
        
        timeColumn.setCellFactory(column -> {
            return new TableCell<SlotWeek, LocalTime>() {
                @Override
                protected void updateItem(LocalTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setDisable(true);
                        setText(item.toString());
                    }
                }
            };
        });
        
        disableCell(mondayColumn);
        disableCell(tuesdayColumn);
        disableCell(wednesdayColumn);
        disableCell(thursdayColumn);
        disableCell(fridayColumn);
        disableCell(saturdayColumn);
        disableCell(sundayColumn);
        
        //Not editable fields
        timeField.setEditable(false);
        dateField.setEditable(false);
        
        //Selection by cells
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        
        //Add a listener to the cell of the TableView
        ObservableList<TablePosition> selectedCells = tableView.getSelectionModel().getSelectedCells() ;
        selectedCells.addListener((ListChangeListener.Change<? extends TablePosition> change) -> {
            if (selectedCells.size() > 0) {           
                TablePosition selectedCell = selectedCells.get(0);
                int columnIndex = selectedCell.getColumn();
                int rowIndex = selectedCell.getRow();
                
                time = slots.get(rowIndex).getSlot();
                timeField.setText(time.toString());
                
                if(columnIndex > 0){
                    int sumDays = columnIndex - weekDay;
                    weekDay = columnIndex;
                    
                    if(sumDays > 0)
                        date = date.plusDays(sumDays);
                    if(sumDays < 0)
                        date = date.minusDays(sumDays * -1);
                                                                                                                        
                    dateField.setText(date.toString());
                    dateValid = true;
                }
                
                updateLabel();
            }
        });
    }    

    @FXML
    private void buttonHandler(ActionEvent event) throws IOException {
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ((Button) errorAlert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
        
        switch(((Node) event.getSource()).getId()){
            case "beforeWeek":
                if(week != currentWeek) {
                    doctorWeek = getAppointmentsWeek(--week, days, start, 
                        end, appointments);
                    checkPatient();
                    slots = FXCollections.observableList( doctorWeek );
                    tableView.setItems(slots);
                    date = date.minusWeeks(1);
                }
                break;
            
            case "nextWeek": 
                doctorWeek = getAppointmentsWeek(++week, days, start, 
                        end, appointments);
                checkPatient();
                slots = FXCollections.observableList( doctorWeek );
                tableView.setItems(slots);
                date = date.plusWeeks(1);
                break;
                
            case "okButton":
                if(dateValid) {
                    controller.getData(LocalDateTime.of(date, time));
                    okButton.getScene().getWindow().hide();
                } else {
                    errorAlert.showAndWait();
                }
                break;
                
            case "cancelButton": cancelButton.getScene().getWindow().hide();
        }
        
        updateLabel();
    }
    
    public void initData(Patient p, Doctor d, AddAppointmentController before) {
        controller = before;
        
        clinic = ClinicDBAccess.getSingletonClinicDBAccess();
        
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        currentWeek = LocalDate.now().get(weekFields.weekOfWeekBasedYear());
        week = currentWeek;
        numDayNow = LocalDate.now().get(weekFields.dayOfWeek());
        
        days = d.getVisitDays();
        start = d.getVisitStartTime();
        end = d.getVisitEndTime();
        id = d.getIdentifier();
        appointments = clinic.getDoctorAppointments(id);
        
        this.patient = p;
        
        doctorWeek = getAppointmentsWeek(currentWeek, days, start, 
                end, appointments);
        checkPatient();
        slots = FXCollections.observableList( doctorWeek );
        tableView.setItems(slots);
        
        date = LocalDate.now();
        
        updateLabel();
        
        weekDay = numDayNow;
    }
    
    private void disableCell(TableColumn tcolumn){
        tcolumn.setCellFactory(column -> {
            return new TableCell<SlotWeek, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        if(!item.equalsIgnoreCase("Free")){
                            setDisable(true);
                        }  
                        else {
                            setDisable(false);
                        }
                        setText(item);
                    }
                }
            };
        });
    }
    
    private void updateLabel(){
        weekNumberLabel.setText("WEEK OF " + date.getDayOfMonth() +" " + date.getMonth() +  " " + date.getYear() );
    }
    
    private void checkPatient(){
        ArrayList<Appointment> appoint = clinic.getPatientAppointments(patient.getIdentifier());
        for(int i = 0;i < appoint.size(); i++){
            Appointment app = appoint.get(i);
            int w = app.getAppointmentWeek();
            if(w == week) {
                int index = searchIndex(app.getAppointmentDateTime().toLocalTime());
                if(index != -1){
                    SlotWeek sw = doctorWeek.get(index);
                    switch(app.getAppointmentDay()){
                        case Monday: sw.setMondayAvailability("Patient App."); break;
                        case Tuesday: sw.setTuesdayAvailability("Patient App."); break;
                        case Wednesday: sw.setWednesdayAvailability("Patient App."); break;
                        case Thursday: sw.setThursdayAvailability("Patient App."); break;
                        case Friday: sw.setFridayAvailability("Patient App."); break;
                        case Saturday: sw.setSaturdayAvailability("Patient App."); break;
                        case Sunday: sw.setSundayAvailability("Patient App."); break;
                    }
                }
            }
        }
    }
    
    private int searchIndex(LocalTime lt){
        int index = -1;
        boolean end = false;
        
        for(int i = 0; i < doctorWeek.size() && !end; i++){
            if(doctorWeek.get(i).getSlot().equals(lt)){
                end = true;
                index = i;
            }
        }
        
        return index;
    }
}

package cms;

import accesoaBD.AccesoaBD;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import modelo.Curso;
import modelo.Dias;

public class CourseEditFXMLController implements Initializable {
    
    @FXML
    private Button okB;
    @FXML
    private Button cancelB;
    
    @FXML
    private TextField courseTitleText;
    @FXML
    private TextField teacherText;
    @FXML
    private TextField maxNoStudents;    
    @FXML
    private DatePicker sDateText;
    @FXML
    private DatePicker eDateText;
    @FXML
    private TextField hourText;
    @FXML
    private TextField minuteText;
    @FXML
    private TextField roomText;
    
    @FXML
    private RadioButton mondayRadioB;
    @FXML
    private RadioButton tuesdayRadioB;
    @FXML
    private RadioButton wednesdayRadioB;
    @FXML
    private RadioButton thursdayRadioB;
    @FXML
    private RadioButton fridayRadioB;
    @FXML
    private RadioButton saturdayRadioB;
    @FXML
    private RadioButton sundayRadioB;
    
    AccesoaBD db;
    ObservableList<Curso> coursesOb;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding zeroDaysSelected = 
            Bindings.or(mondayRadioB.selectedProperty(),
            Bindings.or(tuesdayRadioB.selectedProperty(),
            Bindings.or(wednesdayRadioB.selectedProperty(),
            Bindings.or(thursdayRadioB.selectedProperty(),       
            Bindings.or(fridayRadioB.selectedProperty(),        
            Bindings.or(saturdayRadioB.selectedProperty(),        
                sundayRadioB.selectedProperty()
        ))))));        
        okB.disableProperty().bind(Bindings.or(courseTitleText.textProperty().isEmpty(), 
            Bindings.or(teacherText.textProperty().isEmpty(),
            Bindings.or(maxNoStudents.textProperty().isEmpty(),
            Bindings.or(sDateText.valueProperty().isNull(),
            Bindings.or(eDateText.valueProperty().isNull(),
            Bindings.or(hourText.textProperty().isEmpty(),
            Bindings.or(minuteText.textProperty().isEmpty(),
            Bindings.or(   roomText.textProperty().isEmpty(),
                zeroDaysSelected.not()
        )))))))));
    }
    
    public void initWindow(AccesoaBD db, ObservableList<Curso> coursesOb) {
        this.db = db;
        this.coursesOb = coursesOb;
    }
    
    @FXML
    private void onMouseClicked(MouseEvent event) {
        if (event.getSource() == okB) {            
            try {
                Curso c = addCourse();                
                coursesOb.add(c);
                db.salvar();
                ((Node) event.getSource()).getScene().getWindow().hide();
                addCourseSuccessful();
            } catch (NumberFormatException | DateTimeException | IllegalStateException e) {
                addCourseUnsuccessful(e.getMessage());
            }
        } else if (event.getSource() == cancelB) {
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
    }
    
    private Curso addCourse() throws IllegalStateException {
        int max = Integer.parseInt(maxNoStudents.getText());
        LocalDate sDate = sDateText.getValue();
        LocalDate eDate = eDateText.getValue();
        
        if (sDate.isAfter(eDate)) {
            throw new IllegalStateException("The starting date of the course (" + sDate + ") needs to be before its ending date" + sDate + ")");
        }
            
        int hour = Integer.parseInt(hourText.getText());
        int minutes = Integer.parseInt(minuteText.getText());
        LocalTime time = LocalTime.of(hour, minutes);
                
        ArrayList<Dias> days = new ArrayList<Dias>();
        if (mondayRadioB.isSelected()) {
            days.add(Dias.Lunes);
        } else if (tuesdayRadioB.isSelected()) {
            days.add(Dias.Martes);
        } else if (wednesdayRadioB.isSelected()) {
            days.add(Dias.Miercoles);
        } else if (thursdayRadioB.isSelected()) {
            days.add(Dias.Jueves);
        } else if (fridayRadioB.isSelected()) {
            days.add(Dias.Viernes);
        } else if (saturdayRadioB.isSelected()) {
            days.add(Dias.Sabado);
        } else if (sundayRadioB.isSelected()) {
            days.add(Dias.Domingo);
        }                
        
        Curso c = new Curso(courseTitleText.getText(), teacherText.getText(), 
                            max, sDate, eDate, time, days, roomText.getText());
        return c;
    }
    
    private void addCourseSuccessful() {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Confirmation");
        alert.setHeaderText("The new course was added successfully.");
        alert.setContentText(courseTitleText.getText() + " is now on the database.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait(); 
    }
    
    private void addCourseUnsuccessful(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
        alert.setHeaderText("Illegal field value.");
        alert.setContentText("Please check the form fields.\n" + error);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
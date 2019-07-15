package cms;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Curso;
import modelo.Matricula;

public class CourseListFXMLController implements Initializable {

    @FXML
    private Button returnButton;
    @FXML
    private Button removeB;
    @FXML
    private Button enrollB;
    @FXML
    private Button unrollB;
    @FXML
    private TableView<Curso> courseTableView;
    @FXML
    private TableColumn<Curso, String> courseTitleColumn;
    @FXML
    private TableColumn<Curso, String> teacherColumn;
    @FXML
    private TableColumn<Curso, Integer> maxNoStudentsColumn;
    @FXML
    private TableColumn<Curso, String> sDateColumn;
    @FXML
    private TableColumn<Curso, String> eDateColumn;
    @FXML
    private TableColumn<Curso, String> timeColumn;
    @FXML
    private TableColumn<Curso, String> wScheduleColumn;
    @FXML
    private TableColumn<Curso, String> roomColumn;
    
    private Stage primaryStage;
    private Scene prevScene;
    private String prevTitle;
    
    private AccesoaBD db;
    private ObservableList<Curso> coursesOb;    
    @FXML
    private TextField searchText;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new AccesoaBD();
        coursesOb = FXCollections.observableList(db.getCursos());
        courseTableView.setItems(coursesOb);
        
        courseTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);        
        BooleanBinding checkCourseSelection = 
                Bindings.isEmpty(courseTableView.getSelectionModel().getSelectedItems());
        removeB.disableProperty().bind(checkCourseSelection);
        enrollB.disableProperty().bind(checkCourseSelection);
        unrollB.disableProperty().bind(checkCourseSelection);
        
        courseTitleColumn.setCellValueFactory(new PropertyValueFactory<Curso, String>("titulodelcurso"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<Curso, String>("profesorAsignado"));
        maxNoStudentsColumn.setCellValueFactory(new PropertyValueFactory<Curso, Integer>("numeroMaximodeAlumnos"));
        sDateColumn.setCellValueFactory(new PropertyValueFactory<Curso, String>("fechainicio"));
        eDateColumn.setCellValueFactory(new PropertyValueFactory<Curso, String>("fechafin"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Curso, String>("hora"));
        wScheduleColumn.setCellValueFactory(new PropertyValueFactory<Curso, String>("diasimparte"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<Curso, String>("aula"));
        
        FilteredList<Curso> filteredCoursesOb = new FilteredList<Curso> (coursesOb, e -> true);
        searchText.setOnKeyReleased(e -> {
            searchText.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredCoursesOb.setPredicate((Predicate<? super Curso>) c -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lcFilter = newValue.toLowerCase();
                    if (c.getTitulodelcurso().toLowerCase().contains(lcFilter)) {
                        return true;
                    } else if (c.getProfesorAsignado().toLowerCase().contains(lcFilter)) {
                        return true;
                    } else if (c.getAula().toLowerCase().contains(lcFilter)) {
                        return true;
                    } else if (String.valueOf(c.getNumeroMaximodeAlumnos()).contains(lcFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Curso> sortedCourses = new SortedList<Curso>(filteredCoursesOb);
            sortedCourses.comparatorProperty().bind(courseTableView.comparatorProperty());
            courseTableView.setItems(sortedCourses);
        });
    }    

    public void initStage(Stage stage) {
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        primaryStage.setTitle("Course Manager");
        
        primaryStage.setResizable(true);
        primaryStage.hide();
        primaryStage.show();
    }

    @FXML
    private void returnToMainWindow(ActionEvent event) {        
        if (event.getSource() == returnButton) {
            primaryStage.setTitle(prevTitle);
            primaryStage.setScene(prevScene);
            primaryStage.setResizable(false);
            primaryStage.hide();        
            primaryStage.setMaximized(false);
            primaryStage.show();
        }
    }

    @FXML
    private void addCourse(MouseEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("CourseEditFXML.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        //Init elements
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add Course");
        
        CourseEditFXMLController mController = myLoader.<CourseEditFXMLController>getController();
        mController.initWindow(db, coursesOb);
        
        stage.show();
    }
    
    @FXML
    private void removeCourse(MouseEvent event) {
        Curso curso = courseTableView.getSelectionModel().getSelectedItem();
        String name = curso.getTitulodelcurso();
        if (db.getMatriculasDeCurso(curso).isEmpty()) {
            if (removeCourseCheck(name)) {
                coursesOb.remove(curso);
                db.salvar();
                removeCourseSuccessful(name);
            }
        } else {
            removeCourseUnsuccessful();
        }
    }
    
    private List<String> checkEnrollment(Alumno a) {
        List<Matricula> matriculas = db.getMatriculas();        
        List<String> courses = new ArrayList<String>();
        
        for (Matricula aux : matriculas) {
            if (aux.getAlumno().equals(a)) {
                courses.add(aux.getCurso().getTitulodelcurso());
            }
        }
        return courses;
    }
    
    private void removeCourseSuccessful(String name) {        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Confirmation");
        alert.setHeaderText("The course was removed successfully.");
        alert.setContentText(name + " has been deleted from the database.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait(); 
    }
    
    private void removeCourseUnsuccessful() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
        alert.setHeaderText("Unable to delete a course.");
        alert.setContentText("Please remove all students from the course "
                            + "before deleting it.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    
    private boolean removeCourseCheck(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You are about to remove a course from the database.");
        alert.setContentText("Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;        
    }

    @FXML
    private void rollStudentCourse(MouseEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("CourseStudentEnrollFMXL.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        //Init elements
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(true);
        
        CourseStudentEnrollFMXLController mController = myLoader.<CourseStudentEnrollFMXLController>getController();
        Curso c = courseTableView.getSelectionModel().getSelectedItem();
        
        
        ObservableList<Alumno> studentsOb = null;
        
        if (event.getSource() == enrollB) {            
            if (c.getNumeroMaximodeAlumnos() == db.getMatriculasDeCurso(c).size()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
                alert.setHeaderText("Unable to enroll a student.");
                alert.setContentText(c.getTitulodelcurso() + " has reach its upper max limit of students.");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
                return;
            }
            
            stage.setTitle("Enroll Student");
            mController.setButtonAction((enrollB.getText()));
            
            ArrayList<Alumno> a = (ArrayList<Alumno>) db.getAlumnos();
            ArrayList<Alumno> ac = (ArrayList<Alumno>) a.clone();
            List<Matricula> x = db.getMatriculasDeCurso(c);
            for (Matricula m : x) {
                ac.remove(db.getAlumnoByDNI(m.getAlumno().getDni()));
            }
            
            if (ac.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Information");
                alert.setHeaderText("All students are enrolled in this course.");
                alert.setContentText("Please add new students to the database before trying to enroll new ones.");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
                return;
            }
            
            studentsOb = FXCollections.observableList(ac);
        } else if (event.getSource() == unrollB) {
            stage.setTitle("Unenroll Student");
            mController.setButtonAction((unrollB.getText()));
            try {
                studentsOb = FXCollections.observableList(db.getAlumnosDeCurso(c));
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
                alert.setHeaderText("This course is empty!");
                alert.setContentText("Please enroll students to the course before trying to unenroll them.");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
                return;
            }
        }
        
        mController.initWindow(db, c, studentsOb);
        stage.show();
    }

    @FXML
    private void exitClicked(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void helpCenterClicked(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("HelpCenterFXML.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        //Init elements
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void aboutClicked(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("AboutFXML.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        //Init elements
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void addClicked(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("CourseEditFXML.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        //Init elements
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add Course");
        
        CourseEditFXMLController mController = myLoader.<CourseEditFXMLController>getController();
        mController.initWindow(db, coursesOb);
        
        stage.show();
    }
}

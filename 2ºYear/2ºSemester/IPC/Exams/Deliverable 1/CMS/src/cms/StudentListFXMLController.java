/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Matricula;

public class StudentListFXMLController implements Initializable {
    
    @FXML
    private Button returnButton;
    @FXML
    private Button addB;
    @FXML
    private Button removeB;    
    @FXML
    private AnchorPane anchorPane;        
    @FXML
    private VBox vBoxContainer;
    @FXML
    private TableView<Alumno> studentTableView;
    @FXML
    private TableColumn<Alumno, String> dniColumn;
    @FXML
    private TableColumn<Alumno, String> fullNameColumn;
    @FXML
    private TableColumn<Alumno, Integer> ageColumn;
    @FXML
    private TableColumn<Alumno, String> addressColumn;
    @FXML
    private TableColumn<Alumno, String> iDateColumn;
    @FXML
    private TableColumn<Alumno, Image> photoColumn;
    
    private Stage primaryStage;
    private Scene prevScene;
    private String prevTitle;
    
    private AccesoaBD db;
    private ObservableList<Alumno> studentsOb;
    
    @FXML
    private TextField searchText;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new AccesoaBD();
        studentsOb = FXCollections.observableList(db.getAlumnos());
        studentTableView.setItems(studentsOb);
        
        studentTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        BooleanBinding noPersonSelected = 
                Bindings.isEmpty(studentTableView.getSelectionModel().getSelectedItems());
        removeB.disableProperty().bind(noPersonSelected);
        
        dniColumn.setCellValueFactory(new PropertyValueFactory<Alumno, String>("dni"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Alumno, Integer>("edad"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Alumno, String>("direccion"));
        iDateColumn.setCellValueFactory(new PropertyValueFactory<Alumno, String>("fechadealta"));
        
        photoColumn.setCellValueFactory(new PropertyValueFactory<Alumno, Image>("foto"));
        photoColumn.setCellFactory(i -> new TableCell<Alumno, Image>() {
            ImageView view = new ImageView();
            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                }
                else {
                    view.setFitHeight(100);
                    view.setFitWidth(100);
                    view.setImage(item);
                    setGraphic(view);
                }
            }
        });        
        studentTableView.setItems(studentsOb); 
        
        FilteredList<Alumno> filteredStudentsOb = new FilteredList<Alumno> (studentsOb, e -> true);
        searchText.setOnKeyReleased(e -> {
            searchText.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredStudentsOb.setPredicate((Predicate<? super Alumno>) a -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lcFilter = newValue.toLowerCase();
                    if (a.getDni().contains(lcFilter)) {
                        return true;
                    } else if (a.getNombre().toLowerCase().contains(lcFilter)) {
                        return true;
                    } else if (a.getDireccion().toLowerCase().contains(lcFilter)) {
                        return true;
                    } else if (String.valueOf(a.getEdad()).contains(lcFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Alumno> sortedStudents = new SortedList<Alumno>(filteredStudentsOb);
            sortedStudents.comparatorProperty().bind(studentTableView.comparatorProperty());
            studentTableView.setItems(sortedStudents);
        });
    }    

    public void initStage(Stage stage) {
        primaryStage = stage;
        prevScene = stage.getScene();
        prevTitle = stage.getTitle();
        primaryStage.setTitle("Student Manager");
        
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
    private void addStudent(MouseEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("StudentEditFXML.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        //Init elements
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add Student");
        
        StudentEditFXMLController mController = myLoader.<StudentEditFXMLController>getController();
        mController.initWindow(db, studentsOb);
        
        stage.show();
    }

    @FXML
    private void removeStudent(MouseEvent event) {
        Alumno alumno = studentTableView.getSelectionModel().getSelectedItem();
        String name = alumno.getNombre();
        List<String> matriculas = checkEnrollment(alumno);
        System.out.println(matriculas.toString());
        if (matriculas.isEmpty()) { 
            if (removePersonCheck(name)) {
                studentsOb.remove(alumno);
                db.salvar();
                removePersonSuccessful(name);
            }
        } else {
            removePersonUnsuccessful(matriculas);
        }
    }
    
    private List<String> checkEnrollment(Alumno a) {
        List<Matricula> matriculas = db.getMatriculas();
        System.out.println(matriculas.toString());
        List<String> courses = new ArrayList<String>();
        
        for (Matricula aux : matriculas) {
            if (aux.getAlumno().getDni().equals(a.getDni())
                && aux.getAlumno().getNombre().equals(a.getNombre())
                && aux.getAlumno().getEdad() == (a.getEdad())
                && aux.getAlumno().getDireccion().equals(a.getDireccion())
                && aux.getAlumno().getFechadealta().equals(a.getFechadealta())) {
                courses.add(aux.getCurso().getTitulodelcurso());
            }
        }
        return courses;
    }
    
    private void removePersonSuccessful(String name) {        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Confirmation");
        alert.setHeaderText("The student was removed successfully.");
        alert.setContentText(name + " has been deleted from the database.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait(); 
    }
    
    private void removePersonUnsuccessful(List<String> matriculas) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
        alert.setHeaderText("Unable to delete a student with an enrollment");
        
        String error = "Please check the following enrollments:\n";
        for (String aux : matriculas) {
            error += "\t- " + aux + "\n";
        }
        
        alert.setContentText(error);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    
    private boolean removePersonCheck(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You are about to remove a student from the database.");
        alert.setContentText("Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;        
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
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("StudentEditFXML.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        //Init elements
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add Student");
        
        StudentEditFXMLController mController = myLoader.<StudentEditFXMLController>getController();
        mController.initWindow(db, studentsOb);
        
        stage.show();
    }
}

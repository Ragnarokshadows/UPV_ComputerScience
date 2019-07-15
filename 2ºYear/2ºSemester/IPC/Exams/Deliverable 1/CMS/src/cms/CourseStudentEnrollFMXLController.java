package cms;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Curso;
import modelo.Matricula;

public class CourseStudentEnrollFMXLController implements Initializable {

    @FXML
    private Button bAction;
    @FXML
    private Button cancelB;
    @FXML
    private TextField searchText;
    @FXML
    private TableView<Alumno> studentTableView;
    @FXML
    private TableColumn<Alumno, String> dniColumn;
    @FXML
    private TableColumn<Alumno, String> fullNameColumn;
    @FXML
    private TableColumn<Alumno, Image> photoColumn;

    private AccesoaBD db;
    private ObservableList<Alumno> studentsOb;
    private Curso courseEdit;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        studentTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        dniColumn.setCellValueFactory(new PropertyValueFactory<Alumno, String>("dni"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
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
        
        studentTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        BooleanBinding noPersonSelected = 
                Bindings.isEmpty(studentTableView.getSelectionModel().getSelectedItems());
        bAction.disableProperty().bind(noPersonSelected);
    }

    public void initWindow(AccesoaBD db, Curso courseEdit, ObservableList<Alumno> studentOb) {
        this.db = db;
        this.courseEdit = courseEdit;
        this.studentsOb = studentOb;
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
    
    public void setButtonAction(String s) {
       bAction.setText(s);
    }
    
    @FXML
    private void onMouseClicked(MouseEvent event) {
        if (event.getSource() == bAction) {
            String s = bAction.getText();
            if (s.equals("Enroll")) {
                try {
                    enrollStudent();
                    ((Node) event.getSource()).getScene().getWindow().hide();
                    enrollStudentSuccessful();
                } catch (IllegalStateException e) {
                    enrollStudentUnsuccessful();
                } catch (IndexOutOfBoundsException e) {
                    String name = studentTableView.getSelectionModel().getSelectedItem().getNombre();
                    String course = courseEdit.getTitulodelcurso();

                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
                    alert.setHeaderText("Unable to enroll a student.");
                    alert.setContentText(name + " cannot attend to " + course + " due to his/her schedule.");
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.showAndWait();
                }
            }
            else if (s.equals("Unenroll")) {
                if (unenrollStudentCheck()) {
                    unenrollStudent();
                    ((Node) event.getSource()).getScene().getWindow().hide();
                    unenrollStudentSuccessful();
                }
                
            }
        }
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
        
    private void enrollStudent() throws IllegalStateException, IndexOutOfBoundsException {
        if (courseEdit.getNumeroMaximodeAlumnos() == db.getMatriculasDeCurso(courseEdit).size()) {
            throw new IndexOutOfBoundsException("");
        }
        
        Alumno a = studentTableView.getSelectionModel().getSelectedItem();
        List<Matricula> matriculas = db.getMatriculas();
        List<Matricula> am = new ArrayList<Matricula>();
        for (Matricula aux : matriculas) {
            if (aux.getAlumno().equals(a)) {
                am.add(aux);
            }
        }
        for (Matricula aux : matriculas) {
            Curso c = aux.getCurso();
            if (c.getDiasimparte().equals(courseEdit.getDiasimparte())
                    && c.getHora().equals(courseEdit.getHora())) {
                if (c.getFechainicio().isBefore(courseEdit.getFechainicio()) && courseEdit.getFechainicio().isBefore(c.getFechafin())) {
                    throw new IllegalStateException();
                } else if (c.getFechainicio().isAfter(courseEdit.getFechainicio()) && c.getFechainicio().isBefore(courseEdit.getFechafin())) {
                    throw new IllegalStateException();
                } else if (c.getFechainicio().equals(courseEdit.getFechainicio())) {
                    throw new IllegalStateException();
                }
            }
        }
        
        Matricula m = new Matricula(LocalDate.now(), courseEdit, a);
        db.getMatriculas().add(m);
        db.salvar();
    }
    
    private void enrollStudentSuccessful() {
        String name = studentTableView.getSelectionModel().getSelectedItem().getNombre();
        String course = courseEdit.getTitulodelcurso();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Confirmation");
        alert.setHeaderText("The new student was enrolled successfully.");
        alert.setContentText(name + " is now enrolled on " + course + ".");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait(); 
    }
    
    private void enrollStudentUnsuccessful() {
        String name = studentTableView.getSelectionModel().getSelectedItem().getNombre();
        String course = courseEdit.getTitulodelcurso();
        
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
        alert.setHeaderText("Unable to enroll a student.");
        alert.setContentText(name + " cannot attend to " + course + " due to his/her schedule.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    
    private void enrollStudentUnseccessful2() {
        String name = studentTableView.getSelectionModel().getSelectedItem().getNombre();
        String course = courseEdit.getTitulodelcurso();
        
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
        alert.setHeaderText("Unable to enroll a student.");
        alert.setContentText(course + " has reach its upper max limit of students.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    
    private void unenrollStudent() {
        Alumno a = studentTableView.getSelectionModel().getSelectedItem();
        List<Matricula> m = db.getMatriculasDeCurso(courseEdit);
        for (Matricula aux : m) {
            if (aux.getAlumno().getDni().equals(a.getDni())) {
                db.getMatriculas().remove(aux);
                break;
            }
        }
        db.salvar();
    }
    
    private boolean unenrollStudentCheck() {
        String name = studentTableView.getSelectionModel().getSelectedItem().getNombre();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You are about to remove a enrollment from the database.");
        alert.setContentText("Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;        
    }
    
    private void unenrollStudentSuccessful() {
        String name = studentTableView.getSelectionModel().getSelectedItem().getNombre();
        String course = courseEdit.getTitulodelcurso();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Confirmation");
        alert.setHeaderText("The student was unenrolled successfully.");
        alert.setContentText("The enrollment of " + name + " in " + course + " was deleted from the database.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait(); 
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
}

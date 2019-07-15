package cms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CMS_FXMLDocumentController implements Initializable {

    @FXML
    private Button bCourses;
    @FXML
    private Button bStudents;
    
    private Stage primaryStage;
    @FXML
    private AnchorPane anchorPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    @FXML
    private void switchOnAction(ActionEvent event) throws IOException {
        
        if (event.getSource() == bCourses) {
            
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("CourseListFXML.fxml"));
            Parent root = (Parent) myLoader.load();
            CourseListFXMLController window1 = myLoader.<CourseListFXMLController>getController();
            window1.initStage(primaryStage);
            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setScene(scene);
        } else if (event.getSource() == bStudents)  {
            
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("StudentListFXML.fxml"));
            Parent root = (Parent) myLoader.load();
            StudentListFXMLController window2 = myLoader.<StudentListFXMLController>getController();
            window2.initStage(primaryStage);
            Scene scene = new Scene(root, 600, 400);            
            primaryStage.setScene(scene);
        }
    }    
    
    public void initStage(Stage stage) {
        primaryStage = stage;
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

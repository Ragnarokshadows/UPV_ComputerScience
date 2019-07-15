package cms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CMS extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CMS_FXMLDocument.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        CMS_FXMLDocumentController mainController = loader.<CMS_FXMLDocumentController>getController();
        mainController.initStage(stage);
        
        stage.setTitle("Courses Management System");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

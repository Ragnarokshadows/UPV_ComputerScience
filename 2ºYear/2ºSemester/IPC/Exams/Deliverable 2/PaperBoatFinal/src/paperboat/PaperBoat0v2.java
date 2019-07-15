package paperboat;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PaperBoat0v2 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StartUpFXML.fxml"));
        
        Scene scene = new Scene(root);       
        scene.getStylesheets().add(getClass().getResource("/resources/StyleSheets/dayCSS.css").toExternalForm());
        
        stage.setTitle("Paper Boat");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
               System.exit(0);
            }
            
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

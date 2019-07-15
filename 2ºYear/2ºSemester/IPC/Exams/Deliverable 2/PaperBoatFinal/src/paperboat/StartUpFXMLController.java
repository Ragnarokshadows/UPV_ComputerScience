package paperboat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

public class StartUpFXMLController implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ProgressBar progressBar;
    
    private Task<Void> delayTask;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        delayTask = new RunTask();
        progressBar.progressProperty().bind(delayTask.progressProperty());
        Thread th = new Thread(delayTask);
        th.setDaemon(true);
        th.start();
        
        delayTask.setOnSucceeded (new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    startApplication();
                } catch (IOException e) {}
            }
        });
    }
    
    private void startApplication() throws IOException {
        delayTask.cancel();
        AnchorPane ap = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
        anchorPane.getChildren().setAll(ap);
        
    }
    
    private class RunTask extends Task<Void> {
        
        public RunTask() {}
        
        @Override
        protected Void call() {
            double waitTime = Math.random() * 5 + 1.0;            
            try {
                Thread.sleep((long) (waitTime * 1000));
            } catch (InterruptedException e) {}            
            return null;
        }
    }    
}
package Chronometer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Text text;
    @FXML
    private Button start, stop, unpause, reset;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Service<String> service = new Service<String>() {
            long accumulated = 0;
            long begin;

            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                        begin = System.currentTimeMillis();
                        while (true) {
                            long time = System.currentTimeMillis() - begin + accumulated;
                            updateValue(String.format("%02d:%02d:%02d.%03d", time / 3600000, time / 60000, time / 1000, time % 1000));
                            Thread.sleep(1);
                        }
                    }
                };
            }

            @Override
            protected void cancelled() {
                accumulated = System.currentTimeMillis() - begin + accumulated;
                super.cancelled();
            }
        };

        text.textProperty().bind(service.valueProperty());

        start.setOnAction(event -> service.start());
        stop.setOnAction(event -> service.cancel());
        unpause.setOnAction(event -> service.restart());
        reset.setOnAction(event -> service.reset());

        unpause.visibleProperty().bind(service.runningProperty().not());
        start.visibleProperty().bind(service.runningProperty().not());
        stop.visibleProperty().bind(service.runningProperty());
        reset.visibleProperty().bind(service.runningProperty().not());
    }
}

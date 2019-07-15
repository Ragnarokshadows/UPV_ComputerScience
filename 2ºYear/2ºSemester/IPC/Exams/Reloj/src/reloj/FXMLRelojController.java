/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reloj;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 *
 * @author jsoler
 */
public class FXMLRelojController implements Initializable {

    @FXML
    private Label relojLabel;

    private MyTask task;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        task = new MyTask();
        Thread hiloBack = new Thread(task);
        hiloBack.setDaemon(true);
        hiloBack.start();
//======================Bind entre message y el label, comentar si se utiliza el runLater()================
        relojLabel.textProperty().bind(task.messageProperty());

    }

    class MyTask extends Task<Void> {

        void calcula() {
            LocalTime horalocal = LocalTime.now();
            updateMessage(horalocal.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
            
//========================Version altenativa con runLater()===============================================
//            Platform.runLater(() -> {
//                relojLabel.setText(horalocal.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));           
//            });
        }

        @Override
        protected Void call() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    if (isCancelled()) {
                        break;
                    }
                }
                if (isCancelled()) {
                    break;
                }
                calcula();
            }
            return null;
        }
    }
}

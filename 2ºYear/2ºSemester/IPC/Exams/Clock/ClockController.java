package Clock;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class ClockController implements Initializable {
    @FXML
    private Text clock;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Task<String> clockTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                DateFormat format = DateFormat.getTimeInstance();
                while (true) {
                    updateValue(format.format(Calendar.getInstance().getTime()));
                    try {
                        Thread.sleep(999);
                    } catch (InterruptedException e) {
                        if (getValue().isEmpty()) return null;
                    }
                }
            }
        };

        clock.textProperty().bind(clockTask.valueProperty());

        Thread t = new Thread(clockTask);
        t.setDaemon(true);
        t.start();
    }
}

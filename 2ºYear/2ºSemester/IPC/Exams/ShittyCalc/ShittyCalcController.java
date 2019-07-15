import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class ShittyCalcController implements Initializable {
    @FXML
    private Text valueText, restandoText;
    @FXML
    private TextField editText;
    @FXML
    private CheckBox restarCheckbox;
    double value;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        restandoText.visibleProperty().bind(restarCheckbox.selectedProperty());
        value = 0;
        valueText.setText(String.valueOf(value));
        editText.setOnKeyPressed(
                (keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) sumarClick(null);
                }
        );
    }

    @FXML
    public void numberClick(ActionEvent event) {
        sumar(Integer.valueOf(((Button) event.getSource()).getText()));
    }

    @FXML
    public void sumarClick(ActionEvent event) {
        double add;
        try {
            add = Double.parseDouble(editText.getText());
        } catch (NumberFormatException e) {
            //TODO: show error message
            return;
        }
        sumar(add);
    }

    private void sumar(double add) {
        if (restarCheckbox.isSelected()) add *= -1;
        value += add;
        valueText.setText(String.valueOf(value));
        editText.setText("");
    }
}

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class HandleController implements Initializable {
    private static final int ROW_COUNT = 3, COL_COUNT = 3;

    @FXML
    Button button;
    @FXML
    VBox parent;
    @FXML
    GridPane matriz;
    double x, y;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        parent.setOnKeyPressed(
                (keyEvent) -> {
                    int col = GridPane.getColumnIndex(button);
                    int row = GridPane.getRowIndex(button);
                    matriz.getChildren().remove(button);
                    switch (keyEvent.getCode()) {
                        case LEFT:
                            col = (col - 1 + COL_COUNT) % COL_COUNT;
                            break;
                        case RIGHT:
                            col =  (col + 1) % COL_COUNT;
                            break;
                        case UP:
                            row = (row - 1 + ROW_COUNT) % ROW_COUNT;
                            break;
                        case DOWN:
                            row = (row + 1) % ROW_COUNT;
                            break;
                    }
                    matriz.add(button, col, row);
                    button.setText(col + "," + row);
                }
        );
        parent.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            Stage stage = ((Stage) parent.getScene().getWindow());
            x = stage.getX() - mouseEvent.getScreenX();
            y = stage.getY() - mouseEvent.getScreenY();
        });
        parent.setOnMouseDragged(mouseEvent -> {
            Stage stage = ((Stage) parent.getScene().getWindow());
            stage.setX(mouseEvent.getScreenX() + x);
            stage.setY(mouseEvent.getScreenY() + y);
        });
    }

    @FXML
    public void cerrarClick(ActionEvent event) {
        ((Stage)
                ((Button)event.getSource()).getScene().getWindow()
        ).close();
    }

    private void move(KeyCode code) {

    }
}

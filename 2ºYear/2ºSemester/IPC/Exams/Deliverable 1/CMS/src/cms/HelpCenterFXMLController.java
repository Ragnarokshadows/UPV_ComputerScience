package cms;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public class HelpCenterFXMLController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    @FXML
    private void closeWindow(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }    
}

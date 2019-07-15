package DatabaseTableTest.controlador;

import es.upv.inf.Database;
import es.upv.inf.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador implements Initializable {
    private ObservableList<Product> datos;

    @FXML
    public TableView<Product> tableView;
    @FXML
    public TableColumn<Product, String> descripcion;
    @FXML
    public TableColumn<Product, Number> precio;
    @FXML
    public TableColumn<Product, Number> stock;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descripcion.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getDescription()));
        precio.setCellValueFactory(e -> new SimpleDoubleProperty(e.getValue().getPrice()));
        stock.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getStock()));

        datos = FXCollections.observableArrayList(Database.getProductByCategory(Product.Category.CPU));
        tableView.setItems(datos);
    }
}

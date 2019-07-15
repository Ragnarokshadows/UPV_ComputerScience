/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TablaPersonas.controlador;

import TablaPersonas.modelo.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author jsoler
 */
public class FXMLPersonasTableController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Button modButton;
    @FXML
    private Button delButton;
    @FXML
    private TableView<Persona> personasTable;
    @FXML
    private TableColumn<Persona, String> nomColum;
    @FXML
    private TableColumn<Persona, String> apellColum;

    private ObservableList<Persona> datos;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomColum.setCellValueFactory(e -> e.getValue().NombreProperty());
        apellColum.setCellValueFactory(e -> e.getValue().ApellidosProperty());

        ArrayList<Persona> misdatos = new ArrayList<>();
        misdatos.add(new Persona("Pepe", "García"));
        misdatos.add(new Persona("María", "Pérez"));
        datos = FXCollections.observableArrayList(misdatos);
        personasTable.setItems(datos); // vinculación entre la vista y el modelo
    }

    @FXML
    protected void addClick(ActionEvent event) {
        // Mostrar la ventana de inserción
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/TablaPersonas/vista/FXMLPersona.fxml"));
            Parent root = cargador.load();

            ((FXMLPersonaController) cargador.getController()).setList(datos);

            Scene scene = new Scene(root);

            Stage ventana2 = new Stage();
            ventana2.setTitle("Insertar registro");
            ventana2.initModality(Modality.WINDOW_MODAL);
            ventana2.setScene(scene);
            ventana2.setResizable(false);
            ventana2.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void delClick(ActionEvent event) {
        datos.remove(personasTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    protected void modClick(ActionEvent event) {
        int index = personasTable.getSelectionModel().getSelectedIndex();
        if (index < 0) return;
        // Mostrar la ventana de inserción
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/TablaPersonas/vista/FXMLPersona.fxml"));
            Parent root = cargador.load();

            ((FXMLPersonaController) cargador.getController()).setList(datos, index);

            Scene scene = new Scene(root);

            Stage ventana2 = new Stage();
            ventana2.setTitle("Insertar registro");
            ventana2.initModality(Modality.WINDOW_MODAL);
            ventana2.setScene(scene);
            ventana2.setResizable(false);
            ventana2.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

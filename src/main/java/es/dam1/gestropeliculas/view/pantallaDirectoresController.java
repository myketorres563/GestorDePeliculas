package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.DirectorDAO;
import es.dam1.gestropeliculas.model.Director;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

public class pantallaDirectoresController {

    @FXML
    private TableView<Director> tablaDirectores;

    @FXML
    private TableColumn<Director, String> colNombre;

    @FXML
    private TableColumn<Director, String> colNacionalidad;

    @FXML
    private TableColumn<Director, Integer> colEdad;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnEliminar;

    @FXML

    private ObservableList<Director> listaDirectores;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarDirectores();
    }

    private void configurarColumnas() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
    }

    private void cargarDirectores() {
        List<Director> directores = DirectorDAO.findAll();
        listaDirectores = FXCollections.observableArrayList(directores);
        tablaDirectores.setItems(listaDirectores);
    }

    @FXML
    private void accionAtras() {
        Stage stage = (Stage) btnAtras.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionEliminar() {
        Director seleccionado = tablaDirectores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            boolean eliminado = DirectorDAO.deleteDirector(seleccionado.getID());
            if (eliminado) {
                listaDirectores.remove(seleccionado);
                tablaDirectores.refresh();
                mostrarAlerta("Éxito", "Director eliminado correctamente.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el director.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Aviso", "Selecciona un director para eliminar.", Alert.AlertType.WARNING);
        }
    }



    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

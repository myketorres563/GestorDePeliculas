package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.ContenidoDAO;
import es.dam1.gestropeliculas.model.Estado;
import es.dam1.gestropeliculas.model.Series;
import es.dam1.gestropeliculas.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class pantallaMisSeriesController {

    @FXML
    private TableView<Series> tablaSeries;
    @FXML
    private TableColumn<Series, String> colTitulo;
    @FXML
    private TableColumn<Series, Integer> colTemporadas;
    @FXML
    private TableColumn<Series, String> colEstado;
    @FXML
    private TableColumn<Series, Integer> colAnyo;
    @FXML
    private TableColumn<Series, String> colGenero;
    @FXML
    private TableColumn<Series, String> colSinopsis;

    @FXML
    private Button btnAtras;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAnadir;
    @FXML

    private final ObservableList<Series> listaSeries = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Configura las columnas usando el modelo real Series
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTemporadas.setCellValueFactory(new PropertyValueFactory<>("temporadas"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colAnyo.setCellValueFactory(new PropertyValueFactory<>("anyoEstreno"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colSinopsis.setCellValueFactory(new PropertyValueFactory<>("sinopsis"));

        // Cargar solo series del usuario logueado
        Usuario usuarioActual = Usuario.Sesion.getUsuarioActual();
        if (usuarioActual != null) {
            List<Series> misSeries = ContenidoDAO.getSeriesPorUsuario(usuarioActual.getUsuario());
            listaSeries.setAll(misSeries);
        }

        tablaSeries.setItems(listaSeries);
    }

    @FXML
    private void accionAtras() throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPrincipal.fxml", "Menú Principal");
    }

    @FXML
    private void accionEliminar() {
        Series seleccionada = tablaSeries.getSelectionModel().getSelectedItem();
        Usuario usuarioActual = Usuario.Sesion.getUsuarioActual();
        if (seleccionada != null && usuarioActual != null) {
            boolean borrado = ContenidoDAO.eliminarUsuarioContenido(
                    usuarioActual.getUsuario(),
                    seleccionada.getID()
            );
            if (borrado) {
                listaSeries.remove(seleccionada);
                mostrarAlerta("Serie eliminada correctamente.");
            } else {
                mostrarAlerta("No se pudo eliminar la serie.");
            }
        } else if (seleccionada == null) {
            mostrarAlerta("Selecciona una serie para eliminar.");
        } else {
            mostrarAlerta("Usuario no válido.");
        }
    }


    @FXML
    private void accionAnadir() throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaSeriesAñadir.fxml", "Añadir Serie");
    }


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

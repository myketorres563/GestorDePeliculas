package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.PeliculaDAO;
import es.dam1.gestropeliculas.model.Pelicula;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;

public class pantallaPeliculasController {

    @FXML private TableView<Pelicula> tablaPeliculas;
    @FXML private TableColumn<Pelicula, String> colTitulo;
    @FXML private TableColumn<Pelicula, String> colDirector;
    @FXML private TableColumn<Pelicula, String> colEstado;
    @FXML private TableColumn<Pelicula, Integer> colAnyo;
    @FXML private TableColumn<Pelicula, String> colGenero;
    @FXML private TableColumn<Pelicula, String> colSinopsis;
    @FXML private TableColumn<Pelicula, Double> colDuracion;

    @FXML private Button btnAtras;
    @FXML private Button btnEliminar;
    @FXML private Button btnAnadir;

    /**
     *
     * Inicializa la tabla de películas y carga los datos desde la base de datos.
     *
     */
    @FXML
    private void initialize() {
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));
        colDirector.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDirector().getNombre()));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().toString()));
        colAnyo.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAnyoEstreno()).asObject());
        colGenero.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenero().toString()));
        colSinopsis.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSinopsis()));
        colDuracion.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getDuracion()).asObject());

        tablaPeliculas.setItems(FXCollections.observableArrayList(PeliculaDAO.findAll()));
    }

    /**
     *
     * Acción para volver a la pantalla principal.
     *
     * @throws IOException Si ocurre un error al abrir la nueva ventana.
     */
    @FXML
    private void accionAtras() throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPrincipal.fxml", "Menú Principal");
    }

    /**
     *
     * Acción para eliminar la película seleccionada de la tabla y de la base de datos.
     *
     */
    @FXML
    private void accionEliminar() {
        Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            if (PeliculaDAO.deletePelicula(seleccionada.getID())) {
                tablaPeliculas.getItems().remove(seleccionada);
            }
        }
    }
}
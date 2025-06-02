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

public class pantallaListaPeliculasController {

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
    @FXML private Button btnModificar;
    @FXML private Button btnAnadirMiLista;

    /**
     * Inicializa la tabla de películas y carga los datos desde la base de datos.
     */
    @FXML
    private void initialize() {
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));
        colDirector.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDirector())); // Cambiado: ahora es String
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().toString()));
        colAnyo.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAnyoEstreno()).asObject());
        colGenero.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenero().toString()));
        colSinopsis.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSinopsis()));
        colDuracion.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getDuracion()).asObject());

        tablaPeliculas.setItems(FXCollections.observableArrayList(PeliculaDAO.findAll()));
    }

    /**
     * Acción para volver a la pantalla principal.
     */
    @FXML
    private void accionAtras() throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPrincipal.fxml", "Menú Principal");
    }


    /**
     * Acción para eliminar la película seleccionada de la tabla y de la base de datos.
     */
    @FXML
    private void accionEliminar() {
        Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            if (PeliculaDAO.deletePelicula(seleccionada.getID())) {
                tablaPeliculas.getItems().remove(seleccionada);
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la película.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Aviso", "Selecciona una película primero.", Alert.AlertType.WARNING);
        }
    }

    /**
     * Acción para abrir la ventana de añadir película.
     */
    @FXML
    private void accionAnadir() throws IOException {
        // Implementa aquí la lógica para abrir la ventana de añadir película
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPeliculasAñadir.fxml", "Añadir Película");
    }

    /**
     * Acción para modificar la película seleccionada.
     */
    @FXML
    private void accionModificar() {
        Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            // Implementa aquí la lógica para abrir la ventana de modificar, pasando la película seleccionada
            // Utils.abrirNuevaVentanaConDatos("/es/dam1/gestropeliculas/view/pantallaPeliculasModificar.fxml", "Modificar Película", seleccionada);
        } else {
            mostrarAlerta("Aviso", "Selecciona una película primero.", Alert.AlertType.WARNING);
        }
    }

    /**
     * Acción para añadir la película seleccionada a la lista del usuario actual.
     */
    @FXML
    private void accionAnadirMiLista() {
        Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            // Implementa aquí la lógica para añadir la película seleccionada a la lista del usuario.
            // Por ejemplo, usando UsuarioContenidoDAO.insertarRelacion(usuarioActual, seleccionada)
            mostrarAlerta("Éxito", "Película añadida a tu lista.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Aviso", "Selecciona una película primero.", Alert.AlertType.WARNING);
        }
    }

    /**
     * Muestra una alerta en pantalla.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

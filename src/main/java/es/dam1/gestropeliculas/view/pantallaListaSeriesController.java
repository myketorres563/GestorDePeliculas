package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.SerieDAO;
import es.dam1.gestropeliculas.DAO.UsuarioContenidoDAO;
import es.dam1.gestropeliculas.model.Series;
import es.dam1.gestropeliculas.model.Usuario;
import es.dam1.gestropeliculas.model.UsuarioContenido;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class pantallaListaSeriesController {

    @FXML private TableView<Series> tablaSeries;
    @FXML private TableColumn<Series, String> colTitulo;
    @FXML private TableColumn<Series, String> colDirector;
    @FXML private TableColumn<Series, String> colEstado;
    @FXML private TableColumn<Series, Integer> colAnyo;
    @FXML private TableColumn<Series, String> colGenero;
    @FXML private TableColumn<Series, String> colSinopsis;
    @FXML private TableColumn<Series, Integer> colTemporadas;

    @FXML private Button btnAtras;
    @FXML private Button btnEliminar;
    @FXML private Button btnAnadir;
    @FXML private Button btnModificar;
    @FXML private Button btnAnadirMiLista;

    /**
     * Inicializa la tabla de series y carga los datos desde la base de datos.
     */
    @FXML
    private void initialize() {
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));
        colDirector.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDirector())); // Ahora es String
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().toString()));
        colAnyo.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAnyoEstreno()).asObject());
        colGenero.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenero().toString()));
        colSinopsis.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSinopsis()));
        colTemporadas.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTemporadas()).asObject());

        tablaSeries.setItems(FXCollections.observableArrayList(SerieDAO.findAll()));
    }

    /**
     * Acción para volver a la pantalla principal.
     */
    @FXML
    private void accionAtras() {
        try {
            Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPrincipal.fxml", "Menú Principal");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir la pantalla principal.\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Acción para eliminar la serie seleccionada en la tabla.
     */
    @FXML
    private void accionEliminar() {
        Series seleccionada = tablaSeries.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            boolean eliminado = SerieDAO.deleteSerie(seleccionada.getID());
            if (eliminado) {
                tablaSeries.getItems().remove(seleccionada);
                mostrarAlerta("Éxito", "Serie eliminada correctamente.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la serie de la base de datos.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Aviso", "Selecciona una serie primero.", Alert.AlertType.WARNING);
        }
    }

    /**
     * Acción para añadir una serie.
     */
    @FXML
    private void accionAnadir() {
        // Implementa aquí la lógica para abrir el formulario de añadir serie
        // Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaSeriesAñadir.fxml", "Añadir Serie");
    }

    /**
     * Acción para modificar la serie seleccionada.
     */
    @FXML
    private void accionModificar() {
        Series seleccionada = tablaSeries.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            // Implementa aquí la lógica para abrir el formulario de modificar, pasando la serie seleccionada
            // Utils.abrirNuevaVentanaConDatos("/es/dam1/gestropeliculas/view/pantallaSeriesModificar.fxml", "Modificar Serie", seleccionada);
        } else {
            mostrarAlerta("Aviso", "Selecciona una serie primero.", Alert.AlertType.WARNING);
        }
    }

    /**
     * Acción para añadir la serie seleccionada a la lista del usuario actual.
     */
    @FXML
    private void accionAnadirMiLista() {
        Series seleccionada = tablaSeries.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            Usuario usuarioActual = Usuario.Sesion.getUsuarioActual(); // O como tengas gestionado el usuario logueado

            // Si tu clase UsuarioContenido usa LocalDate:
            UsuarioContenido uc = new UsuarioContenido(
                    usuarioActual,
                    seleccionada, // o seleccionada.getContenido() si guardas el contenido base
                    java.time.LocalDate.now()
            );

            boolean exito = UsuarioContenidoDAO.insertarRelacion(uc);

            if (exito) {
                mostrarAlerta("Éxito", "Serie añadida a tu lista.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Aviso", "No se pudo añadir la serie a tu lista (puede que ya esté).", Alert.AlertType.WARNING);
            }
        } else {
            mostrarAlerta("Aviso", "Selecciona una serie primero.", Alert.AlertType.WARNING);
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

package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.SerieDAO;
import es.dam1.gestropeliculas.DAO.UsuarioContenidoDAO;
import es.dam1.gestropeliculas.model.Series;
import es.dam1.gestropeliculas.model.Usuario;
import es.dam1.gestropeliculas.model.UsuarioContenido;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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

    private final ObservableList<Series> listaSeries = FXCollections.observableArrayList();

    /**
     * Inicializa la tabla de series y carga los datos desde la base de datos.
     */
    @FXML
    private void initialize() {
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));
        colDirector.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDirector()));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().toString()));
        colAnyo.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAnyoEstreno()).asObject());
        colGenero.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenero().toString()));
        colSinopsis.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSinopsis()));
        colTemporadas.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTemporadas()).asObject());

        // Cargar todas las series de la BD (todas, no solo las del usuario)
        recargarTabla();
    }

    private void recargarTabla() {
        List<Series> seriesBD = SerieDAO.findAll();
        listaSeries.setAll(seriesBD);
        tablaSeries.setItems(listaSeries);
    }

    @FXML
    private void accionAtras() {
        try {
            Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPrincipal.fxml", "Menú Principal");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir la pantalla principal.\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void accionEliminar() {
        Series seleccionada = tablaSeries.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            boolean eliminado = SerieDAO.deleteSerie(seleccionada.getID());
            if (eliminado) {
                listaSeries.remove(seleccionada); // Quita de la vista
                mostrarAlerta("Éxito", "Serie eliminada correctamente.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la serie de la base de datos.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Aviso", "Selecciona una serie primero.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void accionAnadir() throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaSeriesAñadir.fxml", "Añadir Serie");
    }

    @FXML
    private void accionModificar() {
        Series seleccionada = tablaSeries.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            // Lógica para modificar (ventana aparte)
            // Utils.abrirNuevaVentanaConDatos(...);
            mostrarAlerta("Info", "Aquí abrirías la ventana para modificar la serie.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Aviso", "Selecciona una serie primero.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void accionAnadirMiLista() {
        Series seleccionada = tablaSeries.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            Usuario usuarioActual = Usuario.Sesion.getUsuarioActual();
            if (usuarioActual == null) {
                mostrarAlerta("Error", "Debes iniciar sesión para añadir series a tu lista.", Alert.AlertType.ERROR);
                return;
            }

            // Comprobar si ya la tiene
            boolean yaExiste = usuarioActual.getUsuarioContenidos().stream()
                    .anyMatch(uc -> uc.getContenido() instanceof Series &&
                            uc.getContenido().getID() == seleccionada.getID());

            if (yaExiste) {
                mostrarAlerta("Aviso", "Esta serie ya está en tu lista.", Alert.AlertType.WARNING);
                return;
            }

            // Insertar relación en la BD
            UsuarioContenido uc = new UsuarioContenido(usuarioActual, seleccionada, LocalDate.now());
            boolean exito = UsuarioContenidoDAO.insertarRelacion(uc);

            if (exito) {
                usuarioActual.getUsuarioContenidos().add(uc); // Opcional: actualiza modelo en memoria
                mostrarAlerta("Éxito", "Serie añadida a tu lista.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Aviso", "No se pudo añadir la serie a tu lista (puede que ya esté).", Alert.AlertType.WARNING);
            }
        } else {
            mostrarAlerta("Aviso", "Selecciona una serie primero.", Alert.AlertType.WARNING);
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


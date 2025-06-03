package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.ContenidoDAO;
import es.dam1.gestropeliculas.model.Estado;
import es.dam1.gestropeliculas.model.Pelicula;
import es.dam1.gestropeliculas.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class pantallaMisPeliculasController {

    @FXML
    private TableView<Pelicula> tablaPeliculas;
    @FXML
    private TableColumn<Pelicula, String> colTitulo;
    @FXML
    private TableColumn<Pelicula, String> colDirector;
    @FXML
    private TableColumn<Pelicula, String> colEstado;
    @FXML
    private TableColumn<Pelicula, Integer> colAnyo;
    @FXML
    private TableColumn<Pelicula, String> colGenero;
    @FXML
    private TableColumn<Pelicula, String> colSinopsis;
    @FXML
    private TableColumn<Pelicula, Double> colDuracion;

    @FXML
    private Button btnAtras;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnModificar;

    private final ObservableList<Pelicula> listaPeliculas = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Configurar las columnas para que tomen valores de Pelicula real
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colAnyo.setCellValueFactory(new PropertyValueFactory<>("anyoEstreno"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colSinopsis.setCellValueFactory(new PropertyValueFactory<>("sinopsis"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        // Cargar películas reales del usuario logeado
        Usuario usuarioActual = Usuario.Sesion.getUsuarioActual();
        if (usuarioActual != null) {
            List<Pelicula> misPeliculas = ContenidoDAO.getPeliculasPorUsuario(usuarioActual.getUsuario());
            listaPeliculas.setAll(misPeliculas);
        }

        tablaPeliculas.setItems(listaPeliculas);
    }

    @FXML
    private void accionAtras() throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPrincipal.fxml", "Menú Principal");
    }

    @FXML
    private void accionEliminar() {
        Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            // Aquí deberías eliminar la relación usuario-pelicula en la tabla usuario_contenido,
            // y probablemente actualizar la vista tras borrarlo de la base de datos.
            // Por ejemplo:
            // UsuarioContenidoDAO.eliminarRelacion(usuario, seleccionada);
            listaPeliculas.remove(seleccionada);
        } else {
            mostrarAlerta("Selecciona una película para eliminar.");
        }
    }

    @FXML
    private void accionAnadir() throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPeliculasAñadir.fxml", "Añadir Película");
    }

    @FXML
    private void accionModificar() {
        Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            try {
                Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPeliculaAñadir.fxml", "Modificar Película");
            } catch (IOException e) {
                mostrarAlerta("Error al abrir la ventana de modificación: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecciona una película para modificar.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

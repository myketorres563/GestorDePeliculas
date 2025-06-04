package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.PeliculaDAO;
import es.dam1.gestropeliculas.DAO.UsuarioContenidoDAO;
import es.dam1.gestropeliculas.model.Pelicula;
import es.dam1.gestropeliculas.model.Usuario;
import es.dam1.gestropeliculas.model.UsuarioContenido;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

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
    @FXML private Button btnAnadir;
    @FXML private Button btnModificar;
    @FXML private Button btnAnadirMiLista;

    @FXML
    private void initialize() {
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));
        colDirector.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDirector()));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().toString()));
        colAnyo.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAnyoEstreno()).asObject());
        colGenero.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenero().toString()));
        colSinopsis.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSinopsis()));
        colDuracion.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getDuracion()).asObject());

        recargarTabla();
    }

    private void recargarTabla() {
        tablaPeliculas.setItems(FXCollections.observableArrayList(PeliculaDAO.findAll()));
    }

    @FXML
    private void accionAtras() throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPrincipal.fxml", "Menú Principal");
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
                // Cargar el FXML de la ventana de añadir/modificar películas
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/dam1/gestropeliculas/view/pantallaPeliculasAñadir.fxml"));
                Parent root = loader.load();

                // Obtener el controlador y pasarle la película seleccionada
                pantallaPeliculasAñadirController controller = loader.getController();
                controller.cargarDatosPelicula(seleccionada); // Este método lo tienes que crear (te lo paso abajo)

                // Abrir la ventana en modo modal
                Stage stage = new Stage();
                stage.setTitle("Modificar Película");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

                // Refrescar la tabla por si hubo cambios
                recargarTabla();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo abrir la ventana de modificación.", Alert.AlertType.ERROR);
            }
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
            Usuario usuarioActual = Usuario.Sesion.getUsuarioActual();
            if (usuarioActual == null) {
                mostrarAlerta("Error", "Debes iniciar sesión para añadir películas a tu lista.", Alert.AlertType.ERROR);
                return;
            }

            // Comprobar si ya la tiene
            boolean yaExiste = usuarioActual.getUsuarioContenidos().stream()
                    .anyMatch(uc -> uc.getContenido() instanceof Pelicula &&
                            uc.getContenido().getID() == seleccionada.getID());

            if (yaExiste) {
                mostrarAlerta("Aviso", "Esta película ya está en tu lista.", Alert.AlertType.WARNING);
                return;
            }

            // Insertar relación en la BD
            UsuarioContenido uc = new UsuarioContenido(usuarioActual, seleccionada, LocalDate.now());
            boolean exito = UsuarioContenidoDAO.insertarRelacion(uc);

            if (exito) {
                usuarioActual.getUsuarioContenidos().add(uc); // Actualiza modelo en memoria
                mostrarAlerta("Éxito", "Película añadida a tu lista.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Aviso", "No se pudo añadir la película a tu lista (puede que ya esté).", Alert.AlertType.WARNING);
            }
        } else {
            mostrarAlerta("Aviso", "Selecciona una película primero.", Alert.AlertType.WARNING);
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

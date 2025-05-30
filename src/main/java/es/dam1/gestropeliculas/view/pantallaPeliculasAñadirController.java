package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.ContenidoDAO;
import es.dam1.gestropeliculas.DAO.DirectorDAO;
import es.dam1.gestropeliculas.DAO.PeliculaDAO;
import es.dam1.gestropeliculas.model.Contenido;
import es.dam1.gestropeliculas.model.Director;
import es.dam1.gestropeliculas.model.Estado;
import es.dam1.gestropeliculas.model.Genero;
import es.dam1.gestropeliculas.model.Pelicula;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class pantallaPeliculasAñadirController {

    @FXML private TextField txtTitulo;
    @FXML private ComboBox<Director> comboDirector;
    @FXML private ComboBox<Estado> comboEstado;
    @FXML private TextField txtAnyoEstreno;
    @FXML private ComboBox<Genero> comboGenero;
    @FXML private TextArea txtSinopsis;
    @FXML private TextField txtDuracion;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    /**
     *
     * Inicializa los ComboBox con los valores de la base de datos y enums.
     *
     */
    @FXML
    public void initialize() {
        comboDirector.setItems(FXCollections.observableArrayList(DirectorDAO.findAll()));
        comboEstado.setItems(FXCollections.observableArrayList(Estado.values()));
        comboGenero.setItems(FXCollections.observableArrayList(Genero.values()));
    }

    /**
     *
     * Acción para guardar una nueva película. Realiza validaciones e inserta los datos en la base de datos.
     *
     */
    @FXML
    private void accionGuardar() {
        try {
            String titulo     = txtTitulo.getText();
            Director director = comboDirector.getValue();
            Estado estado     = comboEstado.getValue();
            int anyo          = Integer.parseInt(txtAnyoEstreno.getText());
            Genero genero     = comboGenero.getValue();
            String sinopsis   = txtSinopsis.getText();
            int duracion      = Integer.parseInt(txtDuracion.getText());

            Contenido nuevoContenido = new Contenido(0, null, director, titulo, estado, anyo, genero, sinopsis);
            int idGenerado = ContenidoDAO.insertContenido(nuevoContenido);
            if (idGenerado <= 0) {
                throw new RuntimeException("No se pudo insertar el contenido.");
            }
            nuevoContenido.setID(idGenerado);

            Pelicula nuevaPelicula = new Pelicula(nuevoContenido, duracion);
            PeliculaDAO.insertPelicula(nuevaPelicula);

            mostrarAlerta("Éxito", "Película añadida correctamente.", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } catch (Exception e) {
            mostrarAlerta("Error", "Datos inválidos o incompletos.\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     *
     * Acción para cerrar la ventana actual.
     *
     */
    @FXML
    private void accionAtras() {
        cerrarVentana();
    }

    /**
     *
     * Cierra la ventana actual.
     *
     */
    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     *
     * Muestra una alerta en pantalla.
     *
     * @param titulo  Título de la alerta.
     * @param mensaje Mensaje de la alerta.
     * @param tipo    Tipo de alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
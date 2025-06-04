package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.ContenidoDAO;
import es.dam1.gestropeliculas.DAO.PeliculaDAO;
import es.dam1.gestropeliculas.model.Contenido;
import es.dam1.gestropeliculas.model.Estado;
import es.dam1.gestropeliculas.model.Genero;
import es.dam1.gestropeliculas.model.Pelicula;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class pantallaPeliculasAñadirController {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtDirector;
    @FXML private ComboBox<Estado> comboEstado;
    @FXML private TextField txtAnyoEstreno;
    @FXML private ComboBox<Genero> comboGenero;
    @FXML private TextArea txtSinopsis;
    @FXML private TextField txtDuracion;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    // Variable para saber si estamos editando o añadiendo
    private Pelicula peliculaEditando = null;

    @FXML
    public void initialize() {
        comboEstado.setItems(FXCollections.observableArrayList(Estado.values()));
        comboGenero.setItems(FXCollections.observableArrayList(Genero.values()));
    }

    // Método para precargar los datos en modo edición
    public void cargarDatosPelicula(Pelicula pelicula) {
        this.peliculaEditando = pelicula;
        txtTitulo.setText(pelicula.getTitulo());
        txtDirector.setText(pelicula.getDirector());
        comboEstado.setValue(pelicula.getEstado());
        txtAnyoEstreno.setText(String.valueOf(pelicula.getAnyoEstreno()));
        comboGenero.setValue(pelicula.getGenero());
        txtSinopsis.setText(pelicula.getSinopsis());
        txtDuracion.setText(String.valueOf(pelicula.getDuracion()));
    }

    /**
     * Acción para guardar una nueva película o modificar una existente.
     */
    @FXML
    private void accionGuardar() {
        try {
            String titulo    = txtTitulo.getText();
            String director  = txtDirector.getText();
            Estado estado    = comboEstado.getValue();
            String anyoTexto = txtAnyoEstreno.getText();
            Genero genero    = comboGenero.getValue();
            String sinopsis  = txtSinopsis.getText();
            String duracionTexto = txtDuracion.getText();

            if (titulo.isEmpty() || director.isEmpty() || estado == null || genero == null ||
                    sinopsis.isEmpty() || anyoTexto.isEmpty() || duracionTexto.isEmpty()) {
                throw new IllegalArgumentException("Rellena todos los campos correctamente.");
            }

            int anyo = Integer.parseInt(anyoTexto);
            int duracion = Integer.parseInt(duracionTexto);

            if (peliculaEditando == null) {
                // --- AÑADIR NUEVA PELÍCULA ---
                Contenido nuevoContenido = new Contenido(0, director, titulo, estado, anyo, genero, sinopsis);
                int idGenerado = ContenidoDAO.insertContenido(nuevoContenido);
                if (idGenerado <= 0) {
                    throw new RuntimeException("No se pudo insertar el contenido.");
                }
                nuevoContenido.setID(idGenerado);

                Pelicula nuevaPelicula = new Pelicula(nuevoContenido, duracion);
                PeliculaDAO.insertPelicula(nuevaPelicula);

                mostrarAlerta("Éxito", "Película añadida correctamente.", Alert.AlertType.INFORMATION);
            } else {
                // --- MODIFICAR PELÍCULA EXISTENTE ---
                peliculaEditando.setTitulo(titulo);
                peliculaEditando.setDirector(director);
                peliculaEditando.setEstado(estado);
                peliculaEditando.setAnyoEstreno(anyo);
                peliculaEditando.setGenero(genero);
                peliculaEditando.setSinopsis(sinopsis);
                peliculaEditando.setDuracion(duracion);

                ContenidoDAO.updateContenido(peliculaEditando); // Pasa la película, que hereda de Contenido
                PeliculaDAO.updatePelicula(peliculaEditando);

                mostrarAlerta("Éxito", "Película modificada correctamente.", Alert.AlertType.INFORMATION);
            }

            cerrarVentana();
        } catch (Exception e) {
            mostrarAlerta("Error", "Datos inválidos o incompletos.\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void accionAtras() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

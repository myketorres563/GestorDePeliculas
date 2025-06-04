package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.ContenidoDAO;
import es.dam1.gestropeliculas.DAO.SerieDAO;
import es.dam1.gestropeliculas.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class pantallaSeriesAñadirController {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtDirector;
    @FXML private ComboBox<Estado> comboEstado;
    @FXML private TextField txtAnyoEstreno;
    @FXML private ComboBox<Genero> comboGenero;
    @FXML private TextArea txtSinopsis;
    @FXML private TextField txtTemporadas;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    // --- NUEVO: Para saber si estamos modificando o añadiendo ---
    private Series serieEditando = null;

    @FXML
    public void initialize() {
        comboEstado.setItems(FXCollections.observableArrayList(Estado.values()));
        comboGenero.setItems(FXCollections.observableArrayList(Genero.values()));
    }

    // --- Método para precargar datos en modo edición ---
    public void cargarDatosSerie(Series serie) {
        this.serieEditando = serie;
        txtTitulo.setText(serie.getTitulo());
        txtDirector.setText(serie.getDirector());
        comboEstado.setValue(serie.getEstado());
        txtAnyoEstreno.setText(String.valueOf(serie.getAnyoEstreno()));
        comboGenero.setValue(serie.getGenero());
        txtSinopsis.setText(serie.getSinopsis());
        txtTemporadas.setText(String.valueOf(serie.getTemporadas()));
    }

    /**
     * Acción para guardar una nueva serie o modificar una existente.
     */
    @FXML
    private void accionGuardar() {
        try {
            String titulo = txtTitulo.getText();
            String director = txtDirector.getText();
            Estado estado = comboEstado.getValue();
            String anyoTexto = txtAnyoEstreno.getText();
            Genero genero = comboGenero.getValue();
            String sinopsis = txtSinopsis.getText();
            String temporadasTexto = txtTemporadas.getText();

            if (titulo.isEmpty() || director.isEmpty() || estado == null || genero == null || sinopsis.isEmpty() || anyoTexto.isEmpty() || temporadasTexto.isEmpty()) {
                throw new IllegalArgumentException("Rellena todos los campos correctamente.");
            }

            int anyo = Integer.parseInt(anyoTexto);
            int temporadas = Integer.parseInt(temporadasTexto);

            if (serieEditando == null) {
                // --- AÑADIR NUEVA SERIE ---
                Contenido nuevoContenido = new Contenido(0, director, titulo, estado, anyo, genero, sinopsis);
                int idContenido = ContenidoDAO.insertContenido(nuevoContenido);
                if (idContenido <= 0) {
                    throw new RuntimeException("No se pudo insertar el contenido.");
                }
                nuevoContenido.setID(idContenido);

                Series nuevaSerie = new Series(nuevoContenido, temporadas);
                SerieDAO.insertSerie(nuevaSerie);

                mostrarAlerta("Éxito", "Serie añadida correctamente.", Alert.AlertType.INFORMATION);
            } else {
                // --- MODIFICAR SERIE EXISTENTE ---
                serieEditando.setTitulo(titulo);
                serieEditando.setDirector(director);
                serieEditando.setEstado(estado);
                serieEditando.setAnyoEstreno(anyo);
                serieEditando.setGenero(genero);
                serieEditando.setSinopsis(sinopsis);
                serieEditando.setTemporadas(temporadas);

                // Actualizamos el contenido y la serie
                ContenidoDAO.updateContenido(serieEditando); // Pasa la serie, que es un Contenido
                SerieDAO.updateSerie(serieEditando);

                mostrarAlerta("Éxito", "Serie modificada correctamente.", Alert.AlertType.INFORMATION);
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

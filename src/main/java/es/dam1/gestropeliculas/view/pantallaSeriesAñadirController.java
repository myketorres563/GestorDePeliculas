package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.ContenidoDAO;
import es.dam1.gestropeliculas.DAO.DirectorDAO;
import es.dam1.gestropeliculas.DAO.SerieDAO;
import es.dam1.gestropeliculas.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class pantallaSeriesAñadirController {

    @FXML private TextField txtTitulo;
    @FXML private ComboBox<Director> comboDirector;
    @FXML private ComboBox<Estado> comboEstado;
    @FXML private TextField txtAnyoEstreno;
    @FXML private ComboBox<Genero> comboGenero; // Actualizado
    @FXML private TextArea txtSinopsis;
    @FXML private TextField txtTemporadas;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    @FXML
    public void initialize() {
        comboDirector.setItems(FXCollections.observableArrayList(DirectorDAO.findAll()));
        comboEstado.setItems(FXCollections.observableArrayList(Estado.values()));
        comboGenero.setItems(FXCollections.observableArrayList(Genero.values())); // Carga el enum Genero
    }

    @FXML
    private void accionGuardar() {
        try {
            String titulo = txtTitulo.getText();
            Director director = comboDirector.getValue();
            Estado estado = comboEstado.getValue();
            int anyo = Integer.parseInt(txtAnyoEstreno.getText());
            Genero genero = comboGenero.getValue(); // Usar ComboBox
            String sinopsis = txtSinopsis.getText();
            int temporadas = Integer.parseInt(txtTemporadas.getText());

            // Validaciones simples opcionales
            if (titulo.isEmpty() || director == null || estado == null || genero == null || sinopsis.isEmpty()) {
                throw new IllegalArgumentException("Rellena todos los campos correctamente.");
            }

            // Crear el objeto Contenido
            Contenido nuevoContenido = new Contenido(0, null, director, titulo, estado, anyo, genero, sinopsis);

            // Insertar contenido en la BBDD y obtener ID
            int idContenido = ContenidoDAO.insertContenido(nuevoContenido);
            if (idContenido <= 0) {
                throw new RuntimeException("No se pudo insertar el contenido.");
            }
            nuevoContenido.setID(idContenido);

            // Crear la serie con ese contenido
            Series nuevaSerie = new Series(nuevoContenido, temporadas);

            // Insertar la serie
            SerieDAO.insertSerie(nuevaSerie);

            mostrarAlerta("Éxito", "Serie añadida correctamente.", Alert.AlertType.INFORMATION);
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

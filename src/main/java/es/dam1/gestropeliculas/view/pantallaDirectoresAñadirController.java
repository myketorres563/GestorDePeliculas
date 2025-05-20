package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.DirectorDAO;
import es.dam1.gestropeliculas.model.Director;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class pantallaDirectoresAñadirController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TextField txtNacionalidad;
    @FXML private Button btnAtras;
    @FXML private Button btnAnadir;

    @FXML
    private void accionAnadir() {
        try {
            String nombre = txtNombre.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String nacionalidad = txtNacionalidad.getText();

            Director nuevoDirector = new Director(nombre, edad, nacionalidad, 0); // ID = 0 porque es autoincremental
            boolean insertado = DirectorDAO.insertDirector(nuevoDirector);

            if (insertado) {
                mostrarAlerta("Éxito", "Director añadido correctamente.", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo añadir el director.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Datos inválidos o incompletos.\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void accionAtras() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnAtras.getScene().getWindow();
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

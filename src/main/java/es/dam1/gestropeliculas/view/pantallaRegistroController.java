package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.UsuarioDAO;
import es.dam1.gestropeliculas.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;

import java.io.IOException;

public class pantallaRegistroController {

    @FXML private TextField usuario;
    @FXML private TextField email;
    @FXML private TextField contrasenia;

    public void guardarRegistro(ActionEvent actionEvent) {
        String nombreUsuario = usuario.getText().trim();
        String emailUsuario = email.getText().trim();
        String contraseniaTexto = contrasenia.getText().trim();

        try {
            int contraseniaInt = Integer.parseInt(contraseniaTexto);

            Usuario nuevoUsuario = new Usuario(nombreUsuario, emailUsuario, contraseniaInt);
            boolean insertado = UsuarioDAO.insertUsuario(nuevoUsuario);

            if (insertado) {
                mostrarAlerta("Registro exitoso", "El usuario ha sido registrado correctamente.");
            } else {
                mostrarAlerta("Error", "No se pudo registrar el usuario.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "La contraseña debe ser un número.");
        }
    }

    public void cancelarRegistro(ActionEvent actionEvent) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaInicial.fxml", "Gestor de Películas");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

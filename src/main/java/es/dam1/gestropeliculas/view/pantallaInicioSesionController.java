package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.io.IOException;

public class pantallaInicioSesionController {

    @FXML private TextField usuarioInicio;
    @FXML private TextField contraseniaInicio;

    /**
     *
     * Inicia sesión validando las credenciales del usuario y abre la pantalla principal si son correctas.
     *
     * @param actionEvent Evento de acción del botón iniciar sesión.
     */
    public void iniciarSesion(ActionEvent actionEvent) {
        String nombreUsuario = usuarioInicio.getText().trim();
        String contraseñaUsuario = contraseniaInicio.getText().trim();

        if (UsuarioDAO.validarCredenciales(nombreUsuario, contraseñaUsuario)) {
            // Las credenciales son válidas → Abrimos nueva ventana o pantalla principal
            try {
                Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPrincipal.fxml", "Gestor de Películas");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Mostrar mensaje de error si las credenciales no son válidas
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de inicio de sesión");
            alerta.setHeaderText(null);
            alerta.setContentText("Usuario o contraseña incorrectos.");
            alerta.showAndWait();
        }
    }

    /**
     *
     * Cancela el inicio de sesión y vuelve a la pantalla inicial.
     *
     * @param actionEvent Evento de acción del botón cancelar.
     * @throws IOException Si ocurre un error al abrir la ventana.
     */
    public void cancelarInicio(ActionEvent actionEvent) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaInicial.fxml", "Gestor de Peliculas");
    }
}
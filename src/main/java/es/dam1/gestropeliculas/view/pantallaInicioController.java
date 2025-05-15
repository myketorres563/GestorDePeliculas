package es.dam1.gestropeliculas.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;

public class pantallaInicioController {

    @FXML private TextField usuarioInicio;

    @FXML private TextField contraseniaInicio;

    public void iniciarSesion(ActionEvent actionEvent) {
        String nombreUsuario = usuarioInicio.getText().trim();
        String contraseñaUsuario = contraseniaInicio.getText().trim();

        // Aquí puedes agregar la lógica para verificar las credenciales del usuario
        // y realizar la acción correspondiente al iniciar sesión.
    }

    public void cancelarInicio(ActionEvent actionEvent) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaInicial.fxml", "Gestor de Peliculas");
    }
}

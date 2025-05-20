package es.dam1.gestropeliculas.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class pantallaInicialController {

    @FXML
    private Button botonRegistrar;

    @FXML
    private Button botonIniciarSesion;

    /**
     *
     * Abre la ventana de registro de usuario.
     *
     * @param actionEvent Evento de acción del botón Registrar.
     * @throws IOException Si ocurre un error al abrir la ventana.
     */
    @FXML
    public void botonRegistrar(ActionEvent actionEvent) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaRegistro.fxml", "Registro");
    }

    /**
     *
     * Abre la ventana para iniciar sesión.
     *
     * @param actionEvent Evento de acción del botón Iniciar Sesión.
     * @throws IOException Si ocurre un error al abrir la ventana.
     */
    @FXML
    public void botonIniciarSesion(ActionEvent actionEvent) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaInicioSesion.fxml", "Iniciar Sesión");
    }
}
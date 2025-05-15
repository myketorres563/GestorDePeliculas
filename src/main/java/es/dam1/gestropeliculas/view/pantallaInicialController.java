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

    @FXML
    public void botonRegistrar(ActionEvent actionEvent) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaRegistro.fxml", "Registro");
    }

    @FXML
    public void botonIniciarSesion(ActionEvent actionEvent) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaInicio.fxml", "Iniciar Sesión");
    }
}

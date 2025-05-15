package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.UsuarioDAO;
import es.dam1.gestropeliculas.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.IOException;

public class pantallaRegistroController {

    @FXML private TextField usuario;
    @FXML private TextField email;
    @FXML private TextField contrasenia;





    public void guardarRegistro(javafx.event.ActionEvent actionEvent) {
        String nombreUsuario = usuario.getText().trim();
        String emailUsuario = email.getText().trim();
        String contraseñaUsuario = contrasenia.getText().trim();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.insertUsuario(new Usuario(nombreUsuario, emailUsuario, contraseñaUsuario));

    }
    public void cancelarRegistro(ActionEvent actionEvent) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaInicial.fxml", "Gestor de Peliculas");
    }
}

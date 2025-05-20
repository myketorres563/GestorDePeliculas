package es.dam1.gestropeliculas.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class pantallaPrincipalController {

    @FXML
    public void abrirListaPeliculas(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPeliculas.fxml", "Lista de Películas");
    }

    @FXML
    public void abrirListaDirectores(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaDirectores.fxml", "Lista de Directores");
    }

    @FXML
    public void abrirListaSeries(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaSeries.fxml", "Lista de Series");
    }

    @FXML
    public void abrirFormularioPelicula(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPeliculasAñadir.fxml", "Añadir Película");
    }

    @FXML
    public void abrirFormularioDirector(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaDirectoresAñadir.fxml", "Añadir Director");
    }

    @FXML
    public void abrirFormularioSerie(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaSeriesAñadir.fxml", "Añadir Serie");
    }
}

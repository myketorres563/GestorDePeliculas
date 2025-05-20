package es.dam1.gestropeliculas.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class pantallaPrincipalController {

    /**
     *
     * Abre la ventana con la lista de películas.
     *
     * @param event Evento de acción del botón correspondiente.
     * @throws IOException Si ocurre un error al abrir la ventana.
     */
    @FXML
    public void abrirListaPeliculas(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPeliculas.fxml", "Lista de Películas");
    }

    /**
     *
     * Abre la ventana con la lista de directores.
     *
     * @param event Evento de acción del botón correspondiente.
     * @throws IOException Si ocurre un error al abrir la ventana.
     */
    @FXML
    public void abrirListaDirectores(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaDirectores.fxml", "Lista de Directores");
    }

    /**
     *
     * Abre la ventana con la lista de series.
     *
     * @param event Evento de acción del botón correspondiente.
     * @throws IOException Si ocurre un error al abrir la ventana.
     */
    @FXML
    public void abrirListaSeries(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaSeries.fxml", "Lista de Series");
    }

    /**
     *
     * Abre el formulario para añadir una nueva película.
     *
     * @param event Evento de acción del botón correspondiente.
     * @throws IOException Si ocurre un error al abrir la ventana.
     */
    @FXML
    public void abrirFormularioPelicula(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPeliculasAñadir.fxml", "Añadir Película");
    }

    /**
     *
     * Abre el formulario para añadir un nuevo director.
     *
     * @param event Evento de acción del botón correspondiente.
     * @throws IOException Si ocurre un error al abrir la ventana.
     */
    @FXML
    public void abrirFormularioDirector(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaDirectoresAñadir.fxml", "Añadir Director");
    }

    /**
     *
     * Abre el formulario para añadir una nueva serie.
     *
     * @param event Evento de acción del botón correspondiente.
     * @throws IOException Si ocurre un error al abrir la ventana.
     */
    @FXML
    public void abrirFormularioSerie(ActionEvent event) throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaSeriesAñadir.fxml", "Añadir Serie");
    }
}
package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.DAO.SerieDAO;
import es.dam1.gestropeliculas.model.Series;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class pantallaSeriesController {

    @FXML private TableView<Series> tablaSeries;
    @FXML private TableColumn<Series, String> colTitulo;
    @FXML private TableColumn<Series, String> colDirector;
    @FXML private TableColumn<Series, String> colEstado;
    @FXML private TableColumn<Series, Integer> colAnyo;
    @FXML private TableColumn<Series, String> colGenero;
    @FXML private TableColumn<Series, String> colSinopsis;
    @FXML private TableColumn<Series, Integer> colTemporadas;

    @FXML private Button btnAtras;
    @FXML private Button btnEliminar;
    @FXML private Button btnAnadir;

    @FXML
    private void initialize() {
        // Configuración de columnas
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));
        colDirector.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDirector().getNombre()));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().toString()));
        colAnyo.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAnyoEstreno()).asObject());
        colGenero.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenero().toString()));
        colSinopsis.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSinopsis()));
        colTemporadas.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTemporadas()).asObject());

        // Cargar datos
        tablaSeries.setItems(FXCollections.observableArrayList(SerieDAO.findAll()));
    }

    @FXML
    private void accionAtras() throws IOException {
        Utils.abrirNuevaVentana("/es/dam1/gestropeliculas/view/pantallaPrincipal.fxml", "Menú Principal");
    }

    @FXML
    private void accionEliminar() {
        Series seleccionada = tablaSeries.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            boolean eliminado = SerieDAO.deleteSerie(seleccionada.getID());
            if (eliminado) {
                tablaSeries.getItems().remove(seleccionada);
            } else {
                System.out.println("Error al eliminar la serie de la base de datos.");
            }
        } else {
            System.out.println("No se ha seleccionado ninguna serie.");
        }
    }


}

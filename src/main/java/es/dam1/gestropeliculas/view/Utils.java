package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Utils {

    /**
     *
     * Abre una nueva ventana de JavaFX a partir de un archivo FXML.
     *
     * @param fxml   Nombre del archivo FXML a cargar.
     * @param titulo Título de la nueva ventana.
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    public static void abrirNuevaVentana(String fxml, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(titulo);
        stage.show();
    }
}
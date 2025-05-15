package es.dam1.gestropeliculas.view;

import es.dam1.gestropeliculas.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Utils {

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

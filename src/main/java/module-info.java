module es.dam1.gestropeliculas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires java.desktop;

    // Acceso reflexivo para JavaFX
    opens es.dam1.gestropeliculas to javafx.fxml;
    opens es.dam1.gestropeliculas.view to javafx.fxml;
    opens es.dam1.gestropeliculas.model to javafx.base; // ← ESTA ES LA CLAVE
    opens es.dam1.gestropeliculas.baseDeDatos to java.xml.bind;

    // Exportaciones
    exports es.dam1.gestropeliculas;
    exports es.dam1.gestropeliculas.view;
}

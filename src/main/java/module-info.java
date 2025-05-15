module es.dam1.gestropeliculas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires java.desktop;

    // Para permitir acceso reflexivo desde FXML
    opens es.dam1.gestropeliculas to javafx.fxml;
    opens es.dam1.gestropeliculas.view to javafx.fxml;
    opens es.dam1.gestropeliculas.baseDeDatos to java.xml.bind;

    // Para exportar los paquetes
    exports es.dam1.gestropeliculas;
    exports es.dam1.gestropeliculas.view;
}

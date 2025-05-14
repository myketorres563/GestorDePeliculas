module es.dam1.gestropeliculas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;


    opens es.dam1.gestropeliculas to javafx.fxml;
    exports es.dam1.gestropeliculas;
}
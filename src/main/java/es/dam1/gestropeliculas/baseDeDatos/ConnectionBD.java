package es.dam1.gestropeliculas.baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con la base de datos.
 * Utiliza la configuración almacenada en un archivo XML.
 */
public class ConnectionBD {
    private final static String FILE = "connection.xml";

    /**
     * Devuelve una nueva conexión a la base de datos utilizando los datos del archivo XML.
     * @return Objeto Connection con la nueva conexión.
     * @throws SQLException si ocurre un error al conectar.
     */
    public static Connection getConnection() throws SQLException {
        ConnectionProperties props = XMLManager.readXML(new ConnectionProperties(), FILE);
        return DriverManager.getConnection(
                props.getUrl(),
                props.getUser(),
                props.getPassword()
        );
    }
}

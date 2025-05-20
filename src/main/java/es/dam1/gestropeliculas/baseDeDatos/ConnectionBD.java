package es.dam1.gestropeliculas.baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Clase encargada de gestionar la conexión con la base de datos.
 * Utiliza la configuración almacenada en un archivo XML.
 */
public class ConnectionBD {
    private static final Logger logger = Logger.getLogger(ConnectionBD.class.getName());
    private final static String FILE = "connection.xml";
    private static volatile Connection con;
    private static volatile ConnectionBD _instance;

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Establece la conexión a la base de datos utilizando los datos del archivo XML.
     */
    private ConnectionBD() {
        try {
            ConnectionProperties properties = XMLManager.readXML(new ConnectionProperties(), FILE);
            con = DriverManager.getConnection(
                    properties.getUrl(),
                    properties.getUser(),
                    properties.getPassword()
            );
        } catch (SQLException e) {
            logger.severe("Error al conectar a la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo establecer la conexión a la base de datos", e);
        }
    }

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

    /**
     * Cierra la conexión abierta y libera la instancia del singleton.
     * Lanza una RuntimeException en caso de error.
     */
    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                con = null;
                _instance = null;
            } catch (SQLException e) {
                logger.severe("Error al cerrar la conexión: " + e.getMessage());
                throw new RuntimeException("Error al cerrar la conexión", e);
            }
        }
    }
}

package es.dam1.gestropeliculas.baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionDB {
    private static final Logger logger = Logger.getLogger(ConnectionDB.class.getName());
    private final static String FILE = "connection.xml";
    private static volatile Connection con;
    private static volatile ConnectionDB _instance;

    private ConnectionDB() {
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

    public static Connection getConnection() {
        if (_instance == null) {
            synchronized (ConnectionDB.class) {
                if (_instance == null) {
                    _instance = new ConnectionDB();
                }
            }
        }
        return con;
    }

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
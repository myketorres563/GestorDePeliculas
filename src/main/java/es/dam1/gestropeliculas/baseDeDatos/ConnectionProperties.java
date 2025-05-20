package es.dam1.gestropeliculas.baseDeDatos;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "connection")
public class ConnectionProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    private String server;
    private String port;
    private String dataBase;
    private String user;
    private String password;

    /**
     * Constructor con parámetros.
     *
     * @param server   Servidor de la base de datos.
     * @param port     Puerto de la base de datos.
     * @param dataBase Nombre de la base de datos.
     * @param user     Usuario.
     * @param password Contraseña.
     */
    public ConnectionProperties(String server, String port, String dataBase, String user, String password) {
        this.server = server;
        this.port = port;
        this.dataBase = dataBase;
        this.user = user;
        this.password = password;
    }

    /**
     * Constructor vacío necesario para JAXB.
     */
    public ConnectionProperties() {}

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return Cadena de texto con las propiedades de la conexión.
     */
    @Override
    public String toString() {
        return "ConnectionProperties{" +
                "server='" + server + '\'' +
                ", port='" + port + '\'' +
                ", dataBase='" + dataBase + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     *
     * @return URL de conexión JDBC formada a partir de las propiedades.
     */
    public String getUrl() {
        return "jdbc:mysql://" + server + ":" + port + "/" + dataBase;
    }
}

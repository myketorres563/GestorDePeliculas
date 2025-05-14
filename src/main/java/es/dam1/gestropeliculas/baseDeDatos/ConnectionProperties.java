package es.dam1.gestropeliculas.baseDeDatos;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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

    public ConnectionProperties(String server, String port, String dataBase, String user, String password) {
        this.server = server;
        this.port = port;
        this.dataBase = dataBase;
        this.user = user;
        this.password = password;
    }

    public ConnectionProperties() {

    }

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

    public String getUrl(){
        return "jdbc:mysql://"+server+":"+port+"/"+dataBase;
    }
}
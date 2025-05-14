package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.Director;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DirectorDAO {

    private final static String SQL_ALL = "SELECT * FROM director";
    private final static String SQL_INSERT = "INSERT INTO director (id, nombre, edad, nacionalidad) VALUES (?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE director SET nombre = ?, edad = ?, nacionalidad = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM director WHERE id = ?";

    /**
     * Método que devuelve la lista de todos los directores de la base de datos.
     * @return lista de directores.
     */
    public static List<Director> findAll() {
        List<Director> directores = new ArrayList<>();
        Connection con = ConnectionBD.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(SQL_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Director director = new Director(
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("nacionalidad"),
                        rs.getInt("id")
                );
                directores.add(director);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return directores;
    }

    /**
     * Método que inserta un director en la base de datos.
     * @param director objeto de tipo Director con los datos a insertar.
     * @return el director insertado o null si no se pudo insertar.
     */
    public static Director insertDirector(Director director) {
        if (director != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                pst.setInt(1, director.getID());
                pst.setString(2, director.getNombre());
                pst.setInt(3, director.getEdad());
                pst.setString(4, director.getNacionalidad());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            director = null;
        }
        return director;
    }

    /**
     * Método que actualiza los datos de un director en la base de datos.
     * @param director objeto de tipo Director con los datos actualizados.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    public static boolean updateDirector(Director director) {
        boolean result = false;
        if (director != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                pst.setString(1, director.getNombre());
                pst.setInt(2, director.getEdad());
                pst.setString(3, director.getNacionalidad());
                pst.setInt(4, director.getID());
                pst.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * Método que elimina un director de la base de datos por su ID.
     * @param director objeto de tipo Director con el ID del director a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public static boolean deleteDirector(Director director) {
        boolean deleted = false;
        if (director != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                pst.setInt(1, director.getID());
                pst.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }
}
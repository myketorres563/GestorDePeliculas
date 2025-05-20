package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    private static final String SQL_ALL    = "SELECT * FROM pelicula";
    private static final String SQL_INSERT = "INSERT INTO pelicula (IDContenido, duracion) VALUES (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM pelicula WHERE IDContenido = ?";

    /** Devuelve todas las películas existentes */
    public static List<Pelicula> findAll() {
        List<Pelicula> lista = new ArrayList<>();
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_ALL);
                ResultSet rs = pst.executeQuery()
        ) {
            while (rs.next()) {
                int idCont = rs.getInt("IDContenido");
                double dur = rs.getDouble("duracion");
                // Cargamos el Contenido asociado
                Contenido contenido = ContenidoDAO.findById(idCont);
                lista.add(new Pelicula(contenido, dur));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener películas", e);
        }
        return lista;
    }

    /** Inserta la película (ya con ID de Contenido generado) */
    public static Pelicula insertPelicula(Pelicula pelicula) {
        if (pelicula == null) return null;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_INSERT)
        ) {
            pst.setInt(1, pelicula.getID());        // IDContenido ya seteado
            pst.setDouble(2, pelicula.getDuracion());
            pst.executeUpdate();
            return pelicula;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar película", e);
        }
    }

    /** Elimina una película por su IDContenido */
    public static boolean deletePelicula(int idContenido) {
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_DELETE)
        ) {
            pst.setInt(1, idContenido);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar película", e);
        }
    }

}

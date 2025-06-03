package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    // Consultas SQL estáticas para claridad y fácil mantenimiento
    private static final String SQL_INSERT  = "INSERT INTO pelicula (IDContenido, duracion) VALUES (?, ?)";
    private static final String SQL_UPDATE  = "UPDATE pelicula SET duracion = ? WHERE IDContenido = ?";
    private static final String SQL_DELETE  = "DELETE FROM pelicula WHERE IDContenido = ?";
    private static final String SQL_SELECT  = "SELECT * FROM pelicula";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM pelicula WHERE IDContenido = ?";

    /**
     * Devuelve todas las películas de la base de datos.
     * Carga también los datos completos del objeto Contenido asociado a cada Pelicula (estilo EAGER).
     *
     * @return Lista de Pelicula con su Contenido.
     */
    public static List<Pelicula> findAll() {
        List<Pelicula> lista = new ArrayList<>();
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_SELECT);
                ResultSet rs = pst.executeQuery()
        ) {
            while (rs.next()) {
                int idCont = rs.getInt("IDContenido");
                double duracion = rs.getDouble("duracion");
                // Cargar el objeto Contenido completo relacionado con la película
                Contenido contenido = ContenidoDAO.findById(idCont);
                if (contenido != null) {
                    lista.add(new Pelicula(contenido, duracion));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las películas", e);
        }
        return lista;
    }

    /**
     * Busca una película por su ID de contenido.
     * Carga también el objeto Contenido asociado (EAGER).
     *
     * @param idContenido ID de la película/Contenido.
     * @return Objeto Pelicula completo, o null si no existe.
     */
    public static Pelicula findById(int idContenido) {
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_FIND_BY_ID)
        ) {
            pst.setInt(1, idContenido);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    double duracion = rs.getDouble("duracion");
                    Contenido contenido = ContenidoDAO.findById(idContenido);
                    if (contenido != null) {
                        return new Pelicula(contenido, duracion);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Pelicula por ID", e);
        }
        return null;
    }

    /**
     * Inserta una nueva película en la base de datos.
     * Se asume que el Contenido ya existe y se le pasa por el objeto Pelicula.
     *
     * @param pelicula Objeto Pelicula a insertar.
     * @return La película insertada (igual que la recibida), o null si es null.
     */
    public static Pelicula insertPelicula(Pelicula pelicula) {
        if (pelicula == null) return null;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_INSERT)
        ) {
            pst.setInt(1, pelicula.getID());
            pst.setDouble(2, pelicula.getDuracion());
            pst.executeUpdate();
            return pelicula;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar Pelicula", e);
        }
    }

    /**
     * Actualiza los datos de una película en la base de datos.
     *
     * @param pelicula Objeto Pelicula con los nuevos datos.
     * @return true si se actualizó correctamente, false si no.
     */
    public static boolean updatePelicula(Pelicula pelicula) {
        if (pelicula == null) return false;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)
        ) {
            pst.setDouble(1, pelicula.getDuracion());
            pst.setInt(2, pelicula.getID());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar Pelicula", e);
        }
    }

    /**
     * Elimina una película de la base de datos por su IDContenido.
     *
     * @param idContenido ID del contenido asociado a la película.
     * @return true si la película se eliminó correctamente, false si no.
     */
    public static boolean deletePelicula(int idContenido) {
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_DELETE)
        ) {
            pst.setInt(1, idContenido);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar Pelicula", e);
        }
    }
}
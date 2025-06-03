package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SerieDAO {

    // Consultas SQL estáticas para claridad y fácil mantenimiento
    private static final String SQL_INSERT  = "INSERT INTO series (IDContenido, temporadas) VALUES (?, ?)";
    private static final String SQL_UPDATE  = "UPDATE series SET temporadas = ? WHERE IDContenido = ?";
    private static final String SQL_DELETE  = "DELETE FROM series WHERE IDContenido = ?";
    private static final String SQL_SELECT  = "SELECT * FROM series";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM series WHERE IDContenido = ?";

    /**
     * Devuelve todas las series de la base de datos.
     * Carga también los datos completos del objeto Contenido asociado a cada Serie (estilo EAGER).
     *
     * @return Lista de Series con su Contenido.
     */
    public static List<Series> findAll() {
        List<Series> lista = new ArrayList<>();
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_SELECT);
                ResultSet rs = pst.executeQuery()
        ) {
            while (rs.next()) {
                int idCont = rs.getInt("IDContenido");
                int temp   = rs.getInt("temporadas");
                // Cargar el objeto Contenido completo relacionado con la serie
                Contenido c = ContenidoDAO.findById(idCont);
                if (c != null) {
                    lista.add(new Series(c, temp));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las series", e);
        }
        return lista;
    }

    /**
     * Busca una serie por su ID de contenido.
     * Carga también el objeto Contenido asociado (EAGER).
     *
     * @param idContenido ID de la serie/Contenido.
     * @return Objeto Series completo, o null si no existe.
     */
    public static Series findById(int idContenido) {
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_FIND_BY_ID)
        ) {
            pst.setInt(1, idContenido);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int temp = rs.getInt("temporadas");
                    Contenido c = ContenidoDAO.findById(idContenido);
                    if (c != null) {
                        return new Series(c, temp);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Serie por ID", e);
        }
        return null;
    }

    /**
     * Inserta una nueva serie en la base de datos.
     * Se asume que el Contenido ya existe y se le pasa por el objeto Series.
     *
     * @param serie Objeto Series a insertar.
     * @return La serie insertada (igual que la recibida), o null si es null.
     */
    public static Series insertSerie(Series serie) {
        if (serie == null) return null;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_INSERT)
        ) {
            pst.setInt(1, serie.getID());
            pst.setInt(2, serie.getTemporadas());
            pst.executeUpdate();
            return serie;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar Serie", e);
        }
    }

    /**
     * Actualiza los datos de una serie en la base de datos.
     *
     * @param serie Objeto Series con los nuevos datos.
     * @return true si se actualizó correctamente, false si no.
     */
    public static boolean updateSerie(Series serie) {
        if (serie == null) return false;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)
        ) {
            pst.setInt(1, serie.getTemporadas());
            pst.setInt(2, serie.getID());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar Serie", e);
        }
    }

    /**
     * Elimina una serie de la base de datos por su IDContenido.
     *
     * @param idContenido ID del contenido asociado a la serie.
     * @return true si la serie se eliminó correctamente, false si no.
     */
    public static boolean deleteSerie(int idContenido) {
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_DELETE)
        ) {
            pst.setInt(1, idContenido);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar Serie", e);
        }
    }
}
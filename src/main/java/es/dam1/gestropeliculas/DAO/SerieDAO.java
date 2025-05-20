// src/main/java/es/dam1/gestropeliculas/DAO/SerieDAO.java
package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SerieDAO {

    private static final String SQL_INSERT  = "INSERT INTO series (IDContenido, temporadas) VALUES (?, ?)";
    private static final String SQL_UPDATE  = "UPDATE series SET temporadas = ? WHERE IDContenido = ?";
    private static final String SQL_DELETE  = "DELETE FROM series WHERE IDContenido = ?";
    private static final String SQL_SELECT  = "SELECT * FROM series";

    /** Devuelve todas las series */
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
                Contenido c = ContenidoDAO.findById(idCont);
                lista.add(new Series(c, temp));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las series", e);
        }
        return lista;
    }

    /** Inserta una nueva serie */
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

    /** Actualiza una serie existente */
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

    /** Elimina una serie por IDContenido */
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
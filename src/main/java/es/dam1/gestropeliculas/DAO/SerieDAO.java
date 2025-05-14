package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.Estado;
import es.dam1.gestropeliculas.model.Genero;
import es.dam1.gestropeliculas.model.Series;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SerieDAO {

    private final static String SQL_ALL = "SELECT * FROM serie";  // Asegurarse que la tabla tiene la columna estado
    private final static String SQL_INSERT = "INSERT INTO serie (id, titulo, director, anyo_estreno, genero, sinopsis, temporadas) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE serie SET titulo = ?, director = ?, anyo_estreno = ?, genero = ?, sinopsis = ?, temporadas = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM serie WHERE id = ?";

    public static List<Series> findAll() {
        List<Series> series = new ArrayList<>();
        Connection con = ConnectionBD.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(SQL_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Series serie = new Series(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        Estado.valueOf(rs.getString("estado")),  // Faltaba el estado
                        rs.getInt("anyo_estreno"),
                        Genero.valueOf(rs.getString("genero")),  // Convertir String a enum Genero
                        rs.getString("sinopsis"),
                        rs.getInt("temporadas")
                );
                series.add(serie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return series;
    }

    public static Series insertSerie(Series serie) {
        if (serie != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                pst.setInt(1, serie.getID());
                pst.setString(2, serie.getTitulo());
                pst.setString(3, serie.getDirector());
                pst.setInt(4, serie.getAnyoEstreno());
                pst.setString(5, serie.getGenero().toString());
                pst.setString(6, serie.getSinopsis());
                pst.setInt(7, serie.getTemporadas());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            serie = null;
        }
        return serie;
    }

    public static boolean updateSerie(Series     serie) {
        boolean result = false;
        if (serie != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                pst.setString(1, serie.getTitulo());
                pst.setString(2, serie.getDirector());
                pst.setInt(3, serie.getAnyoEstreno());
                pst.setString(4, serie.getGenero() != null ? serie.getGenero().toString() : null);
                pst.setString(5, serie.getSinopsis());
                pst.setInt(6, serie.getTemporadas());
                pst.setInt(7, serie.getID());
                pst.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static boolean deleteSerie(Series serie) {
        boolean deleted = false;
        if (serie != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                pst.setInt(1, serie.getID());
                pst.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }
}
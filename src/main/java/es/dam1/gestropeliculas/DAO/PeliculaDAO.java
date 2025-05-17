package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.Genero;
import es.dam1.gestropeliculas.model.Pelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {


    private final static String SQL_ALL = "SELECT * FROM pelicula";
    private final static String SQL_INSERT = "INSERT INTO pelicula (IDContenido, titulo, director, anyo_estreno, genero, sinopsis) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE pelicula SET titulo = ?, director = ?, anyo_estreno = ?, genero = ?, sinopsis = ? WHERE IDContenido = ?";
    private final static String SQL_DELETE = "DELETE FROM pelicula WHERE IDContenido = ?";

    public static List<Pelicula> findAll() {
        List<Pelicula> peliculas = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionBD.getConnection().prepareStatement(SQL_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setID(rs.getInt("IDContenido"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setDirector(rs.getString("director"));
                pelicula.setAnyoEstreno(rs.getInt("anyo_estreno"));
                pelicula.setGenero(Genero.valueOf(rs.getString("genero")));
                pelicula.setSinopsis(rs.getString("sinopsis"));
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }

    public static Pelicula insertPelicula(Pelicula pelicula) {
        if (pelicula != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                pst.setInt(1, pelicula.getID()); // Aquí también cambia a IDContenido
                pst.setString(2, pelicula.getTitulo());
                pst.setString(3, pelicula.getDirector());
                pst.setInt(4, pelicula.getAnyoEstreno());
                pst.setString(5, pelicula.getGenero().name());
                pst.setString(6, pelicula.getSinopsis());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            pelicula = null;
        }
        return pelicula;
    }

    public static boolean updatePelicula(Pelicula pelicula) {
        boolean result = false;
        if (pelicula != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                pst.setString(1, pelicula.getTitulo());
                pst.setString(2, pelicula.getDirector());
                pst.setInt(3, pelicula.getAnyoEstreno());
                pst.setString(4, pelicula.getGenero().name());
                pst.setString(5, pelicula.getSinopsis());
                pst.setInt(6, pelicula.getID()); // También debe ser IDContenido
                pst.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static boolean deletePelicula(Pelicula pelicula) {
        boolean deleted = false;
        if (pelicula != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                pst.setInt(1, pelicula.getID()); // También debe ser IDContenido
                pst.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }

}

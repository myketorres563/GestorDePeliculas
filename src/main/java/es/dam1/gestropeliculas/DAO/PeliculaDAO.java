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
    private final static String SQL_UPDATE = "UPDATE pelicula SET titulo = ?, director = ?, anyoestreno = ?, genero = ?, sinopsis = ? WHERE IDContenido = ?";
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
        Connection conn = null;
        try {
            conn = ConnectionBD.getConnection();
            conn.setAutoCommit(false); // para hacer transacción

            // Insertar en contenido
            String sqlContenido = "INSERT INTO contenido (IDContenido, titulo, director, estado, anyoEstreno, genero, sinopsis) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstContenido = conn.prepareStatement(sqlContenido)) {
                pstContenido.setInt(1, pelicula.getID());
                pstContenido.setString(2, pelicula.getTitulo());
                pstContenido.setString(3, pelicula.getDirector());
                pstContenido.setString(4, pelicula.getEstado().name());
                pstContenido.setInt(5, pelicula.getAnyoEstreno());
                pstContenido.setString(6, pelicula.getGenero().name());
                pstContenido.setString(7, pelicula.getSinopsis());
                pstContenido.executeUpdate();
            }

            // Insertar en pelicula
            String sqlPelicula = "INSERT INTO pelicula (IDContenido, duracion) VALUES (?, ?)";
            try (PreparedStatement pstPelicula = conn.prepareStatement(sqlPelicula)) {
                pstPelicula.setInt(1, pelicula.getID());
                pstPelicula.setDouble(2, pelicula.getDuracion());
                pstPelicula.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
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

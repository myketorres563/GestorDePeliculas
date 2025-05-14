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
    private final static String SQL_INSERT = "INSERT INTO pelicula (id, titulo, director, anyo_estreno, genero, sinopsis) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE pelicula SET titulo = ?, director = ?, anyo_estreno = ?, genero = ?, sinopsis = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM pelicula WHERE id = ?";

    public static List<Pelicula> findAll() {
        List<Pelicula> peliculas = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionBD.getConnection().prepareStatement(SQL_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setID(rs.getInt("id"));
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


}
package es.dam1.gestropeliculas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    private Connection connection;

    public PeliculaDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean crearPelicula(int id, String titulo, String genero) {
        String sql = "INSERT INTO peliculas (id, titulo, genero) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, titulo);
            statement.setString(3, genero);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPelicula(int id) {
        String sql = "DELETE FROM peliculas WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> obtenerTodasLasPeliculas() {
        String sql = "SELECT * FROM peliculas";
        List<String> peliculas = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String pelicula = "ID: " + resultSet.getInt("id") +
                        ", Título: " + resultSet.getString("titulo") +
                        ", Género: " + resultSet.getString("genero");
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculas;
    }
}
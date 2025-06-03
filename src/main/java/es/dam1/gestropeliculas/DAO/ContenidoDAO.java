package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContenidoDAO {

    private static final String SQL_ALL         = "SELECT * FROM contenido";
    private static final String INSERT_SQL      = "INSERT INTO contenido (director, titulo, estado, anyoEstreno, genero, sinopsis) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL      = "UPDATE contenido SET director = ?, titulo = ?, estado = ?, anyoEstreno = ?, genero = ?, sinopsis = ? WHERE IDContenido = ?";
    private static final String DELETE_SQL      = "DELETE FROM contenido WHERE IDContenido = ?";
    private static final String FIND_BY_ID_SQL  = "SELECT * FROM contenido WHERE IDContenido = ?";

    // ==================== METODOS COMUNES ====================

    public static Contenido findById(int id) {
        Contenido contenido = null;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    contenido = new Contenido();
                    contenido.setID(rs.getInt("IDContenido"));
                    contenido.setDirector(rs.getString("director"));
                    contenido.setTitulo(rs.getString("titulo"));
                    contenido.setEstado(Estado.valueOf(rs.getString("estado")));
                    contenido.setAnyoEstreno(rs.getInt("anyoEstreno"));
                    contenido.setGenero(Genero.valueOf(rs.getString("genero")));
                    contenido.setSinopsis(rs.getString("sinopsis"));
                    // No cargamos usuarioContenidos aquí para evitar recursividad/ciclo de conexiones
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Contenido por ID", e);
        }
        return contenido;
    }


    public static int insertContenido(Contenido contenido) {
        int idGenerado = -1;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            pst.setString(1, contenido.getDirector());
            pst.setString(2, contenido.getTitulo());
            pst.setString(3, contenido.getEstado().toString());
            pst.setInt(4, contenido.getAnyoEstreno());
            pst.setString(5, contenido.getGenero().toString());
            pst.setString(6, contenido.getSinopsis());
            pst.executeUpdate();

            try (ResultSet keys = pst.getGeneratedKeys()) {
                if (keys.next()) {
                    idGenerado = keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar Contenido", e);
        }
        return idGenerado;
    }

    public static boolean updateContenido(Contenido contenido) {
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(UPDATE_SQL)
        ) {
            pst.setString(1, contenido.getDirector());
            pst.setString(2, contenido.getTitulo());
            pst.setString(3, contenido.getEstado().toString());
            pst.setInt(4, contenido.getAnyoEstreno());
            pst.setString(5, contenido.getGenero().toString());
            pst.setString(6, contenido.getSinopsis());
            pst.setInt(7, contenido.getID());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar Contenido", e);
        }
    }

    public static boolean deleteContenido(int idContenido) {
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(DELETE_SQL)
        ) {
            pst.setInt(1, idContenido);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar Contenido", e);
        }
    }

    // ==================== NUEVOS METODOS ====================

    // Películas (global)
    public static List<Pelicula> getAllPeliculas() {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT c.*, p.duracion " +
                "FROM contenido c " +
                "JOIN pelicula p ON c.IDContenido = p.IDContenido";
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {
            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setID(rs.getInt("IDContenido"));
                pelicula.setDirector(rs.getString("director"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setEstado(Estado.valueOf(rs.getString("estado")));
                pelicula.setAnyoEstreno(rs.getInt("anyoEstreno"));
                pelicula.setGenero(Genero.valueOf(rs.getString("genero")));
                pelicula.setSinopsis(rs.getString("sinopsis"));
                pelicula.setDuracion(rs.getDouble("duracion"));
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las películas", e);
        }
        return peliculas;
    }

    // Series (global)
    public static List<Series> getAllSeries() {
        List<Series> seriesList = new ArrayList<>();
        String sql = "SELECT c.*, s.temporadas " +
                "FROM contenido c " +
                "JOIN series s ON c.IDContenido = s.IDContenido";
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {
            while (rs.next()) {
                Series serie = new Series();
                serie.setID(rs.getInt("IDContenido"));
                serie.setDirector(rs.getString("director"));
                serie.setTitulo(rs.getString("titulo"));
                serie.setEstado(Estado.valueOf(rs.getString("estado")));
                serie.setAnyoEstreno(rs.getInt("anyoEstreno"));
                serie.setGenero(Genero.valueOf(rs.getString("genero")));
                serie.setSinopsis(rs.getString("sinopsis"));
                serie.setTemporadas(rs.getInt("temporadas"));
                seriesList.add(serie);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las series", e);
        }
        return seriesList;
    }

    // Películas SOLO del usuario logueado
    public static List<Pelicula> getPeliculasPorUsuario(String nombreUsuario) {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT c.*, p.duracion " +
                "FROM usuario_contenido uc " +
                "JOIN contenido c ON uc.IDContenido = c.IDContenido " +
                "JOIN pelicula p ON c.IDContenido = p.IDContenido " +
                "WHERE uc.usuario = ?";
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {
            pst.setString(1, nombreUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Pelicula pelicula = new Pelicula();
                    pelicula.setID(rs.getInt("IDContenido"));
                    pelicula.setDirector(rs.getString("director"));
                    pelicula.setTitulo(rs.getString("titulo"));
                    pelicula.setEstado(Estado.valueOf(rs.getString("estado")));
                    pelicula.setAnyoEstreno(rs.getInt("anyoEstreno"));
                    pelicula.setGenero(Genero.valueOf(rs.getString("genero")));
                    pelicula.setSinopsis(rs.getString("sinopsis"));
                    pelicula.setDuracion(rs.getDouble("duracion"));
                    peliculas.add(pelicula);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener películas del usuario", e);
        }
        return peliculas;
    }

    // Series SOLO del usuario logueado
    public static List<Series> getSeriesPorUsuario(String nombreUsuario) {
        List<Series> seriesList = new ArrayList<>();
        String sql = "SELECT c.*, s.temporadas " +
                "FROM usuario_contenido uc " +
                "JOIN contenido c ON uc.IDContenido = c.IDContenido " +
                "JOIN series s ON c.IDContenido = s.IDContenido " +
                "WHERE uc.usuario = ?";
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ) {
            pst.setString(1, nombreUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Series serie = new Series();
                    serie.setID(rs.getInt("IDContenido"));
                    serie.setDirector(rs.getString("director"));
                    serie.setTitulo(rs.getString("titulo"));
                    serie.setEstado(Estado.valueOf(rs.getString("estado")));
                    serie.setAnyoEstreno(rs.getInt("anyoEstreno"));
                    serie.setGenero(Genero.valueOf(rs.getString("genero")));
                    serie.setSinopsis(rs.getString("sinopsis"));
                    serie.setTemporadas(rs.getInt("temporadas"));
                    seriesList.add(serie);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener series del usuario", e);
        }
        return seriesList;
    }
}

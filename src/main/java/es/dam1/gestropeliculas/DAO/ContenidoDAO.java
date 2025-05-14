package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.Contenido;
import es.dam1.gestropeliculas.model.Estado;
import es.dam1.gestropeliculas.model.Genero;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContenidoDAO {
    private final static String SQL_ALL = "SELECT * FROM contenido";
    private final static String SQL_INSERT = "INSERT INTO contenido (id, titulo, director, estado, anyo_estreno, genero, sinopsis) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE contenido SET titulo = ?, director = ?, estado = ?, anyo_estreno = ?, genero = ?, sinopsis = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM contenido WHERE id = ?";

    public static List<Contenido> findAll() {
        List<Contenido> contenidos = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionBD.getConnection().prepareStatement(SQL_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Contenido contenido = new Contenido();
                contenido.setID(rs.getInt("id"));
                contenido.setTitulo(rs.getString("titulo"));
                contenido.setDirector(rs.getString("director"));
                contenido.setEstado(Estado.valueOf(rs.getString("estado")));
                contenido.setAnyoEstreno(rs.getInt("anyo_estreno"));
                contenido.setGenero(Genero.valueOf(rs.getString("genero")));                contenido.setSinopsis(rs.getString("sinopsis"));
                contenidos.add(contenido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contenidos;
    }

    public static Contenido insertContenido(Contenido contenido) {
        if (contenido != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                pst.setInt(1, contenido.getID());
                pst.setString(2, contenido.getTitulo());
                pst.setString(3, contenido.getDirector());
                pst.setString(4, contenido.getEstado().toString());
                pst.setInt(5, contenido.getAnyoEstreno());
                pst.setString(6, contenido.getGenero().toString());
                pst.setString(7, contenido.getSinopsis());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return contenido;
    }

    public static boolean updateContenido(Contenido contenido) {
        boolean result = false;
        if (contenido != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                pst.setString(1, contenido.getTitulo());
                pst.setString(2, contenido.getDirector());
                pst.setString(3, contenido.getEstado().toString());
                pst.setInt(4, contenido.getAnyoEstreno());
                pst.setString(5, contenido.getGenero().toString());
                pst.setString(6, contenido.getSinopsis());
                pst.setInt(7, contenido.getID());
                pst.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static boolean deleteContenido(Contenido contenido) {
        boolean deleted = false;
        if (contenido != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                pst.setInt(1, contenido.getID());
                pst.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }
}
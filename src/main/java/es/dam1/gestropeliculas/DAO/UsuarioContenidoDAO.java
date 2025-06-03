package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.Usuario;
import es.dam1.gestropeliculas.model.Contenido;
import es.dam1.gestropeliculas.model.UsuarioContenido;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioContenidoDAO {

    private static final String INSERT_SQL = "INSERT INTO usuario_contenido (usuario, IDContenido, fechaAñadido) VALUES (?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM usuario_contenido WHERE usuario = ? AND IDContenido = ?";
    private static final String FIND_BY_USUARIO_SQL = "SELECT * FROM usuario_contenido WHERE usuario = ?";
    private static final String FIND_BY_CONTENIDO_SQL = "SELECT * FROM usuario_contenido WHERE IDContenido = ?";
    private static final String FIND_RELATION_SQL = "SELECT * FROM usuario_contenido WHERE usuario = ? AND IDContenido = ?";

    // Insertar relación usuario-contenido
    public static boolean insertarRelacion(UsuarioContenido uc) {
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(INSERT_SQL)
        ) {
            pst.setString(1, uc.getUsuario().getUsuario());
            pst.setInt(2, uc.getContenido().getID());
            pst.setDate(3, Date.valueOf(uc.getFechaAñadido()));
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar relación usuario-contenido", e);
        }
    }

    // Eliminar relación usuario-contenido
    public static boolean eliminarRelacion(Usuario usuario, Contenido contenido) {
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(DELETE_SQL)
        ) {
            pst.setString(1, usuario.getUsuario());
            pst.setInt(2, contenido.getID());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar relación usuario-contenido", e);
        }
    }

    // Buscar todos los contenidos de un usuario
    public static List<UsuarioContenido> buscarContenidosPorUsuario(Usuario usuario) {
        List<UsuarioContenido> lista = new ArrayList<>();
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(FIND_BY_USUARIO_SQL)
        ) {
            pst.setString(1, usuario.getUsuario());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Contenido contenido = ContenidoDAO.findById(rs.getInt("IDContenido"));
                    // Cambiado el nombre de la columna:
                    LocalDate fecha = rs.getDate("fechaAñadido").toLocalDate();
                    lista.add(new UsuarioContenido(usuario, contenido, fecha));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar contenidos de usuario", e);
        }
        return lista;
    }

    // Buscar todos los usuarios de un contenido
    public static List<UsuarioContenido> buscarUsuariosPorContenido(Contenido contenido) {
        List<UsuarioContenido> lista = new ArrayList<>();
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(FIND_BY_CONTENIDO_SQL)
        ) {
            pst.setInt(1, contenido.getID());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = UsuarioDAO.findById(rs.getString("usuario"));
                    // Cambiado el nombre de la columna:
                    LocalDate fecha = rs.getDate("fechaAñadido").toLocalDate();
                    lista.add(new UsuarioContenido(usuario, contenido, fecha));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuarios de un contenido", e);
        }
        return lista;
    }

    // Comprobar si existe la relación usuario-contenido
    public static boolean existeRelacion(Usuario usuario, Contenido contenido) {
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(FIND_RELATION_SQL)
        ) {
            pst.setString(1, usuario.getUsuario());
            pst.setInt(2, contenido.getID());
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al comprobar existencia de relación usuario-contenido", e);
        }
    }
}

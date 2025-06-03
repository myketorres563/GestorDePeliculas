package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.Contenido;
import es.dam1.gestropeliculas.model.Usuario;
import es.dam1.gestropeliculas.model.UsuarioContenido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String SQL_INSERT = "INSERT INTO usuario (Usuario, Email, Contraseña) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE usuario SET Usuario = ?, Email = ?, Contraseña = ? WHERE Usuario = ?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE Usuario = ?";
    private static final String SQL_VALIDAR_CREDENCIALES = "SELECT * FROM usuario WHERE Usuario = ? AND Contraseña = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM usuario WHERE Usuario = ?";
    private static final String SQL_USUARIO_CONTENIDOS = "SELECT * FROM usuario_contenido WHERE Usuario = ?";

    // Insertar usuario
    public static boolean insertUsuario(Usuario usuario) {
        boolean result = false;
        if (usuario != null) {
            try (
                    Connection conn = ConnectionBD.getConnection();
                    PreparedStatement pst = conn.prepareStatement(SQL_INSERT)
            ) {
                pst.setString(1, usuario.getUsuario());
                pst.setString(2, usuario.getEmail());
                pst.setInt(3, usuario.getContrasenia());
                pst.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    // Actualizar usuario
    public static boolean updateUsuario(Usuario usuario) {
        boolean result = false;
        if (usuario != null) {
            try (
                    Connection conn = ConnectionBD.getConnection();
                    PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)
            ) {
                pst.setString(1, usuario.getUsuario());
                pst.setString(2, usuario.getEmail());
                pst.setInt(3, usuario.getContrasenia());
                pst.setString(4, usuario.getUsuario());
                pst.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    // Eliminar usuario
    public static boolean deleteUsuario(String usuario) {
        boolean result = false;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_DELETE)
        ) {
            pst.setString(1, usuario);
            pst.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    // Validar credenciales
    public static boolean validarCredenciales(String usuario, String contrasenia) {
        boolean valido = false;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_VALIDAR_CREDENCIALES)
        ) {
            pst.setString(1, usuario);
            pst.setString(2, contrasenia);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    valido = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valido;
    }

    // Obtener usuario y sus UsuarioContenido (Eager)
    public static Usuario findById(String usuario) {
        Usuario user = null;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_FIND_BY_ID)
        ) {
            pst.setString(1, usuario);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user = new Usuario(
                            rs.getString("Usuario"),
                            rs.getString("Email"),
                            rs.getInt("Contraseña")
                    );
                    // EAGER: cargar todos los UsuarioContenido del usuario
                    user.setUsuarioContenidos(cargarUsuarioContenidos(user));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Método auxiliar para cargar UsuarioContenido de un usuario
    private static List<UsuarioContenido> cargarUsuarioContenidos(Usuario usuario) {
        List<UsuarioContenido> lista = new ArrayList<>();
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(SQL_USUARIO_CONTENIDOS)
        ) {
            pst.setString(1, usuario.getUsuario());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Contenido contenidoObj = ContenidoDAO.findById(rs.getInt("IdContenido")); // El Contenido SÍ se carga completo
                    LocalDate fecha = rs.getDate("FechaAñadido").toLocalDate();

                    UsuarioContenido uc = new UsuarioContenido(
                            usuario,        // Usar el objeto que ya tienes
                            contenidoObj,
                            fecha
                    );
                    lista.add(uc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

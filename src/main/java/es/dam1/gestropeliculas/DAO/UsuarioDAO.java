package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    // Consultas SQL
    private static final String SQL_INSERT = "INSERT INTO usuario (Usuario, Email, Contraseña) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE usuario SET Usuario = ?, Email = ?, Contraseña = ? WHERE Usuario = ?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE Usuario = ?";
    private static final String SQL_SELECT = "SELECT * FROM usuario";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM usuario WHERE Usuario = ?";
    private static final String SQL_VALIDAR_CREDENCIALES = "SELECT * FROM usuario WHERE Usuario = ? AND Contraseña = ?";


    /**
     * Método que inserta un nuevo usuario en la base de datos.
     * @param usuario objeto de tipo Usuario a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public static boolean insertUsuario(Usuario usuario) {
        boolean result = false;
        if (usuario != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                pst.setString(1, usuario.getUsuario());
                pst.setString(2, usuario.getEmail());
                pst.setString(3, usuario.getContrasenia());
                pst.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * Método que actualiza los datos de un usuario en la base de datos.
     * @param usuario objeto de tipo Usuario con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public static boolean updateUsuario(Usuario usuario) {
        boolean result = false;
        if (usuario != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                pst.setString(1, usuario.getUsuario());
                pst.setString(2, usuario.getEmail());
                pst.setString(3, usuario.getContrasenia());
                pst.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * Método que elimina un usuario de la base de datos.
     * @param id identificador del usuario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public static boolean deleteUsuario(int id) {
        boolean result = false;
        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Método que obtiene todos los usuarios de la base de datos.
     * @return Lista de usuarios
     */
    public static List<Usuario> selectAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contraseña")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    /**
     * Método que obtiene un usuario específico de la base de datos.
     * @param id identificador del usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    public static Usuario selectUsuarioById(int id) {
        Usuario usuario = null;
        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_SELECT_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("contraseña")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }



    /**
     * Método que valida si un usuario con esa contraseña existe en la base de datos.
     * @param usuario nombre del usuario
     * @param contrasenia contraseña del usuario
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public static boolean validarCredenciales(String usuario, String contrasenia) {
        boolean valido = false;
        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_VALIDAR_CREDENCIALES)) {
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

}
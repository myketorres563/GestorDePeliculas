package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String SQL_INSERT = "INSERT INTO usuario (Usuario, Email, Contraseña) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE usuario SET Usuario = ?, Email = ?, Contraseña = ? WHERE Usuario = ?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE Usuario = ?";
    private static final String SQL_VALIDAR_CREDENCIALES = "SELECT * FROM usuario WHERE Usuario = ? AND Contraseña = ?";



    public static boolean insertUsuario(Usuario usuario) {
        boolean result = false;
        if (usuario != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
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


    public static boolean updateUsuario(Usuario usuario) {
        boolean result = false;
        if (usuario != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                pst.setString(1, usuario.getUsuario());
                pst.setString(2, usuario.getEmail());
                pst.setInt(3, usuario.getContrasenia());
                pst.setString(4, usuario.getUsuario()); // usuario actual como clave para actualizar
                pst.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }



    public static boolean deleteUsuario(String usuario) {
        boolean result = false;
        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
            pst.setString(1, usuario);
            pst.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }



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








    private static final String SQL_FIND_BY_ID = "SELECT * FROM usuario WHERE Usuario = ?";

    public static Usuario findById(String usuario) {
        Usuario user = null;

        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            pst.setString(1, usuario);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user = new Usuario(
                            rs.getString("Usuario"),
                            rs.getString("Email"),
                            rs.getInt("Contraseña")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


}
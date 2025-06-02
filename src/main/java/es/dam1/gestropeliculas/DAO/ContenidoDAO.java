package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.*;

import java.sql.*;

public class ContenidoDAO {

    private static final String SQL_ALL         = "SELECT * FROM contenido";
    private static final String INSERT_SQL      = "INSERT INTO contenido (director, titulo, estado, anyoEstreno, genero, sinopsis) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL      = "UPDATE contenido SET director = ?, titulo = ?, estado = ?, anyoEstreno = ?, genero = ?, sinopsis = ? WHERE IDContenido = ?";
    private static final String DELETE_SQL      = "DELETE FROM contenido WHERE IDContenido = ?";
    private static final String FIND_BY_ID_SQL  = "SELECT * FROM contenido WHERE IDContenido = ?";

    /**
     * Busca un contenido por su ID.
     *
     * @param id ID del contenido a buscar.
     * @return Objeto Contenido si existe, null si no existe.
     */
    public static Contenido findById(int id) {
        Contenido contenido = null;
        try (
                Connection conn = ConnectionBD.getConnection();
                PreparedStatement pst = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String director = rs.getString("director");

                    contenido = new Contenido();
                    contenido.setID(rs.getInt("IDContenido"));
                    contenido.setDirector(director); // Ahora es String
                    contenido.setTitulo(rs.getString("titulo"));
                    contenido.setEstado(Estado.valueOf(rs.getString("estado")));
                    contenido.setAnyoEstreno(rs.getInt("anyoEstreno"));
                    contenido.setGenero(Genero.valueOf(rs.getString("genero")));
                    contenido.setSinopsis(rs.getString("sinopsis"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar Contenido por ID", e);
        }
        return contenido;
    }

    /**
     * Inserta un nuevo contenido en la base de datos.
     *
     * @param contenido Objeto Contenido a insertar.
     * @return ID generado para el nuevo contenido, -1 si falla.
     */
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

    /**
     * Actualiza un contenido existente en la base de datos.
     *
     * @param contenido Objeto Contenido con los datos actualizados.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
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

    /**
     * Elimina un contenido por su ID.
     *
     * @param idContenido ID del contenido a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
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
}

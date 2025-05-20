package es.dam1.gestropeliculas.DAO;

import es.dam1.gestropeliculas.baseDeDatos.ConnectionBD;
import es.dam1.gestropeliculas.model.Director;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DirectorDAO {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM director WHERE ID = ?";
    private static final String SQL_INSERT = "INSERT INTO director (nombre, edad, nacionalidad) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE director SET nombre = ?, edad = ?, nacionalidad = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM director WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM director";


    public static List<Director> findAll() {
        List<Director> directores = new ArrayList<>();

        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Director director = new Director(
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("nacionalidad"),
                        rs.getInt("id")
                );
                directores.add(director);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return directores;
    }



    public static boolean insertDirector(Director director) {
        boolean insertado = false;

        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
            pst.setString(1, director.getNombre());
            pst.setInt(2, director.getEdad());
            pst.setString(3, director.getNacionalidad());

            insertado = pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return insertado;
    }



    public static boolean updateDirector(Director director) {
        boolean result = false;
        if (director != null) {
            try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                pst.setString(1, director.getNombre());
                pst.setInt(2, director.getEdad());
                pst.setString(3, director.getNacionalidad());
                pst.setInt(4, director.getID());
                pst.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static boolean deleteDirector(int id) {
        boolean eliminado = false;

        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            eliminado = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eliminado;
    }





    public static Director findById(int id) {
        Director director = null;

        try (PreparedStatement pst = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    director = new Director(
                            rs.getString("nombre"),
                            rs.getInt("edad"),
                            rs.getString("nacionalidad"),
                            rs.getInt("ID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return director;
    }




}
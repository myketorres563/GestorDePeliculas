package es.dam1.gestropeliculas.test;

import es.dam1.gestropeliculas.DAO.ContenidoDAO;
import es.dam1.gestropeliculas.DAO.DirectorDAO;
import es.dam1.gestropeliculas.DAO.SerieDAO;
import es.dam1.gestropeliculas.DAO.UsuarioDAO;
import es.dam1.gestropeliculas.model.*;

public class SeriesTest {
    public static void main(String[] args) {
        // Obtener el usuario y director ya existentes en la base de datos
        Usuario usuario = UsuarioDAO.findById("miguel");
        Director director = DirectorDAO.findById(1);

        if (usuario == null || director == null) {
            System.out.println("No se encontró el usuario o el director. Asegúrate de que existan en la base de datos.");
            return;
        }

        // Crear contenido base usando setters
        Contenido temp = new Contenido();
        temp.setID(3001); // IDContenido único
        temp.setUsuario(usuario);
        temp.setDirector(director);
        temp.setTitulo("Stranger Things");
        temp.setEstado(Estado.EN_PROCESO);
        temp.setAnyoEstreno(2016);
        temp.setGenero(Genero.FANTASIA);
        temp.setSinopsis("Niños enfrentan sucesos paranormales.");

        // Usar constructor de copia
        Contenido contenido = new Contenido(temp);

        // Insertar el contenido en la base de datos
        ContenidoDAO.insertContenido(contenido);

        // Crear la serie con el contenido ya insertado
        Series serie = new Series(contenido, 4); // 4 temporadas

        // Insertar en tabla series
        SerieDAO.insertSerie(serie);

        System.out.println("Serie insertada correctamente.");
    }
}

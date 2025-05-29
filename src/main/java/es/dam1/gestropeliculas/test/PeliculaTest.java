package es.dam1.gestropeliculas.test;

import es.dam1.gestropeliculas.DAO.ContenidoDAO;
import es.dam1.gestropeliculas.DAO.PeliculaDAO;
import es.dam1.gestropeliculas.DAO.UsuarioDAO;
import es.dam1.gestropeliculas.model.*;

public class PeliculaTest {
    public static void main(String[] args) {
        // Obtener un usuario existente
        Usuario usuario = UsuarioDAO.findById("miguel");

        // Obtener un director existente
        Director director = DirectorDAO.findById(1);

        if (usuario == null || director == null) {
            System.out.println("No se encontró el usuario o el director. Asegúrate de que existan en la base de datos.");
            return;
        }

        // Crear y rellenar el contenido
        Contenido temp = new Contenido();
        temp.setID(1001); // Asegúrate de que este ID no esté ya en la BBDD
        temp.setUsuario(usuario);
        temp.setDirector(director);
        temp.setTitulo("Matrix");
        temp.setEstado(Estado.NO_VISTA);
        temp.setAnyoEstreno(1999);
        temp.setGenero(Genero.ACCION);
        temp.setSinopsis("Un hacker descubre la verdad sobre su mundo.");

        // Usar constructor de copia
        Contenido contenido = new Contenido(temp);

        // Insertar el contenido
        ContenidoDAO.insertContenido(contenido);

        // Crear la película asociada con ese contenido
        Pelicula pelicula = new Pelicula(contenido, 136.5); // duración en minutos

        // Insertar la película
        PeliculaDAO.insertPelicula(pelicula);

        System.out.println("Película insertada correctamente.");
    }
}

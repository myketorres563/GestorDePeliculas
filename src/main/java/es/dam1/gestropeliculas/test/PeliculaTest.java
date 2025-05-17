package es.dam1.gestropeliculas.test;

import es.dam1.gestropeliculas.DAO.PeliculaDAO;
import es.dam1.gestropeliculas.model.Estado;
import es.dam1.gestropeliculas.model.Genero;
import es.dam1.gestropeliculas.model.Pelicula;

public class PeliculaTest {
    public static void main(String[] args) {
        // Crear una nueva película
        Pelicula nuevaPelicula = new Pelicula(
                1,
                "Inception",
                "Christopher Nolan",
                Estado.PENDIENTE,
                2010,
                Genero.ACCION,
                "Un ladrón que roba secretos a través de los sueños.",
                148.0
        );        // Intentar añadir la película a la base de datos
        Pelicula peliculaInsertada = PeliculaDAO.insertPelicula(nuevaPelicula);

        // Verificar si se añadió correctamente
        if (peliculaInsertada != null) {
            System.out.println("Película añadida correctamente: " + peliculaInsertada.getTitulo());
        } else {
            System.out.println("Error al añadir la película.");
        }
    }
}
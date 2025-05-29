package es.dam1.gestropeliculas.test;

public class DirectorTest {
    public static void main(String[] args) {
        Director director = new Director("Christopher Nolan", 53, "Reino Unido", 1);
        DirectorDAO.insertDirector(director);
        System.out.println("Director insertado correctamente.");
    }
}

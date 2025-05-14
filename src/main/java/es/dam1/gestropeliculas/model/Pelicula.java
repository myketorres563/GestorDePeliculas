package es.dam1.gestropeliculas.model;

public class Pelicula extends Contenido{
    private double duracion;

    public Pelicula(int ID, String titulo, String director, Estado estado, int anyoEstreno, Genero genero, String sinopsis, double duracion) {
        super(ID, titulo, director, estado, anyoEstreno, genero, sinopsis);
        this.duracion = duracion;
    }
    public Pelicula() {
        super();
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }


    @Override
    public String toString() {
        return super.toString() + ", duracion=" + duracion;
    }

}

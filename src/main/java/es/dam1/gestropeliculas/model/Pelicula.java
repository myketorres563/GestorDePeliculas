package es.dam1.gestropeliculas.model;

public class Pelicula extends Contenido{
    private double duracion;

    public Pelicula(Contenido contenido, double duracion) {
        super(contenido); // Llama al constructor de copia de Contenido
        this.duracion = duracion;
    }


    public Pelicula() {
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

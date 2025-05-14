package es.dam1.gestropeliculas.model;

public class Series extends Contenido{
    private int temporadas;

    public Series(int ID, String titulo, String director, Estado estado, int anyoEstreno, Genero genero, String sinopsis, int temporadas) {
        super(ID, titulo, director, estado, anyoEstreno, genero, sinopsis);
        this.temporadas = temporadas;
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    @Override
    public String toString() {
        return super.toString() + ", temporadas=" + temporadas;
    }

}
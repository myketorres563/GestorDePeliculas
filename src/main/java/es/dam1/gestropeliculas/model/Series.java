package es.dam1.gestropeliculas.model;

public class Series extends Contenido {
    private int temporadas;

    // Constructor que usa Usuario y Director como en Contenido
    public Series(Contenido contenido, int temporadas) {
        super(contenido); // Esto requiere que Contenido tenga un constructor copia
        this.temporadas = temporadas;
    }


    public Series() {
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

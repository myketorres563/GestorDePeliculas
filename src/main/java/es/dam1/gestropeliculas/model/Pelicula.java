package es.dam1.gestropeliculas.model;

public class Pelicula {
    private int ID;
    private String titulo;
    private String director;
    private Estado estado;
    private int anyoEstreno;
    private String genero;
    private String sinopsis;

    public Pelicula(String nombre, String director, int anyoEstreno, String genero, String sinopsis, int ID) {
        this.titulo = nombre;
        this.director = director;
        this.anyoEstreno = anyoEstreno;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAnyoEstreno() {
        return anyoEstreno;
    }

    public void setAnyoEstreno(int anyoEstreno) {
        this.anyoEstreno = anyoEstreno;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "ID='" + ID + '\'' +
                ", titulo='" + titulo + '\'' +
                ", director='" + director + '\'' +
                ", estado='" + estado + '\'' +
                ", anyoEstreno=" + anyoEstreno +
                ", genero='" + genero + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                '}';
    }
}

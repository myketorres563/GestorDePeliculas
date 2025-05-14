package es.dam1.gestropeliculas.model;

public class Contenido {
    private int ID;
    private String titulo;
    private String director;
    private Estado estado;
    private int anyoEstreno;
    private Genero genero;
    private String sinopsis;

    public Contenido(int ID, String titulo, String director, Estado estado, int anyoEstreno, Genero genero, String sinopsis) {
        this.ID = ID;
        this.titulo = titulo;
        this.director = director;
        this.estado = estado;
        this.anyoEstreno = anyoEstreno;
        this.genero = genero;
        this.sinopsis = sinopsis;
    }

    public Contenido() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getAnyoEstreno() {
        return anyoEstreno;
    }

    public void setAnyoEstreno(int anyoEstreno) {
        this.anyoEstreno = anyoEstreno;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public String toString() {
        return "Contenido{" +
                "ID=" + ID +
                ", titulo='" + titulo + '\'' +
                ", director='" + director + '\'' +
                ", estado=" + estado +
                ", anyoEstreno=" + anyoEstreno +
                ", genero='" + genero + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                '}';
    }
}

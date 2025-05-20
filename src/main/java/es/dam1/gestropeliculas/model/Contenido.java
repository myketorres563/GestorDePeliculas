package es.dam1.gestropeliculas.model;

public class Contenido {
    private Usuario usuario;
    private int ID;
    private String titulo;
    private Director director;
    private Estado estado;
    private int anyoEstreno;
    private Genero genero;
    private String sinopsis;

    public Contenido(int ID, Usuario usuario, Director director, String titulo,
                     Estado estado, int anyoEstreno, Genero genero, String sinopsis) {
        this.ID = ID;
        this.usuario = usuario;
        this.director = director;
        this.titulo = titulo;
        this.estado = estado;
        this.anyoEstreno = anyoEstreno;
        this.genero = genero;
        this.sinopsis = sinopsis;
    }
    public Contenido(Contenido otro) {
        this.ID = otro.ID;
        this.usuario = otro.usuario;
        this.director = otro.director;
        this.titulo = otro.titulo;
        this.estado = otro.estado;
        this.anyoEstreno = otro.anyoEstreno;
        this.genero = otro.genero;
        this.sinopsis = otro.sinopsis;
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

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
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

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Contenido{" +
                "usuario=" + usuario +
                ", ID=" + ID +
                ", titulo='" + titulo + '\'' +
                ", director=" + director +
                ", estado=" + estado +
                ", anyoEstreno=" + anyoEstreno +
                ", genero=" + genero +
                ", sinopsis='" + sinopsis + '\'' +
                '}';
    }
}
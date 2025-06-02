package es.dam1.gestropeliculas.model;

import java.util.ArrayList;
import java.util.List;

public class Contenido {
    private int ID;
    private String titulo;
    private String director;
    private Estado estado;
    private int anyoEstreno;
    private Genero genero;
    private String sinopsis;

    // Lista de relaciones con usuarios
    private List<UsuarioContenido> usuarioContenidos = new ArrayList<>();

    public Contenido(int ID, String director, String titulo, Estado estado, int anyoEstreno, Genero genero, String sinopsis) {
        this.ID = ID;
        this.director = director;
        this.titulo = titulo;
        this.estado = estado;
        this.anyoEstreno = anyoEstreno;
        this.genero = genero;
        this.sinopsis = sinopsis;
    }

    public Contenido(Contenido otro) {
        this.ID = otro.ID;
        this.director = otro.director;
        this.titulo = otro.titulo;
        this.estado = otro.estado;
        this.anyoEstreno = otro.anyoEstreno;
        this.genero = otro.genero;
        this.sinopsis = otro.sinopsis;
    }

    public Contenido() {}

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

    public List<UsuarioContenido> getUsuarioContenidos() {
        return usuarioContenidos;
    }

    public void setUsuarioContenidos(List<UsuarioContenido> usuarioContenidos) {
        this.usuarioContenidos = usuarioContenidos;
    }

    // Método para obtener los usuarios que han añadido este contenido
    public List<Usuario> getUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        for (UsuarioContenido uc : usuarioContenidos) {
            lista.add(uc.getUsuario());
        }
        return lista;
    }

    @Override
    public String toString() {
        return "Contenido{" +
                "ID=" + ID +
                ", titulo='" + titulo + '\'' +
                ", director=" + director +
                ", estado=" + estado +
                ", anyoEstreno=" + anyoEstreno +
                ", genero=" + genero +
                ", sinopsis='" + sinopsis + '\'' +
                '}';
    }
}

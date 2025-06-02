package es.dam1.gestropeliculas.model;

import java.time.LocalDate;

public class UsuarioContenido {
    private Usuario usuario;
    private Contenido contenido;
    private LocalDate fechaAñadido;

    public UsuarioContenido(Usuario usuario, Contenido contenido, LocalDate fechaAñadido) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.fechaAñadido = fechaAñadido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFechaAñadido() {
        return fechaAñadido;
    }

    public void setFechaAñadido(LocalDate fechaAñadido) {
        this.fechaAñadido = fechaAñadido;
    }

    @Override
    public String toString() {
        return "UsuarioContenido{" +
                "usuario=" + usuario +
                ", contenido=" + contenido +
                ", fechaAñadido=" + fechaAñadido +
                '}';
    }
}
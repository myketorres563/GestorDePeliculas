package es.dam1.gestropeliculas.model;
import java.time.LocalDate;
import java.util.List;


public class UsuarioContenido {
    private Usuario usuario;
    private Contenido contenido;
    private LocalDate fechaAñadido;

    List<Usuario> usuarios;
    List<Contenido> contenidos;
















    public UsuarioContenido(Usuario usuario, LocalDate fechaAñadido ,Contenido contenido) {
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

    @Override
    public String toString() {
        return "UsuarioContenido{" +
                "usuario=" + usuario +
                ", contenido=" + contenido +
                ", fechaAñadido=" + fechaAñadido +
                '}';
    }
}

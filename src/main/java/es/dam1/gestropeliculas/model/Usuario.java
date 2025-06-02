package es.dam1.gestropeliculas.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String usuario;
    private int contrasenia;
    private String email;

    // Lista de relaciones con contenidos
    private List<UsuarioContenido> usuarioContenidos = new ArrayList<>();

    public Usuario(String usuario, String email, int contrasenia) {
        this.usuario = usuario;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    public int getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(int contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<UsuarioContenido> getUsuarioContenidos() {
        return usuarioContenidos;
    }

    public void setUsuarioContenidos(List<UsuarioContenido> usuarioContenidos) {
        this.usuarioContenidos = usuarioContenidos;
    }

    public class Sesion {
        private static Usuario usuarioActual;
        public static Usuario getUsuarioActual() { return usuarioActual; }
        public static void setUsuarioActual(Usuario usuario) { usuarioActual = usuario; }
    }


    // Método para obtener solo los contenidos asociados a este usuario
    public List<Contenido> getContenidos() {
        List<Contenido> lista = new ArrayList<>();
        for (UsuarioContenido uc : usuarioContenidos) {
            lista.add(uc.getContenido());
        }
        return lista;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuario='" + usuario + '\'' +
                ", contrasenia=" + contrasenia +
                ", email='" + email + '\'' +
                '}';
    }
}

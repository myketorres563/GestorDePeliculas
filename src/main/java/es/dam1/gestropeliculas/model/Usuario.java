package es.dam1.gestropeliculas.model;

import java.util.List;

public class Usuario {
    private String usuario;
    private int contrasenia;
    private String email;

    List<UsuarioContenido> contenidos;












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

    @Override
    public String toString() {
        return "Usuario{" +
                "usuario='" + usuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

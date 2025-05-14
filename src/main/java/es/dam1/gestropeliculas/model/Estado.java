package es.dam1.gestropeliculas.model;

public enum Estado {
    VISTA("La película ha sido vista"),
    NO_VISTA("La película no ha sido vista"),
    EN_PROCESO("La película está siendo vista actualmente"),
    ABANDONADA("La película fue abandonada"),
    PENDIENTE("La película está pendiente de ver");
    
    private final String mensaje;
    
    Estado(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getMensaje() {
        return mensaje;
    }
}
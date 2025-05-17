package es.dam1.gestropeliculas.controler;

public enum Scenes {
    WELCOME("/view/pantallaInicial.fxml"),
    ROOT("/view/layout.fxml");

    private String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

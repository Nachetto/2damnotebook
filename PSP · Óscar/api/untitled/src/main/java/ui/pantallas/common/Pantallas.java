package ui.pantallas.common;

import lombok.Getter;

@Getter
public enum Pantallas {
    INICIO ("/fxml/main.fxml"),
    LISTAR_PERSONAJES("/fxml/character/characterMain.fxml"),
    LISTAR_EPISODIOS("/fxml/episode.fxml"),
    LISTAR_TEMPORADAS("/fxml/season.fxml");

    private final String ruta;
    Pantallas(String ruta) {
        this.ruta=ruta;
    }
}

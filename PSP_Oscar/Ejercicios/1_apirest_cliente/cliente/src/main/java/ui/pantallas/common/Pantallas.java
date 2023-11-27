package ui.pantallas.common;

import lombok.Getter;

@Getter
public enum Pantallas {
    INICIO ("/fxml/main.fxml"),
    LLAMADAS_PERSONAJES("/fxml/character/characterCalls.fxml"),
    LISTAR_PERSONAJES("/fxml/character/characterMain.fxml"),
    LISTAR_EPISODIOS("/fxml/episode/episode.fxml"),
    LLAMADAS_EPISODIOS("/fxml/episode/episodeList.fxml"),
    LISTAR_TEMPORADAS("/fxml/season/seasonMain.fxml");


    private final String ruta;
    Pantallas(String ruta) {
        this.ruta=ruta;
    }
}

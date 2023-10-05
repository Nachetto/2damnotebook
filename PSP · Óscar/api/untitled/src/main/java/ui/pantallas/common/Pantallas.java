package ui.pantallas.common;

import lombok.Getter;

@Getter
public enum Pantallas {
    INICIO ("/fxml/main.fxml");

    private final String ruta;
    Pantallas(String ruta) {
        this.ruta=ruta;
    }
}

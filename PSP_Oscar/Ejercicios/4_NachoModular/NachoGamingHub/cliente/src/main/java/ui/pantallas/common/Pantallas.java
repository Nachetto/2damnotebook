package ui.pantallas.common;

import lombok.Getter;

@Getter
public enum Pantallas {LOGIN("/fxml/login/login.fxml"),
    INICIO ("/fxml/main.fxml"),
    LISTAR_JUEGOS ("/fxml/juegos/juegos.fxml"),
    LISTAR_SUSCRIPCIONES ("/fxml/articulos/articulos.fxml"),
    LISTAR_USUARIOS("/fxml/usuarios/usuarios.fxml");


    private final String ruta;
    Pantallas(String ruta) {
        this.ruta=ruta;
    }
}

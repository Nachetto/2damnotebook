package ui.pantallas.juegos;

import domain.errores.ClienteError;
import domain.modelo.Juego;
import domain.modelo.Suscripcion;
import lombok.Getter;
import ui.pantallas.common.BaseState;

import java.util.List;
import java.util.UUID;

@Getter
public class JuegosState extends BaseState {
    private final Juego juegoSeleccionado;
    private final List<Juego> juegos;
    private final List<Suscripcion> suscripciones;

    public JuegosState(ClienteError error, UUID idUsuarioLogueado, Juego juegoSeleccionado, List<Suscripcion> suscripciones, List<Juego> juegos) {
        super(error, idUsuarioLogueado);
        this.juegos = juegos;
        this.juegoSeleccionado = juegoSeleccionado;
        this.suscripciones = suscripciones;
    }
}

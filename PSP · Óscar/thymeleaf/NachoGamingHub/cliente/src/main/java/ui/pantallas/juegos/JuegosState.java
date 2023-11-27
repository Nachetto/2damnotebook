package ui.pantallas.juegos;

import domain.errores.ClienteError;
import domain.modelo.Juego;
import domain.modelo.Suscripcion;
import lombok.Getter;
import lombok.Setter;
import ui.pantallas.common.BaseState;

import java.util.List;

@Getter
@Setter
public class JuegosState extends BaseState {
    private Juego juegoSeleccionado;
    private String mensaje;
    private List<Juego> juegos;
    private List<Suscripcion> suscripciones;

    public JuegosState(boolean loading, ClienteError error, Juego juegoSeleccionado, List<Suscripcion> suscripciones) {
        super(loading, error);
        this.juegoSeleccionado = juegoSeleccionado;
        this.suscripciones = suscripciones;
    }
}

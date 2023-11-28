package ui.pantallas.common;

import domain.errores.ClienteError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseState {
    private final ClienteError error;
    private final int idUsuarioLogueado;
    protected BaseState(ClienteError error, int idUsuarioLogueado) {
        this.error = error;
        this.idUsuarioLogueado = idUsuarioLogueado;
    }
}

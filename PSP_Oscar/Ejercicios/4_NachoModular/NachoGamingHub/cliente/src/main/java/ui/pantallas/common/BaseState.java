package ui.pantallas.common;

import domain.errores.ClienteError;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class BaseState {
    private final ClienteError error;
    private final UUID idUsuarioLogueado;
    protected BaseState(ClienteError error, UUID idUsuarioLogueado) {
        this.error = error;
        this.idUsuarioLogueado = idUsuarioLogueado;
    }
}

package ui.pantallas.common;

import domain.errores.ClienteError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseState {
    private final boolean loading;
    private final ClienteError error;

    protected BaseState(boolean loading, ClienteError error) {
        this.loading = loading;
        this.error = error;
    }
}

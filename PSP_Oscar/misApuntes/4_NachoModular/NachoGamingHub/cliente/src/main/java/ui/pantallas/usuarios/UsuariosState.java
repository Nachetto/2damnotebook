package ui.pantallas.usuarios;

import domain.errores.ClienteError;
import domain.modelo.Usuario;
import lombok.Getter;
import ui.pantallas.common.BaseState;

import java.util.List;
import java.util.UUID;
@Getter
public class UsuariosState extends BaseState {
    private final List<Usuario> usuarios;
    public UsuariosState(ClienteError error, UUID idUsuarioLogueado, List<Usuario> usuarios) {
        super(error, idUsuarioLogueado);
        this.usuarios = usuarios;
    }
}

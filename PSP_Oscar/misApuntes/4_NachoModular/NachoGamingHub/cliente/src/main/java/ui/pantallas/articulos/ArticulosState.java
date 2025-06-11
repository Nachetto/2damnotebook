package ui.pantallas.articulos;

import domain.errores.ClienteError;
import domain.modelo.Articulo;
import lombok.Getter;
import ui.pantallas.common.BaseState;

import java.util.List;
import java.util.UUID;

@Getter
public class ArticulosState extends BaseState {
    private final List<Articulo> articulos;
    public ArticulosState(ClienteError error, UUID idUsuarioLogueado, List<Articulo> articulos) {
        super(error, idUsuarioLogueado);
        this.articulos = articulos;
    }
}

package ui.pantallas.principal;

import lombok.Data;

@Data
public class ParametrosBusquedaEpisode {
    private String tipoBusqueda;
    private String argumentoBusqueda;
    private int limite;
}

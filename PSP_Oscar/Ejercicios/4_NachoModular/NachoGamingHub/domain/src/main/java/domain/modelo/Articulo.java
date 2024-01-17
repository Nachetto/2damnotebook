package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {
    private UUID uuid;
    private String titulo;
    private String contenido;
    private UUID juegoID;
    private UUID usuarioID;
    private LocalDate fechaPublicacion;
}
package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Juego {
    private UUID uuid;
    private String titulo;
    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private String genero;
    private String descripcion;
}

package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion {
    private UUID uuid;
    private UUID usuarioID;
    private UUID juegoID;
    private LocalDate fechaSuscripcion;
}
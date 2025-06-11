package domain.modelo;

import lombok.Data;

import java.time.LocalDate;
@Data
public class MiSeason {
    private final int number;
    private final LocalDate fecha;
}

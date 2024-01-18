package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Usuario {
    private String username;
    private String password;
    private boolean activated = false;
    private LocalDate lastLifeSignal; //ultima vez que se conecto
    private String activationCode;
}

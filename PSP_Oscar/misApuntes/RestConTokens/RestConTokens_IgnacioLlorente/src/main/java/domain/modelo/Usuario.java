package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Usuario {
    private String username;
    private String password;
    private boolean activated;
    private LocalDateTime lastLifeSignal; //ultima vez que se conecto
    private String activationCode;
}

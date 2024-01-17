package domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private UUID uuid;
    private String nombre;
    private String correoElectronico;
    private String contrasena; // Almacenada como hash
    private LocalDate fechaNacimiento;
}
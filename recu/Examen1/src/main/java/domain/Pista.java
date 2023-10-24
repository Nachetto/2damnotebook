package domain;

import lombok.*;

@Data
@AllArgsConstructor
public class Pista {
    protected String nombre, provinca;
    protected int id, km;
}

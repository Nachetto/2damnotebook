package domain;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Pista implements Serializable {
    protected String nombre, provincia;
    protected int id, km;
}

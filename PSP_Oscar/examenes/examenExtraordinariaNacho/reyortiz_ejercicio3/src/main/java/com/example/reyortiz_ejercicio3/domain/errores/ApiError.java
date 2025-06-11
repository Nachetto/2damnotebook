package com.example.reyortiz_ejercicio3.domain.errores;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ApiError {
    private String mensaje;
}

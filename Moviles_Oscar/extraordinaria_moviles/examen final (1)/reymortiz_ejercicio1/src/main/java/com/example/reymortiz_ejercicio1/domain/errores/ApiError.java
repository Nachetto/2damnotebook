package com.example.reymortiz_ejercicio1.domain.errores;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ApiError {
    private String mensaje;
}

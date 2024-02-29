package com.example.ejercicio3examenpsp.data.error;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorObject {
    private String message;
    private LocalDateTime fecha;
}

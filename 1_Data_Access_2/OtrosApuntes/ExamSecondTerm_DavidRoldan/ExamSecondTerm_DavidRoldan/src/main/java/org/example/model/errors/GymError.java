package org.example.model.errors;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GymError {
    private final String message;

    private final LocalDateTime date;

    public GymError(String message) {
        this.message = message;
        this.date = LocalDateTime.now();
    }
}

package common;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RestaurantError {
    private final int number;
    private final String message;
    private final LocalDateTime date;

    public RestaurantError(int number, String message) {
        this.number = number;
        this.message = message;
        this.date = LocalDateTime.now();
    }

}

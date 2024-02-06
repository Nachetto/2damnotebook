package model;

import lombok.*;
import org.bson.types.ObjectId;

import java.math.BigDecimal;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Builder @ToString

public class Coffee {
    private int idProd;
    private String cofName;
    private Double price;
}

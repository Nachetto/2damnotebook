package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int orderid;
    private int tableid;
    private int customerid;
    private LocalDateTime orderdate;

    @Override
    public String toString() {
        return "Tipo pedido, " +
                "con id: " + orderid +
                ", Con el id de la tabla: " + tableid +
                ", Con el Id del customer " + customerid +
                " y con la fecha: " + orderdate +
                '}';
    }
}

package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Order {
    private int orderid, tableid, customerid;
    private LocalDateTime orderdate;
}

package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@NoArgsConstructor
public class Order {
    private int orderid, tableid, customerid;
    private LocalDateTime orderdate;

    public Order(int orderid, int tableid, int customerid, LocalDateTime orderdate) {
        this.orderid = orderid;
        this.tableid = tableid;
        this.customerid = customerid;
        this.orderdate = orderdate;
    }

    public Order(String fileLine) {
        String[] elemArray = fileLine.split(";");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");

        this.orderid = Integer.parseInt(elemArray[0]);
        this.tableid = Integer.parseInt(elemArray[3]);
        this.customerid = Integer.parseInt(elemArray[2]);

        try {
            this.orderdate = LocalDateTime.parse(elemArray[1], formatter1);
        } catch (DateTimeParseException e) {
            //second method
            this.orderdate = LocalDateTime.parse(elemArray[1], formatter2);
        }
    }
}

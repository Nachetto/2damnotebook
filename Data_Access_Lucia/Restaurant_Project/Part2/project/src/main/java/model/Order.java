package model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
public class Order {
    private int orderid, tableid, customerid;
    private LocalDateTime orderdate;

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

    public Order(int lastOrderId, int i, Integer value, LocalDateTime now) {
    }


    public String toStringTextFile() {
        return orderid+ ";" + orderdate  + ";" + customerid + ";" + tableid;
    }
}

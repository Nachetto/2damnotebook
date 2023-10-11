package model;

import lombok.Data;

import java.util.List;
@Data
public class OrderXML {
    private int id;
    private List<OrderItemXML> orderItemsListXML;
}

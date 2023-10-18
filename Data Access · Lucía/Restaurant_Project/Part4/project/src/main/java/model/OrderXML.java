package model;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class OrderXML {
    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "orderItems")
    private OrderItemsXML orderItems;

    public OrderItemsXML getOrderItems() {
        return orderItems;
    }

    public int getId() {
        return id;
    }
}

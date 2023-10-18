package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class OrderItemsXML {

    @XmlElement(name = "orderItem")
    private List<OrderItemXML> orderItem;

    public List<OrderItemXML> getOrderItem() {
        return orderItem;
    }
}



package model;

import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class OrdersXML {
    @XmlElement(name = "order")
    private List<OrderXML> orders;

    public List<OrderXML> getOrderListXML() {
        return orders;
    }
}


package model.xml;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrdersXML {
    @XmlElement(name = "order")
    private List<OrderXML> list;

}

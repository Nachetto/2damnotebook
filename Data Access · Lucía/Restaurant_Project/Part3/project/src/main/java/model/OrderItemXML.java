package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class OrderItemXML {
    private String menuItem;
    private int quantity;
}

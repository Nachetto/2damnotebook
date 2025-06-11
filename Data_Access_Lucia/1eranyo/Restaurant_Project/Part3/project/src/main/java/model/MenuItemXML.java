package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MenuItemXML {
    @XmlElement
    private int id;
    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement
    private double price;
}


package model;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MenuItemsXML {
    @XmlElement(name = "menuItem")
    private List<MenuItemXML> menuItems;

    public List<MenuItemXML> getMenuItems() {
        return menuItems;
    }
}


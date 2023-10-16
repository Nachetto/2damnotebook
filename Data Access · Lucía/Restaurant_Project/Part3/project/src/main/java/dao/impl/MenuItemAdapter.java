package dao.impl;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import model.MenuItem;
import model.MenuItemXML;

public class MenuItemAdapter extends XmlAdapter<MenuItemXML, MenuItem> {
    @Override
    public MenuItem unmarshal(MenuItemXML menuItemXML) {
        // Aquí puedes construir un objeto MenuItem a partir de MenuItemXML
        MenuItem menuItem = new MenuItem();
        menuItem.setId(menuItemXML.getId());
        menuItem.setName(menuItemXML.getName());
        menuItem.setDescription(menuItemXML.getDescription());
        menuItem.setPrice(menuItemXML.getPrice());
        return menuItem;
    }

    @Override
    public MenuItemXML marshal(MenuItem menuItem) {
        MenuItemXML menuItemXML = new MenuItemXML();
        menuItemXML.setId(menuItem.getId());
        menuItemXML.setName(menuItem.getName());
        menuItemXML.setDescription(menuItem.getDescription());
        menuItemXML.setPrice(menuItem.getPrice());
        return menuItemXML;
    }

}

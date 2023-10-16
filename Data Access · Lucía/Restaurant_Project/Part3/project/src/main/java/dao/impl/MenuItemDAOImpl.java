package dao.impl;

import common.config.Configuration;
import dao.MenuItemDAO;
import io.vavr.control.Either;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;
import model.MenuItem;
import model.MenuItemXML;
import model.MenuItemsXML;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Log4j2
public class MenuItemDAOImpl implements MenuItemDAO{
    // Implementa la lógica para cargar los MenuItem desde el archivo XML
    // o desde otro origen de datos, como un archivo separado o una base de datos.

    @Override
    public Either<String, List<MenuItem>> getAll() {
        try {
            Path xmlFilePath = Paths.get(Configuration.getInstance().getOrderItemsDataFile()); // Ruta al archivo XML de los MenuItem
            File file = xmlFilePath.toFile();

            JAXBContext jaxbContext = JAXBContext.newInstance(MenuItemsXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // Registra el adaptador para deserializar correctamente los elementos menuItem
            jaxbUnmarshaller.setAdapter(new MenuItemAdapter());

            MenuItemsXML menuItemsXML = (MenuItemsXML) jaxbUnmarshaller.unmarshal(file);
            if (menuItemsXML != null) {
                List<MenuItem> menuItems = new ArrayList<>();
                for (MenuItemXML menuItemXML : menuItemsXML.getMenuItems()) {
                    MenuItem menuItem = new MenuItem();
                    menuItem.setId(menuItemXML.getId());
                    menuItem.setName(menuItemXML.getName());
                    menuItem.setDescription(menuItemXML.getDescription());
                    menuItem.setPrice(menuItemXML.getPrice());
                    menuItems.add(menuItem);
                }
                return Either.right(menuItems);
            } else {
                return Either.left("Error al cargar los elementos de MenuItem desde el archivo XML.");
            }

        } catch (JAXBException e) {
            e.printStackTrace();
            log.error("Hubo un error al leer la base de datos: " + e.getMessage());
            return Either.left("Error al cargar los elementos de MenuItem desde el archivo XML.");
        }
    }

}

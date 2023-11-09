package main;

import domain.Door;
import domain.Dungeon;
import domain.Room;
import main.components.MyXMLTreeViewer;
import main.components.RightPanel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DungeonController {
    private Dungeon dungeon;
    private final MyXMLTreeViewer treeViewer;
    private final RightPanel rightPanel;
    Room roomActual;

    public DungeonController(MyXMLTreeViewer treeViewer, RightPanel rightPanel) {
        this.dungeon = new Dungeon("null");
        this.treeViewer = treeViewer;
        this.rightPanel = rightPanel;
    }

    public boolean loadDungeonFromFile(JFrame parentFrame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un archivo de mazmorra");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Parsear y cargar la mazmorra
                this.dungeon = parseDungeon(fromFileToDocument(selectedFile)); // Actualiza la instancia de Dungeon
                updateViews(fromFileToDocument(selectedFile)); // Actualiza las vistas con la nueva mazmorra
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parentFrame, "Error al cargar el archivo XML: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    private Document fromFileToDocument(File file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file);
    }


    // Método que parsea el archivo XML y crea la instancia de Dungeon
    private Dungeon parseDungeon(Document document) {
        document.getDocumentElement().normalize();

        Element dungeonElement = document.getDocumentElement();
        String dungeonName = dungeonElement.getAttribute("name");
        Dungeon dungeon = new Dungeon(dungeonName);

        NodeList roomNodes = dungeonElement.getElementsByTagName("room");
        for (int i = 0; i < roomNodes.getLength(); i++) {
            Element roomElement = (Element) roomNodes.item(i);
            Room room = getRoomFromXML(roomElement);
            dungeon.addRoom(room);
        }
        return dungeon;
    }

    private Room getRoomFromXML(Element roomElement) {
        Room room = new Room(roomElement.getAttribute("id"));
        NodeList doorNodes = roomElement.getElementsByTagName("door");
        for (int j = 0; j < doorNodes.getLength(); j++) {
            Element doorElement = (Element) doorNodes.item(j);
            Door door = new Door(doorElement.getAttribute("name"), doorElement.getAttribute("dest"));
            room.addDoor(door);
        }

        // Aquí asumimos que la descripción siempre está presente en el XML
        String description = roomElement.getElementsByTagName("description").item(0).getTextContent();
        room.setDescription(description);

        return room;
    }


    private void updateViews(Document doc) {
        // Actualiza treeViewer con la nueva estructura de Dungeon
        treeViewer.updateTree(doc);

        // Actualiza rightPanel con la información de la primera habitación
        if (!dungeon.getRooms().isEmpty()) {
            rightPanel.updatePanel(dungeon.getRooms().get(0));
        }
    }

    public void startGame() {
        // Asegurarse de que haya habitaciones cargadas en la mazmorra
        if (dungeon != null && !dungeon.getRooms().isEmpty()) {
            // Obtener la primera habitación
            roomActual = dungeon.getRooms().get(0);

            // Actualizar RightPanel con la información de la primera habitación
            rightPanel.updatePanel(roomActual);
        } else {
            JOptionPane.showMessageDialog(null, "No hay una mazmorra cargada para iniciar el juego.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void moverse(String idRoom) {
        // Encuentra la habitación con el ID deseado
        Room roomActual = null;
        for (Room room : dungeon.getRooms()) {
            if (room.getId().equals(idRoom)) {
                roomActual = room;
                break; // Detén el bucle una vez que se encuentra la habitación
            }
        }

        // Verifica si se encontró la habitación
        if (roomActual != null) {
            // Actualiza la habitación actual y la vista
            rightPanel.updatePanel(roomActual);
        } else {

            JOptionPane.showMessageDialog(null, "No puedes moverte allí.","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}


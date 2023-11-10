package main;

import domain.Door;
import domain.Room;
import main.components.RightPanel;
import main.components.MyXMLTreeViewer;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Mazmorra Víctor y Nacho");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        // Instanciar el servicio y los paneles con sus respectivos controladores
        MyXMLTreeViewer myXMLTreeViewer = new MyXMLTreeViewer();
        RightPanel rightPanel = new RightPanel();

        // El controlador maneja la lógica de negocio y la actualización de las vistas
        DungeonController controller = new DungeonController(myXMLTreeViewer, rightPanel);

        // Configurar los componentes del frame
        mainFrame.add(myXMLTreeViewer, BorderLayout.WEST);
        mainFrame.add(rightPanel, BorderLayout.CENTER);
        setupMenuBar(mainFrame, controller);
        listenerButtons(controller, rightPanel);


        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static void listenerButtons(DungeonController controller, RightPanel rightPanel) {
        rightPanel.northBtn.addActionListener(e -> {
            String direction = "Norte"; // Dirección correspondiente al botón NORTH
            String destinationRoomId = getDestinationRoomId(rightPanel.roomActual, direction);
            controller.moverse(destinationRoomId);
        });
        rightPanel.southBtn.addActionListener(e -> {
            String direction = "Sur"; // Dirección correspondiente al botón SOUTH
            String destinationRoomId = getDestinationRoomId(rightPanel.roomActual, direction);
            controller.moverse(destinationRoomId);
        });
        rightPanel.westBtn.addActionListener(e -> {
            String direction = "Oeste"; // Dirección correspondiente al botón WEST
            String destinationRoomId = getDestinationRoomId(rightPanel.roomActual, direction);
            controller.moverse(destinationRoomId);
        });
        rightPanel.eastBtn.addActionListener(e -> {
            String direction = "Este"; // Dirección correspondiente al botón EAST
            String destinationRoomId = getDestinationRoomId(rightPanel.roomActual, direction);
            controller.moverse(destinationRoomId);
        });
    }

    private static String getDestinationRoomId(Room room, String direction) {
        java.util.List<Door> doors = room.getDoors();
        for (Door door : doors) {
            if (door.getName().equals(direction)) {
                return door.getDestinationRoomId();
            }
        }
        return null; // Manejar el caso en el que no se encuentra una puerta en la dirección especificada
    }


    private static void setupMenuBar(JFrame frame, DungeonController controller) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e ->
                {
                    if (!controller.loadDungeonFromFile(frame)) {
                        JOptionPane.showMessageDialog(frame, "Error al cargar el archivo XML", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

        JMenuItem startItem = new JMenuItem("Start");
        startItem.addActionListener(e -> controller.startGame());
        menu.add(loadItem);
        menu.add(startItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }


}

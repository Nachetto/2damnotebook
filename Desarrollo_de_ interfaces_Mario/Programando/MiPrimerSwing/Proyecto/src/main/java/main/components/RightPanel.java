package main.components;

import domain.Door;
import domain.Room;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RightPanel extends JPanel {
    JTextArea textArea;
    JTextArea textAreaCentral;
    JPanel gamePanel;
    public Room roomActual;
    public JButton northBtn;
    public JButton southBtn;
    public JButton westBtn;
    public JButton eastBtn;

    public RightPanel() {
        setLayout(new GridBagLayout()); // Usar GridBagLayout en lugar de BorderLayout

        this.gamePanel = new JPanel(new BorderLayout(10, 10)); // Añadir algo de espacio entre componentes
        this.textArea = new JTextArea(10, 40); // Tamaño del JTextArea para la descripción de la habitación
        this.textArea.setEditable(false); // Hacer que el área de texto no sea editable

        this.textAreaCentral = new JTextArea(10, 40); // Tamaño del JTextArea para mensajes basados en el tamaño de la habitación
        this.textAreaCentral.setEditable(false); // Hacer que el área de texto no sea editable

        // Añadir los botones al gamePanel
        northBtn = new JButton("NORTH");
        southBtn = new JButton("SOUTH");
        westBtn = new JButton("WEST");
        eastBtn = new JButton("EAST");

        gamePanel.add(northBtn, BorderLayout.NORTH);
        gamePanel.add(southBtn, BorderLayout.SOUTH);
        gamePanel.add(westBtn, BorderLayout.WEST);
        gamePanel.add(eastBtn, BorderLayout.EAST);

        // Añadir textAreaCentral al centro del gamePanel
        gamePanel.add(new JScrollPane(textAreaCentral), BorderLayout.CENTER);

        // Crear restricciones para el layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        // Configurar restricciones para el gamePanel
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(gamePanel, constraints);

        // Configurar restricciones para el textArea (que estará debajo del gamePanel)
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.3; // Dar menos espacio al textArea
        add(scrollPane, constraints);
    }


    public void updatePanel(Room room) {
        // Actualizar el área de texto con la descripción de la habitación
        roomActual = room;
        String description = getDescriptionBasedOnRoomSize(room); // Método para obtener la descripción
        textAreaCentral.setText(description);
        textArea.setText(room.getDescription());

        // Actualizar los botones de dirección en base a las puertas disponibles en la habitación
        List<Door> doors = room.getDoors();
        updateDirectionButtons(doors);
    }

    private void updateDirectionButtons(List<Door> doors) {
        // Aquí asumimos que tienes botones para las direcciones
        // Desactivar todos los botones primero
        northBtn.setEnabled(false);
        southBtn.setEnabled(false);
        eastBtn.setEnabled(false);
        westBtn.setEnabled(false);

        // Activar los botones según las puertas disponibles
        for (Door door : doors) {
            switch (door.getName()) {
                case "Norte":
                    northBtn.setEnabled(true);
                    break;
                case "Sur":
                    southBtn.setEnabled(true);
                    break;
                case "Este":
                    eastBtn.setEnabled(true);
                    break;
                case "Oeste":
                    westBtn.setEnabled(true);
                    break;
            }
        }
    }

    private String getDescriptionBasedOnRoomSize(Room room) {
        // Puedes implementar lógica adicional aquí para generar descripciones en base al tamaño de la habitación
        // Por ejemplo, si tienes un atributo de tamaño en la clase Room, puedes usarlo para determinar la descripción
        String roomSize = room.getDescription();
        return switch (roomSize) {
            case "Habitación muy pequeña" -> "Es una habitación muy pequeña y claustrofóbica.";
            case "Inicio" -> "Estás en una habitación pequeña y acogedora. Empieza a explorar.";
            case "Habitación pequeña" -> "Esta es una habitación pequeña y acogedora.";
            case "Habitación mediana" -> "Estás en una habitación de tamaño mediano con varios objetos a tu alrededor.";
            case "Habitación grande" -> "La habitación es grande y resuena con el eco de tus pasos.";
            case "Habitación vacía" -> "La habitación parece vacía y desolada.";
            default -> "Es una habitación indescriptible.";
        };
    }

}

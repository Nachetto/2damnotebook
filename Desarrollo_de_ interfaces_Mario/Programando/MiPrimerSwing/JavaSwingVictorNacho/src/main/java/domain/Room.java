package domain;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String id;
    private List<Door> doors;
    private String description;

    // Constructor, getters, setters

    public Room(String id) {
        this.id = id;
        this.description = "null";
        this.doors = new ArrayList<>(); // Inicializar la lista de puertas aquí
    }


    public void addDoor(Door door) {
        doors.add(door);
    }

    public String getId() {
        return id;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

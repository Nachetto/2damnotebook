package domain;

public class Door {
    private String name;
    private String destinationRoomId; // ID de la habitación a la que lleva esta puerta

    // Constructor, getters, setters
    public Door(String name, String destinationRoomId) {
        this.name = name;
        this.destinationRoomId = destinationRoomId;
    }

    public String getName() {
        return name;
    }

    public String getDestinationRoomId() {
        return destinationRoomId;
    }
}

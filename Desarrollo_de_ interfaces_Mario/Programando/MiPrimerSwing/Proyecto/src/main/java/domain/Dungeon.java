package domain;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    private List<Room> rooms;
    private String name;

    public Dungeon(String name) {
        this.name = name;
        rooms = new ArrayList<>();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public String getName() {
        return name;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }



}

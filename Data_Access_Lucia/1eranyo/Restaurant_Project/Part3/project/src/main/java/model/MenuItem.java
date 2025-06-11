package model;

import lombok.Data;

@Data
public class MenuItem {
    private int id;
    private String name;
    private String description;
    private double price;
}

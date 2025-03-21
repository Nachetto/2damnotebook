package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItem {
    private int id;
    private String name;
    private String description;
    private double price;
}

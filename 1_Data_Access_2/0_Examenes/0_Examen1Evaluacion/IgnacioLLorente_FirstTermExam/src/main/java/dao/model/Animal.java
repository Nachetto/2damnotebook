package dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Animal {
    private int id;
    private String  name;
    private String species;
    private int age;
    private int habitatId;
}

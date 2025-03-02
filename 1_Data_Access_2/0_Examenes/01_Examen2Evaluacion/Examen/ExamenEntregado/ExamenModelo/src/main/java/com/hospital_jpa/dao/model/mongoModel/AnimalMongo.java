package com.hospital_jpa.dao.model.mongoModel;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalMongo {
    private String name;
    private String description;
}

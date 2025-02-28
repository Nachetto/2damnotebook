package com.hospital_jpa.domain.model.mongoModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeaponMongo {
    private String name;
    private double price;
}

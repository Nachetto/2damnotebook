package com.hospital_jpa.domain.model.mongoModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactionMongo {
    private  String name;
    private String contact;
    private String planet;
    private int numberCS;
    private String dateLastPurchase;
    private List<WeaponMongo> weaponMongos;
}

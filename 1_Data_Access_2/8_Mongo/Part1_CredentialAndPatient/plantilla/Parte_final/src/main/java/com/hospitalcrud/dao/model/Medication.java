package com.hospitalcrud.dao.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Medication {
    private String name;
}





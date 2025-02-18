package com.hospitalcrud.dao.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Doctor {
    @SerializedName("_id")
    private ObjectId id;
    private String name;
}
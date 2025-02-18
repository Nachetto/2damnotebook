package com.hospitalcrud.dao.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private int amount;
    private LocalDate date;
}

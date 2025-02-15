package com.hospitalcrud.dao.model;

import com.google.gson.annotations.SerializedName;
import com.hospitalcrud.domain.model.PatientUI;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @SerializedName("_id")
    private ObjectId id;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private Credential credential;


    @Override
    public String toString() {
        return id + ";" + name + ";" +
                birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + ";" + phone;
    }

}

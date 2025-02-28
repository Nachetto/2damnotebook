package com.hospital_jpa.dao.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Patient {
    @SerializedName("_id")
    private ObjectId id;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private List<Payment> payments;

    public Patient(String name, LocalDate birthDate, String phone) {
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.payments = new ArrayList<>();
    }

    public Patient(ObjectId id) {
        this.id = id;
    }

    public Patient(ObjectId id, String name, LocalDate birthDate, String phone) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
    }
}

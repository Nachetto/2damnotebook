package com.hospital_jpa.dao.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credential {
    @SerializedName("_id")
    private ObjectId id;
    private String username;
    private String password;
    private ObjectId patient;
    private ObjectId doctor;


    public Credential(String username, String password, ObjectId patient) {
        this.username = username;
        this.password = password;
        this.patient = patient;
        this.doctor = null;
    }
}

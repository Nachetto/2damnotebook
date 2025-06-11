package com.hospitalcrud.dao.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credential {
    @SerializedName("_id")
    private ObjectId id;
    private String username;
    private String password;
    private ObjectId patientId;
    private ObjectId doctorId;
}

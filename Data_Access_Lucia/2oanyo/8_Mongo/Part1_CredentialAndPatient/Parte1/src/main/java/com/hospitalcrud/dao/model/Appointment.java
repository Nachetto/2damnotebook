package com.hospitalcrud.dao.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Appointment {
    @SerializedName("_id")
    private int appointmentId;
    private Patient patient;
    private int doctorId;
    private LocalDateTime appointmentDate;
}
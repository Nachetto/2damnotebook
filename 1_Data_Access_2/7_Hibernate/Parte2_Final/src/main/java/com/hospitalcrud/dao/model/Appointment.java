package com.hospitalcrud.dao.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    private int doctorId;
    private LocalDateTime appointmentDate;
}

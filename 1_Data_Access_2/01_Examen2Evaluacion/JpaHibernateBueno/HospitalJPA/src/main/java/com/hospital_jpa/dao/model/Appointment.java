package com.hospital_jpa.dao.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "appointments")

@NamedQueries({
        @NamedQuery(name = "deleteAppointments", query = "delete from Appointment app where app.patient.id = :patient_id")
})
public class Appointment {
    @Id
    @Column(name = "appointment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;
    @Column(name = "doctor_id")
    private int doctorId;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @Column(name = "appointment_date")
    private LocalDate appointmentDate;
}

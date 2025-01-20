package com.hospitalcrud.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "appointments")
@NamedQueries({
        @NamedQuery(name = "Appointment.getAll", query = "FROM Appointment ")
})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private int appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    private Patient patient;

    @Column(name = "doctor_id", nullable = false)
    private int doctorId;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;
}
package com.hospitalcrud.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MedicalRecordUI {
    private int id;
    private String description;
    private String date;
    private int idPatient;
    private int idDoctor;
    private List<String> medications;
}

package com.hospitalcrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Doctor {
    private int doctor_id;
    private String name;
    private String specialization;
    private String phone;
}

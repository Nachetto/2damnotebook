package com.hospitalcrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credential {
    private String username;
    private String password;
    private int patientId;
}

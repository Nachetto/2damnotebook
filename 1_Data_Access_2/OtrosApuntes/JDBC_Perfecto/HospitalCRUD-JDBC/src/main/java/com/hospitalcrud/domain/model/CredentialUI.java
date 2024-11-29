package com.hospitalcrud.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialUI {
    private String username;
    private String password;
}

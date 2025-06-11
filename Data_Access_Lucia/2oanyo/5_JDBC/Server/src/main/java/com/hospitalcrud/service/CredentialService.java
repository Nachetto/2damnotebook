package com.hospitalcrud.service;

import com.hospitalcrud.dao.repository.jdbc.CredentialRepository;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final CredentialRepository dao;
    public CredentialService(CredentialRepository dao) {
        this.dao = dao;
    }

    public boolean isValidUsername(String username) {
        return dao.validateUsername(username);
    }

    public boolean isPasswordCorrect(String username, String password) {
        return dao.login(username, password);
    }
    public String validateCredentials(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return "Invalid username or password";
        }
        if (!isValidUsername(username)) {
            return "Invalid username";
        }
        if (!isPasswordCorrect(username, password)) {
            return "Invalid password";
        }
        return "Valid";
    }
    public boolean delete(int patientId) {
        return dao.delete(patientId);
    }
}

package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.repository.CredentialDAO;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final CredentialDAO dao;

    public CredentialService(CredentialDAO dao) {
        this.dao = dao;
    }
    public boolean isValidUsername(String username) {
        return dao.findByUsername(username).size() == 1;
    }

    public boolean isPasswordCorrect(String username, String password) {
        return dao.findByUsernameAndPassword(username, password).size() == 1;
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
}

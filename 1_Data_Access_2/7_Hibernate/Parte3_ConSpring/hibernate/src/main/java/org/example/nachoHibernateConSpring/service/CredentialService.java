package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.Credential;
import org.example.nachoHibernateConSpring.dao.repository.CredentialDAO;
import org.springframework.stereotype.Service;


@Service
public class CredentialService {
    private final CredentialDAO dao;

    public CredentialService(CredentialDAO dao) {
        this.dao = dao;
    }
    public Credential isValidUsername(String username) {
        if (dao.findByUsername(username).isEmpty()) {
            return null;
        }
        return dao.findByUsername(username).get(0);
    }

    public String validateCredentials(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return "Invalid username or password";
        }
        Credential creds = isValidUsername(username);
        if (creds == null) {
            return "Invalid username";
        }
        if (!username.equals(creds.getUsername()) || !password.equals(creds.getPassword())) {
            return "Invalid password";
        }
        return "Valid";
    }
}

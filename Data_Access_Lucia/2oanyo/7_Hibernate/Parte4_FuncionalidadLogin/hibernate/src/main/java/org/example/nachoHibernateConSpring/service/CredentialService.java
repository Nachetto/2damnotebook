package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.Credential;
import org.example.nachoHibernateConSpring.dao.model.Session;
import org.example.nachoHibernateConSpring.dao.repository.CredentialDAO;
import org.example.nachoHibernateConSpring.dao.repository.SessionDao;
import org.springframework.stereotype.Service;


@Service
public class CredentialService {
    private final CredentialDAO dao;
    private final SessionDao sessionDao;

    public CredentialService(CredentialDAO dao, SessionDao sessionDao) {
        this.dao = dao;
        this.sessionDao = sessionDao;
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

        String userType = determineUserType(creds);
        int userId = determineUserId(creds);

        Session session = new Session(userId, userType);
        sessionDao.save(session); // save JSON

        return "Valid";
    }

    private String determineUserType(Credential credential) {
        if (credential.getPatient() == null && credential.getDoctor() == null) {
            return "admin";
        } else if (credential.getPatient() != null) {
            return "patient";
        } else {
            return "doctor";
        }
    }

    private int determineUserId(Credential credential) {
        if (credential.getPatient() != null) {
            return credential.getPatient().getId();
        } else if (credential.getDoctor() != null) {
            return credential.getDoctor().getId();
        } else {
            return 0; // admin id, i wont need it
        }
    }
}

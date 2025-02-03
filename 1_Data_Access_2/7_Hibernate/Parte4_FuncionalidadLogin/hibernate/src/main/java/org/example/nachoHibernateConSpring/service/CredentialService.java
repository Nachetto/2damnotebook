package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.Credential;
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
        //TODO aqui inicializas el objeto de sesion y le asignas el tipo de usuario y  el id, no es el id de las credentials sino del patient o el user
        sessionDao.save(creds);
        //here i call dao directly, because i only do this once so there is no need to create a method in service

        return "Valid";
    }
}

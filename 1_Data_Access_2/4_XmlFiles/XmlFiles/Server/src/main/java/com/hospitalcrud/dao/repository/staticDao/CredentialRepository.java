package com.hospitalcrud.dao.repository.staticDao;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repository.CredentialDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("xml")
public class CredentialRepository implements CredentialDAO {

    //creando la lista estatica de credenciales
    private static List<Credential> credentials = new ArrayList<>(List.of(
            new Credential("admin", "admin", 1),
            new Credential("user", "user", 2)
    ));

    @Override
    public List<Credential> getAll() {
        return credentials;
    }

    @Override
    public int save(Credential c) {
        //check if the credential already exists
        for (Credential credential : credentials) {
            if (credential.getUsername().equals(c.getUsername())) {
                return 0;
            }
        }
        return credentials.add(c) ? 1 : 0;
    }

    @Override
    public void update(Credential c) {
        for (Credential credential : credentials) {
            if (credential.getPatientId() == c.getPatientId()) {
                credential.setUsername(c.getUsername());
                credential.setPassword(c.getPassword());
                break;
            }
        }
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        if (confirmation) {
            for (Credential credential : credentials) {
                if (credential.getPatientId() == id) {
                    return credentials.remove(credential);
                }
            }
        }
        return false;
    }
}

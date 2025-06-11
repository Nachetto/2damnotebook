package com.hospitalcrud.dao.respositories;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.model.Patient;

import java.util.List;

public interface CredentialRepository {

    List<Credential> getAll();
    boolean delete(int patient_id);
    void save(Patient patient);
    void update(Credential credential);
    Credential get(String username);
}

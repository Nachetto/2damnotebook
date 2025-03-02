package com.hospital_jpa.dao.interfaces;

import com.hospital_jpa.dao.model.Credential;

import java.util.List;

public interface CredentialRepository {
    List<Credential> getAll();
    boolean delete(int patient_id);
    void update(Credential credential);
    Credential get(String username);
}

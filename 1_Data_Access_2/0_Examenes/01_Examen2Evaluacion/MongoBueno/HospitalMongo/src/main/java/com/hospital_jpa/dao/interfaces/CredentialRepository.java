package com.hospital_jpa.dao.interfaces;

import com.hospital_jpa.dao.model.Credential;
import org.bson.types.ObjectId;

public interface CredentialRepository {
    void save(Credential credential);
    int delete(ObjectId patientId);
    Credential get(String username);
}

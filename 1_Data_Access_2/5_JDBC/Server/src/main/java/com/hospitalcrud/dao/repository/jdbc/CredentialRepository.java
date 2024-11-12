package com.hospitalcrud.dao.repository.jdbc;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repository.CredentialDAO;

import java.util.List;

public class CredentialRepository implements CredentialDAO {
    @Override
    public List<Credential> getAll() {
        return List.of();
    }

    @Override
    public int save(Credential c) {
        return 0;
    }

    @Override
    public void update(Credential c) {

    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;
    }
}

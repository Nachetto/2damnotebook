package com.hospitalcrud.domain.service;

import com.hospitalcrud.dao.respositories.CredentialRepository;
import com.hospitalcrud.domain.model.CredentialUI;
import org.springframework.stereotype.Service;


@Service
public class CredentialService {
    private final CredentialRepository credentialRepository;
    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public boolean login(CredentialUI userCredentialsUI) {
        if (credentialRepository.get(userCredentialsUI.getUsername()) == null)
            return false;
        else
            return credentialRepository
                .get(userCredentialsUI.getUsername())
                .getPassword().equals(userCredentialsUI.getPassword());
    }
}

package com.hospitalcrud.dao.repository.staticDAO;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repository.CredentialDAO;

import java.util.ArrayList;
import java.util.List;

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
                return  credentials.add(c) ? 1 : 0;
        }

        @Override
        public void delete(int id, boolean confirmation) {

        }
}

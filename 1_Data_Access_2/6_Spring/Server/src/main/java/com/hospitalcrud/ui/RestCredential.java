package com.hospitalcrud.ui;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.service.CredentialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/login")
public class RestCredential {

    private final CredentialService credentialService;
    public RestCredential(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Credential credential) {
        String validationError = credentialService.validateCredentials(credential.getUsername(), credential.getPassword());
        if (validationError != "Valid" ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
        }
        return ResponseEntity.ok("true");
    }

}

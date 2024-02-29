package com.example.ejercicio3examenpsp.ui;

import com.example.ejercicio3examenpsp.data.modelo.request.AuthenticationRequest;
import com.example.ejercicio3examenpsp.data.modelo.response.AuthenticationResponse;
import com.example.ejercicio3examenpsp.domain.AuthService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuth {
    private final AuthService service;
    public RestAuth(AuthService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public AuthenticationResponse loginAuth(@RequestParam("username") String username, @RequestParam("password") String password) {
        AuthenticationRequest requestAuth = new AuthenticationRequest(username, password);
        return service.authenticate(requestAuth);
    }

    @RolesAllowed({"ADMIN", "NIVEL1", "NIVEL2"})
    @GetMapping("/refresh")
    public String refresh(@RequestParam("username") String username){
        return service.refresh(username);
    }

}

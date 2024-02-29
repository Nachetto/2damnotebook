package com.example.ejercicio3examenpsp.ui;

import com.example.ejercicio3examenpsp.data.modelo.Raton;
import com.example.ejercicio3examenpsp.domain.RatonService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestRaton {
    private final RatonService service;
    public RestRaton(RatonService service) {
        this.service = service;
    }

    @GetMapping("/api/ratones")
    @RolesAllowed({"ADMIN", "NIVEL1", "NIVEL2"})
    public List<Raton> getAllRatones(){
        return service.getAllRatones();
    }

    @PostMapping("/api/ratones")
    @RolesAllowed({"ADMIN"})
    public Raton addRaton(@RequestBody Raton nuevoRaton){
        return service.addRaton(nuevoRaton);
    }

}

package com.example.ejercicio3examenpsp.ui;

import com.example.ejercicio3examenpsp.data.modelo.Informe;
import com.example.ejercicio3examenpsp.domain.AuthService;
import com.example.ejercicio3examenpsp.domain.InformeService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestInforme {
    private final InformeService service;
    private final AuthService service2;

    public RestInforme(InformeService service, AuthService service2) {
        this.service = service;
        this.service2 = service2;
    }

    @GetMapping("/api/informes")
    @RolesAllowed({"ADMIN", "NIVEL1", "NIVEL2"})
    public Informe getById(@RequestParam("id") int id){
        return service.getById(id);
    }

    @GetMapping("/api/informesrol")
    @RolesAllowed({"ADMIN", "NIVEL1", "NIVEL2"})
    public List<Informe> getByRol(@RequestParam("rol") String rol){
        // Obtener los roles del usuario autenticado
        List<String> currentUserRoles = service2.getAuthenticatedUserRoles();
        ///if (currentUserRoles.contains("NIVEL2")) manejar que te deje o no en funcion del enunciado

        // Comprobar si el rol del usuario coincide con el rol proporcionado
        if (currentUserRoles.contains(rol)) {
            // Si coinciden, devolver los informes correspondientes
            return service.getAllByRol(rol);
        } else {
            // Si no coinciden, devolver una lista vacía o lanzar una excepción, según prefieras
            return new ArrayList<>();
        }
    }

    @PostMapping("/api/addinforme")
    @RolesAllowed({"ADMIN"})
    public Informe addInforme(@RequestBody Informe nuevoInforme){
        return service.addInforme(nuevoInforme);
    }

}

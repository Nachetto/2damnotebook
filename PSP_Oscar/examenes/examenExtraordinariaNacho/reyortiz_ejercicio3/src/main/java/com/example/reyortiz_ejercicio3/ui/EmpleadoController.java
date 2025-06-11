package com.example.reyortiz_ejercicio3.ui;

import com.example.reyortiz_ejercicio3.data.modelo.Empleado;
import com.example.reyortiz_ejercicio3.domain.EmpleadoService;
import com.example.reyortiz_ejercicio3.domain.modelo.UserAuthorized;
import com.example.reyortiz_ejercicio3.domain.modelo.UserRequest;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmpleadoController {
    private final EmpleadoService service;

    @GetMapping("/login")
    public UserAuthorized login(@RequestParam("nombre") String nombre, @RequestParam("password") String password){
        return service.login(new UserRequest(nombre, password));
    }

    @GetMapping("/empleados")
    @RolesAllowed({"ADMIN"})
    public List<Empleado> getEmpleados(){
        return service.getEmpleados();
    }

    @PostMapping("/empleado")
    @RolesAllowed({"ADMIN"})
    public Empleado addEmpleado(@RequestBody Empleado empleado){
        return service.add(empleado);
    }
}

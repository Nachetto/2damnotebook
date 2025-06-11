package com.example.ejercicio3examenpsp.ui;

import com.example.ejercicio3examenpsp.data.modelo.Empleado;
import com.example.ejercicio3examenpsp.data.modelo.Movil;
import com.example.ejercicio3examenpsp.domain.AuthService;
import com.example.ejercicio3examenpsp.domain.EmpleadoService;
import com.example.ejercicio3examenpsp.ui.model.CustomEmpleado;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class RestEmpleado {

    private final EmpleadoService empleadoService;
    private final AuthService authService;


    public RestEmpleado(EmpleadoService empleadoService, AuthService authService) {
        this.empleadoService = empleadoService;
        this.authService = authService;
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public List<Empleado> getAllEmpleados() {
        return empleadoService.findAllEmpleadosWithMoviles();
    }

    //metodo que si es admin devuelva solo el nombre del empleado y la lista de todos sus moviles,
    //pero si es empleado que devuelva solo la lista de sus moviles, tambien con el nombre del empleado
    @GetMapping("/movilesrol")
    @RolesAllowed({"ADMIN", "EMPLEADO"})
    public ResponseEntity<?> getByRol() {
        String username = authService.getAuthenticatedUsername();
        List<String> roles = authService.getAuthenticatedUserRoles();

        boolean isAdmin = roles.stream()
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (isAdmin) {
            List<Empleado> empleados = empleadoService.findAllEmpleadosWithMoviles();
            List<CustomEmpleado> customEmpleados = new ArrayList<>();
            for (Empleado empleado : empleados) {
                customEmpleados.add(new CustomEmpleado(empleado.getUsername(), empleado.getMoviles()));
            }
            return ResponseEntity.ok(customEmpleados);
        } else {
            List<Movil> moviles = empleadoService.findMovilesByEmpleadoUsername(username);
            CustomEmpleado customEmpleado = new CustomEmpleado(username, moviles);
            return ResponseEntity.ok(customEmpleado);
        }
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public Empleado addEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.saveEmpleado(empleado);
    }
}
package com.example.ejercicio3examenpsp.ui;

import com.example.ejercicio3examenpsp.data.modelo.Empleado;
import com.example.ejercicio3examenpsp.data.modelo.Movil;
import com.example.ejercicio3examenpsp.domain.EmpleadoService;
import com.example.ejercicio3examenpsp.domain.MovilService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/moviles")
public class RestMovil {

    private final MovilService movilService;
    private final EmpleadoService empleadoService;

    public RestMovil(MovilService movilService, EmpleadoService empleadoService) {
        this.movilService = movilService;
        this.empleadoService = empleadoService;
    }


    
    @GetMapping("/{username}")
    @RolesAllowed({"ADMIN", "EMPLEADO"})
    public List<Movil> getMovilesByEmpleado(@PathVariable String username) {
        Empleado empleado = empleadoService.findEmpleadoByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
        return empleado.getMoviles();
    }

    @PostMapping("/{username}")
    @RolesAllowed("EMPLEADO")
    public Movil addMovilToEmpleado(@PathVariable String username, @RequestBody Movil movil) {
        Empleado empleado = empleadoService.findEmpleadoByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
        movil.setEmpleado(empleado);
        return movilService.saveMovil(movil);
    }

    @PutMapping("/{id}")
    @RolesAllowed("EMPLEADO")
    public ResponseEntity<Movil> updateMovil(@PathVariable Integer id, @RequestBody Movil movil, @RequestParam String username) {
        return movilService.findMovilByIdAndEmpleadoUsername(id, username)
                .map(m -> {
                    m.setModelo(movil.getModelo());
                    m.setAnyo(movil.getAnyo());
                    m.setCapacidad(movil.getCapacidad());
                    // Asegúrate de que el Empleado en el Movil proporcionado también se haya guardado en la base de datos
                    m.setEmpleado(movil.getEmpleado());
                    return new ResponseEntity<>(movilService.saveMovil(m), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("EMPLEADO")
    public ResponseEntity<Void> deleteMovil(@PathVariable Integer id, @RequestParam String username) {
        return movilService.findMovilByIdAndEmpleadoUsername(id, username)
                .map(m -> {
                    movilService.deleteMovilByIdAndEmpleadoUsername(id, username);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
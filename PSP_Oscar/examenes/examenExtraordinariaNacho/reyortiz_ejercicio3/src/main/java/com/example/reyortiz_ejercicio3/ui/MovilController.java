package com.example.reyortiz_ejercicio3.ui;

import com.example.reyortiz_ejercicio3.data.modelo.Movil;
import com.example.reyortiz_ejercicio3.domain.MovilService;
import com.example.reyortiz_ejercicio3.domain.modelo.MovilResponse;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovilController {
    private final MovilService service;

    @GetMapping("/moviles")
    @RolesAllowed({"ADMIN", "EMPLEADO"})
    public List<MovilResponse> getMoviles(){
        return service.getMoviles();
    }

    @PutMapping("/movil")
    @RolesAllowed({"EMPLEADO"})
    public Movil updateMovil(@RequestBody Movil movil){
        return service.updateMovil(movil);
    }

    @DeleteMapping("/movil")
    @RolesAllowed({"EMPLEADO"})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteMovil(@RequestParam int id){
        service.deleteMovil(id);
    }

}

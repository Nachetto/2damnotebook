package com.example.ejercicio3examenpsp.domain;

import com.example.ejercicio3examenpsp.data.modelo.Empleado;
import com.example.ejercicio3examenpsp.data.modelo.Movil;
import com.example.ejercicio3examenpsp.data.repositorios.EmpleadoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public Optional<Empleado> findEmpleadoByUsername(String username) {
        return empleadoRepository.findByUsername(username);
    }

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> findAllEmpleadosWithMoviles() {
        return empleadoRepository.findAllWithMoviles();
    }

    public Empleado saveEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Query("SELECT m FROM Movil m WHERE m.empleado.username = ?1")
    public List<Movil> findMovilesByEmpleadoUsername(String username) {
        return empleadoRepository.findMovilesByEmpleadoUsername(username);
    }
}
package com.example.ejercicio3examenpsp.domain;

import com.example.ejercicio3examenpsp.data.modelo.Empleado;
import com.example.ejercicio3examenpsp.data.modelo.Movil;
import com.example.ejercicio3examenpsp.data.repositorios.EmpleadoRepository;
import com.example.ejercicio3examenpsp.data.repositorios.MovilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovilService {

    private final MovilRepository movilRepository;

    public MovilService(MovilRepository movilRepository) {
        this.movilRepository = movilRepository;
    }

    public Movil saveMovil(Movil movil) {
        return movilRepository.save(movil);
    }

    public Optional<Movil> findMovilByIdAndEmpleadoUsername(Integer id, String username) {
        return movilRepository.findByIdAndEmpleadoUsername(id, username);
    }

    public void deleteMovilByIdAndEmpleadoUsername(Integer id, String username) {
        movilRepository.deleteByIdAndEmpleadoUsername(id, username);
    }
}
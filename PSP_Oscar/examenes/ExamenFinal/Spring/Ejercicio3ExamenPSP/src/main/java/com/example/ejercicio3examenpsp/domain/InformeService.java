package com.example.ejercicio3examenpsp.domain;

import com.example.ejercicio3examenpsp.data.modelo.Informe;
import com.example.ejercicio3examenpsp.data.repositorios.InformeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformeService {

    private final InformeRepository repository;

    public InformeService(InformeRepository repository) {
        this.repository = repository;
    }

    public List<Informe> getAllByRol(String rol){
        try {
            return repository.findAllByRol(rol);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public Informe getById(int id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("No se ha encontrado el informe"));
    }

    public Informe addInforme(Informe nuevoInforme){
        try {
            return repository.save(nuevoInforme);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}

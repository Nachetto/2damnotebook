package com.example.ejercicio3examenpsp.domain;

import com.example.ejercicio3examenpsp.data.modelo.Raton;
import com.example.ejercicio3examenpsp.data.repositorios.RatonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatonService {

    private final RatonRepository repository;

    public RatonService(RatonRepository repository) {
        this.repository = repository;
    }

    public List<Raton> getAllRatones(){
        return repository.findAll();
    }

    public Raton addRaton(Raton nuevoRaton){
        try {
            repository.save(nuevoRaton);
            return nuevoRaton;
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }

}

package com.example.reymortiz_ejercicio1.domain;

import com.example.reymortiz_ejercicio1.data.EquipoRepository;
import com.example.reymortiz_ejercicio1.data.modelo.Equipo;
import com.example.reymortiz_ejercicio1.domain.errores.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipoService {
    private final EquipoRepository repository;

    public List<Equipo> getEquipos(){
        return repository.findAll();
    }

    public Equipo getEquipo(int id){
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Equipo not found"));
    }

}

package com.example.reymortiz_ejercicio1.data;

import com.example.reymortiz_ejercicio1.data.modelo.Equipo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface EquipoRepository extends ListCrudRepository<Equipo, Integer> {
    @EntityGraph(attributePaths = "jugadores")
    List<Equipo> findAll();
}

package com.example.reymortiz_ejercicio1.data;

import com.example.reymortiz_ejercicio1.data.modelo.Jugador;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface JugadorRepository extends ListCrudRepository<Jugador, Integer> {
    @EntityGraph(attributePaths = {"equipo"})
    List<Jugador> getJugadorsByEquipo(int id);
}

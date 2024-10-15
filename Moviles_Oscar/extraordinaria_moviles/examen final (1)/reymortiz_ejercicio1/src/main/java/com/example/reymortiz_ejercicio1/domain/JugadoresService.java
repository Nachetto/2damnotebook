package com.example.reymortiz_ejercicio1.domain;

import com.example.reymortiz_ejercicio1.data.JugadorRepository;
import com.example.reymortiz_ejercicio1.data.modelo.Equipo;
import com.example.reymortiz_ejercicio1.data.modelo.Jugador;
import com.example.reymortiz_ejercicio1.domain.inputs.JugadorInput;
import com.example.reymortiz_ejercicio1.domain.inputs.JugadorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JugadoresService {
    private final JugadorRepository repository;
    private final JugadorMapper mapper;

    public List<Jugador> getJugadoresByEquipo(Equipo equipo){
        return repository.getJugadorsByEquipo(equipo.getId());
    }

    public Jugador addJugador(JugadorInput jugador){
        return repository.save(mapper.toJugador(jugador));
    }
}

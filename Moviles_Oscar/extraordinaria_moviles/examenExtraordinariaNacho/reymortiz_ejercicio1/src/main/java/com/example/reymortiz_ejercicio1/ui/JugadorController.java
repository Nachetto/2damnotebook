package com.example.reymortiz_ejercicio1.ui;

import com.example.reymortiz_ejercicio1.data.modelo.Equipo;
import com.example.reymortiz_ejercicio1.data.modelo.Jugador;
import com.example.reymortiz_ejercicio1.domain.JugadoresService;
import com.example.reymortiz_ejercicio1.domain.inputs.JugadorInput;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class JugadorController {
    private final JugadoresService service;

    @SchemaMapping(typeName = "Equipo", field = "jugadores")
    public List<Jugador> getJugadorsEquipo(Equipo equipo){
        return service.getJugadoresByEquipo(equipo);
    }

    @MutationMapping
    public Jugador addJugador(@Argument JugadorInput jugador){
        return service.addJugador(jugador);
    }
}

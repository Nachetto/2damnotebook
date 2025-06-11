package com.example.reymortiz_ejercicio1.domain.inputs;

import com.example.reymortiz_ejercicio1.data.modelo.Jugador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JugadorMapper {
    Jugador toJugador(JugadorInput jugadorInput);
}

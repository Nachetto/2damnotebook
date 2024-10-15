package com.example.reymortiz_ejercicio1.ui;

import com.example.reymortiz_ejercicio1.data.modelo.Equipo;
import com.example.reymortiz_ejercicio1.domain.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EquipoController {
    private final EquipoService service;

    @QueryMapping
    public List<Equipo> getEquipos(){
        return service.getEquipos();
    }

    @QueryMapping
    public Equipo getEquipo(@Argument int id){
        return service.getEquipo(id);
    }


}

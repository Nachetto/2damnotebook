package com.example.ejercicio1examenpsp.ui;

import com.example.ejercicio1examenpsp.data.modelo.Alumno;
import com.example.ejercicio1examenpsp.data.modelo.Asignatura;
import com.example.ejercicio1examenpsp.domain.servicios.AlumnoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoServicio servicio;

    @QueryMapping
    public List<Alumno> getAlumnos() {
        return servicio.findAlumnos().get();
    }

    @MutationMapping
    public Alumno addAlumno(@Argument String nombre){
        return servicio.addAlumno(nombre).get();
    }

    @SchemaMapping(typeName = "Alumno",field="asignaturas")
    public List<Asignatura> getAsignaturas(Alumno alumno){
        return servicio.getAsignaturas(alumno);
    }

}

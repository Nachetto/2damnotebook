package com.example.ejercicio1examenpsp.domain.servicios;

import com.example.ejercicio1examenpsp.data.error.ErrorObject;
import com.example.ejercicio1examenpsp.data.modelo.Alumno;
import com.example.ejercicio1examenpsp.data.modelo.Asignatura;
import com.example.ejercicio1examenpsp.data.repositorios.AlumnoRepository;
import com.example.ejercicio1examenpsp.data.repositorios.AsignaturaRepository;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlumnoServicio {

    private final AlumnoRepository repository;
    private final AsignaturaRepository asignaturaRepository;


    public AlumnoServicio(AlumnoRepository repository, AsignaturaRepository asignaturaRepository) {
        this.repository = repository;
        this.asignaturaRepository = asignaturaRepository;
    }

    public Either<ErrorObject, List<Alumno>> findAlumnos(){
        Either<ErrorObject, List<Alumno>> res;
        List<Alumno> alumnos;
        try {
            alumnos = repository.findAll();
            res = Either.right(alumnos);
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
        return res;
    }

    public Either<ErrorObject, Alumno> addAlumno(String nombre) {
        Either<ErrorObject, Alumno> res;
        Alumno nuevoAlumno = new Alumno(0, nombre, new ArrayList<>());
        try {
            repository.save(nuevoAlumno);
            res = Either.right(nuevoAlumno);
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
        return res;

    }

    public List<Asignatura> getAsignaturas(Alumno alumno) {

        return asignaturaRepository.findByAlumno(alumno.getId());
    }
}

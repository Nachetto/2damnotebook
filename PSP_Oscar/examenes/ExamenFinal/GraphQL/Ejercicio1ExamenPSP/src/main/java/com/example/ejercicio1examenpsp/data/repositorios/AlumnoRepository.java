package com.example.ejercicio1examenpsp.data.repositorios;

import com.example.ejercicio1examenpsp.data.modelo.Alumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends ListCrudRepository<Alumno, Integer> {


    @Query(""" 
            select a from Alumno a 
            JOIN FETCH a.asignaturas
            """)
    List<Alumno> findAlumnos();

}

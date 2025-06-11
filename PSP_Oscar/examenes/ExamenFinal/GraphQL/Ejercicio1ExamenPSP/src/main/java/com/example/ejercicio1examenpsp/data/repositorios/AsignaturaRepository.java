package com.example.ejercicio1examenpsp.data.repositorios;

import com.example.ejercicio1examenpsp.data.modelo.Asignatura;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignaturaRepository extends ListCrudRepository<Asignatura, Integer> {

    List<Asignatura> findByAlumno(Integer id);
}

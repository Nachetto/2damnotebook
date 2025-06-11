package com.example.reyortiz_ejercicio3.data;

import com.example.reyortiz_ejercicio3.data.modelo.Movil;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MovilRepository extends ListCrudRepository<Movil, Integer> {
    List<Movil> findAll();

    @EntityGraph(attributePaths = "empleado")
    List<Movil> findAllByEmpleado(int id);

}

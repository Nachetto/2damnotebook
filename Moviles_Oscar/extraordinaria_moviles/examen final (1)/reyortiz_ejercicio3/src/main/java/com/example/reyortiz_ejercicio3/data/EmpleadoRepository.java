package com.example.reyortiz_ejercicio3.data;

import com.example.reyortiz_ejercicio3.data.modelo.Empleado;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends ListCrudRepository<Empleado, Integer> {
    @EntityGraph(attributePaths = {"moviles"})
    List<Empleado> findAll();

    Optional<Empleado> findByNombre(String nombre);

}

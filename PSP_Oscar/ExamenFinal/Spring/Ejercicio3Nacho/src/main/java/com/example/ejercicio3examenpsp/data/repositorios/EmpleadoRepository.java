package com.example.ejercicio3examenpsp.data.repositorios;

import com.example.ejercicio3examenpsp.data.modelo.Empleado;
import com.example.ejercicio3examenpsp.data.modelo.Movil;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {
    @EntityGraph(attributePaths = {"rol"})
    Optional<Empleado> findByUsername(String username);

    @Query("SELECT e FROM Empleado e WHERE e.username = ?1")
    Empleado findWithRolesByUsername(String username);

    @Query("SELECT e FROM Empleado e JOIN FETCH e.moviles")
    List<Empleado> findAllWithMoviles();

    @Query("SELECT m FROM Movil m WHERE m.empleado.username = ?1")
    List<Movil> findMovilesByEmpleadoUsername(String username);
}
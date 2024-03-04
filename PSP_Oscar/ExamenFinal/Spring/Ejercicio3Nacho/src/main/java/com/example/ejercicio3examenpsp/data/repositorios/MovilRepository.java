package com.example.ejercicio3examenpsp.data.repositorios;

import com.example.ejercicio3examenpsp.data.modelo.Movil;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovilRepository extends CrudRepository<Movil, Integer> {
    @Query("SELECT m FROM Movil m WHERE m.id = ?1 AND m.empleado.username = ?2")
    Optional<Movil> findByIdAndEmpleadoUsername(Integer id, String username);

    @Transactional
    @Modifying
    @Query("UPDATE Movil m SET m.modelo = ?1 WHERE m.id = ?2 AND m.empleado.username = ?3")
    int updateModeloByIdAndEmpleadoUsername(String modelo, Integer id, String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM Movil m WHERE m.id = ?1 AND m.empleado.username = ?2")
    void deleteByIdAndEmpleadoUsername(Integer id, String username);
}
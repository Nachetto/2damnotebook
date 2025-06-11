package com.example.ejercicio3examenpsp.data.repositorios;

import com.example.ejercicio3examenpsp.data.modelo.Informe;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InformeRepository extends ListCrudRepository<Informe, Integer> {

    Optional<Informe>findById(int id);

    List<Informe> findAllByRol(String rol);


}

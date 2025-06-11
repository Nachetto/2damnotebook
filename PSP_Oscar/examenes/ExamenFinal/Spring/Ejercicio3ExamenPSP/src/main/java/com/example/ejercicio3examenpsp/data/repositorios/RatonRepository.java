package com.example.ejercicio3examenpsp.data.repositorios;

import com.example.ejercicio3examenpsp.data.modelo.Raton;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatonRepository extends ListCrudRepository<Raton, Integer> {
}

package com.example.ejercicio3examenpsp.data.repositorios;

import com.example.ejercicio3examenpsp.data.modelo.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<Usuario, Integer> {

    @EntityGraph(attributePaths = {"rol"}) //esto no sirve para nada en este caso porque rol lo da ya por defecto
    /*  esto por ejemplo tendria mas sentido porque si no no te los devuelve
        @EntityGraph(attributePaths = {"pedidos"})
        List<Usuario> findAll()*/
    Optional<Usuario> findByUsername(String username);


}

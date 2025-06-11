package dev.nacho.ghub.repository;

import dev.nacho.ghub.domain.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JuegoRepository extends JpaRepository<Juego, UUID> {
    Optional<Juego> findByNombre(String nombre);
}
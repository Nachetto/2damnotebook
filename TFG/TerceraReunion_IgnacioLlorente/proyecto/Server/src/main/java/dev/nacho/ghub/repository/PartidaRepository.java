package dev.nacho.ghub.repository;

import dev.nacho.ghub.domain.model.Partida;
import dev.nacho.ghub.domain.model.enumeration.EstadoPartida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PartidaRepository extends JpaRepository<Partida, UUID> {
    // Buscar partidas por anfitrión
    List<Partida> findAllByAnfitrionId(UUID anfitrionId);

    // Buscar partidas por estado (por ejemplo, ESPERANDO)
    List<Partida> findAllByEstado(EstadoPartida estado);
}
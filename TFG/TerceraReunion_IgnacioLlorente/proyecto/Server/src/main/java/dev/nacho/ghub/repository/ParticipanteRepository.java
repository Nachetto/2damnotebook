package dev.nacho.ghub.repository;

import dev.nacho.ghub.domain.model.Participante;
import dev.nacho.ghub.domain.model.Partida;
import dev.nacho.ghub.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipanteRepository extends JpaRepository<Participante, UUID> {
    // Comprobar si un usuario ya está invitado o participa
    Optional<Participante> findByUsuarioAndPartida(Usuario usuario, Partida partida);

    // Listar participantes de una partida
    List<Participante> findAllByPartida(Partida partida);

    // Listar partidas en las que participa un usuario
    List<Participante> findAllByUsuario(Usuario usuario);
}
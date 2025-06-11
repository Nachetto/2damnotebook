package dev.nacho.ghub.repository;


import dev.nacho.ghub.domain.model.Amistad;
import dev.nacho.ghub.domain.model.Usuario;
import dev.nacho.ghub.domain.model.enumeration.EstadoAmistad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AmistadRepository extends JpaRepository<Amistad, UUID> {
    // Buscar solicitud existente entre dos usuarios
    Optional<Amistad> findByUsuarioAndAmigo(Usuario usuario, Usuario amigo);

    // Listar solicitudes pendientes que ha recibido un usuario
    List<Amistad> findAllByAmigoAndEstado(Usuario amigo, EstadoAmistad estado);

    // Listar solicitudes pendientes que ha enviado un usuario
    List<Amistad> findAllByUsuarioAndEstado(Usuario usuario, EstadoAmistad estado);
}

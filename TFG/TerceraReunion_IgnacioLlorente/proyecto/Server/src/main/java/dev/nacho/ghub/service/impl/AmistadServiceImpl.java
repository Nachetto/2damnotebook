package dev.nacho.ghub.service.impl;

import dev.nacho.ghub.domain.model.Amistad;
import dev.nacho.ghub.domain.model.Usuario;
import dev.nacho.ghub.domain.model.dto.FriendRequestDTO;
import dev.nacho.ghub.domain.model.dto.FriendshipDTO;
import dev.nacho.ghub.domain.model.enumeration.EstadoAmistad;
import dev.nacho.ghub.repository.AmistadRepository;
import dev.nacho.ghub.repository.UsuarioRepository;
import dev.nacho.ghub.service.AmistadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class AmistadServiceImpl implements AmistadService {

    private final UsuarioRepository usuarioRepo;
    private final AmistadRepository amistadRepo;

    public AmistadServiceImpl(UsuarioRepository usuarioRepo, AmistadRepository amistadRepo) {
        this.usuarioRepo = usuarioRepo;
        this.amistadRepo = amistadRepo;
    }

    @Override
    public FriendshipDTO requestFriend(String requesterId, FriendRequestDTO request) {
        // 1. Obtener usuario solicitante
        Usuario requester = usuarioRepo.findById(UUID.fromString(requesterId))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 2. Obtener usuario objetivo
        Usuario target = usuarioRepo.findByNombreUsuario(request.getTargetUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario objetivo no encontrado"));

        // 3. Comprobar que no hay ya solicitud/amigos
        amistadRepo.findByUsuarioAndAmigo(requester, target).ifPresent(a -> {
            throw new IllegalStateException("Ya existe una solicitud o amistad entre ambos");
        });

        // 4. Crear la solicitud
        Amistad amistad = Amistad.builder()
                .id(UUID.randomUUID())
                .usuario(requester)
                .amigo(target)
                .estado(EstadoAmistad.PENDIENTE)
                .fechaSolicitud(LocalDateTime.now())
                .build();

        amistad = amistadRepo.save(amistad);

        // 5. Mapear a DTO
        return FriendshipDTO.builder()
                .id(amistad.getId())
                .requesterUsername(requester.getNombreUsuario())
                .targetUsername(target.getNombreUsuario())
                .estado(amistad.getEstado())
                .fechaSolicitud(amistad.getFechaSolicitud())
                .build();
    }
}

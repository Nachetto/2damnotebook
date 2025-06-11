package dev.nacho.ghub.domain.model.dto;

import dev.nacho.ghub.domain.model.enumeration.EstadoAmistad;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class FriendshipDTO {
    private UUID id;
    private String requesterUsername;
    private String targetUsername;
    private EstadoAmistad estado;
    private LocalDateTime fechaSolicitud;
}
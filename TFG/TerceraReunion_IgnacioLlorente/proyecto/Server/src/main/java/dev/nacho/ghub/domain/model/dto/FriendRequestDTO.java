package dev.nacho.ghub.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestDTO {
    // Nombre de usuario del amigo al que solicitamos
    private String targetUsername;
}
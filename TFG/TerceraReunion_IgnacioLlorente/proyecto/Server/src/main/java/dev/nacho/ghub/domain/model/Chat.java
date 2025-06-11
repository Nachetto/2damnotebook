package dev.nacho.ghub.domain.model;

import dev.nacho.ghub.domain.model.enumeration.TipoChat;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoChat tipo;

    // Si es un chat de partida, se refiere a esta partida
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partida_id", unique = true)
    private Partida partida;

    // Mensajes asociados a este chat (privado o de partida)
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensaje> mensajes;
}

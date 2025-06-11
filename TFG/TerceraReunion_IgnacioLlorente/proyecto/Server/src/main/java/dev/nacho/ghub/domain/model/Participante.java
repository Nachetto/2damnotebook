package dev.nacho.ghub.domain.model;

import dev.nacho.ghub.domain.model.enumeration.EstadoParticipante;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "Participante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participante {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    // Relación al usuario participante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación a la partida
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partida_id", nullable = false)
    private Partida partida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoParticipante estado;

    // Rol dentro del juego, si aplica (por ejemplo, "IMPOSTOR", "TRIPULANTE"...)
    @Column(length = 50)
    private String rolEnJuego;

    // Puntuación o resultado del participante
    private Integer puntuacion;
}

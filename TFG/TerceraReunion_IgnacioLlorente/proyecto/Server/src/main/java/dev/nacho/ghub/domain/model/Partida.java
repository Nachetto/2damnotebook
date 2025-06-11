package dev.nacho.ghub.domain.model;


import dev.nacho.ghub.domain.model.enumeration.EstadoPartida;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Partida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partida {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    // Relación al anfitrión (Usuario.partidasCreadas)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anfitrion_id", nullable = false)
    private Usuario anfitrion;

    // Relación al juego (Juego.partidas)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "juego_id", nullable = false)
    private Juego juego;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPartida estado;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private Integer duracionEstimada;               // en minutos
    @Column(columnDefinition = "JSON")
    private String configExtra;                     // ajustes específicos de cada juego

    // Participantes en esta partida (Participante.partida)
    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participante> participantes;

    // Chat asociado a la partida (Chat.partida)
    @OneToOne(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Chat chat;
}

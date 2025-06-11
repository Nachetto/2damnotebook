package dev.nacho.ghub.domain.model;

import dev.nacho.ghub.domain.model.enumeration.EstadoAmistad;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Amistad",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "amigo_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Amistad {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    // Quien envía la solicitud
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Quien recibe la solicitud
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amigo_id", nullable = false)
    private Usuario amigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAmistad estado;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    //fechaAceptacion sólo cuando el estado pase a ACEPTADA
    private LocalDateTime fechaAceptacion;
}

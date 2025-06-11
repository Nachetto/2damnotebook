package domain.modelo;

import lombok.Data;

import java.util.List;

@Data
public class MiCharacter {
    private final String gender;
    private final String characterName;
    private final String actorName;
    private final List<String> job;
}

package dao.retrofit.modelo;

import lombok.Data;

@Data
public class ResponseCharacter {
    private final int id;
    private final String name;
    private final String gender;
    private final String marital;
    //an array of strings
    private final String[] job;
    private final String[] workplace;
    private final String firstAppearance;
    private final String lastAppearance;
    private final String actor;
    //an arraylist of an ebject episode named episodes
    private final ResponseEpisode[] episodes;
}

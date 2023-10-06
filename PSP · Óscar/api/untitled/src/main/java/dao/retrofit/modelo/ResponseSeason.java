package dao.retrofit.modelo;

import lombok.Data;

@Data
public class ResponseSeason {
    private final int id;
    private final int number;
    private final String startDate;
    private final String endDate;
}

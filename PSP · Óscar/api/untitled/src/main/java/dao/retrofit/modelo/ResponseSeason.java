package dao.retrofit.modelo;

import lombok.Data;

@Data
public class ResponseSeason {
    private final String id;
    private final String number;
    private final String startDate;
    private final String endDate;
}

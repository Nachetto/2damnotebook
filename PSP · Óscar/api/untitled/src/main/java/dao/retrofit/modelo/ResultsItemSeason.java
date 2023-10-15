package dao.retrofit.modelo;

import lombok.Data;

import com.squareup.moshi.Json;
@Data
public class ResultsItemSeason {
	@Json(name = "id")
	private int id;
	@Json(name = "number")
	private int number;
	@Json(name = "startDate")
	private String startDate;
	@Json(name = "endDate")
	private String endDate;

	// Getters y setters
}


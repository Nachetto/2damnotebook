package dao.retrofit.modelo;

import lombok.Data;

import java.util.List;

import com.squareup.moshi.Json;
@Data
public class ResponseSeason {
	@Json(name = "responseSeason")
	private List<ResultsItemSeason> responseSeason;

	// Getters y setters
}

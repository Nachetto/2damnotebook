package dao.retrofit.modelo;

import lombok.Data;

import java.util.List;
@Data
public class ResponseCharacter {
	private List<ResultsItemCharacter> results;
}
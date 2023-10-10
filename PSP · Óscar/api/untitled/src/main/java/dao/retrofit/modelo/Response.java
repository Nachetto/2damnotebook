package dao.retrofit.modelo;

import lombok.Data;

import java.util.List;
@Data
public class Response{
	private Meta meta;
	private List<ResultsItem> results;
}
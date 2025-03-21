package dao.retrofit.modelo;

import lombok.Data;

import java.util.List;
@Data
public class ResultsItemCharacter {
	private String firstAppearance;
	private String actor;
	private Object marital;
	private String gender;
	private String lastAppearance;
	private String name;
	private int id;
	private List<Object> job;
	private List<Object> workplace;
}
package dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Visitor {
    private int id;
    private String name;
    private String email;
    private int tickets;
}

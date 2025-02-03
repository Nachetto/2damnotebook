package org.example.nachoHibernateConSpring.dao.model;

import jakarta.ejb.Singleton;
import lombok.Data;

@Singleton
@Data
public class Session {
    private String type;
    private int typeID;

    public Session(int id, String type, int userId) {
        this.type = type;
        this.typeID = typeID;
        //TODO cuando se llama al constructor, este carga sus datos en el json que se guarda en local

    }

    public Session() {

    }
}

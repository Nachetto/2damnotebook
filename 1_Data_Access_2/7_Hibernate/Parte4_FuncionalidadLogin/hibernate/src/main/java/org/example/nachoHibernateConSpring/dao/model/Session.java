package org.example.nachoHibernateConSpring.dao.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Session {
    private String userType;
    private int userId;

    public Session(int userId, String userType) {
        this.userId = userId;
        this.userType = userType;
    }
}
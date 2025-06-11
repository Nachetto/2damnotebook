package com.hospital_jpa.dao.model.mongoModel;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitorMongo {
    private String name;
    private int numTickets;
}

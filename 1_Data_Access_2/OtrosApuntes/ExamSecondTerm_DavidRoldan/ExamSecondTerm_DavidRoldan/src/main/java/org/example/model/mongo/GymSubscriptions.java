package org.example.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.hibernate.mapping.List;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GymSubscriptions {
    private ObjectId _id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Client client;
    private ArrayList<Integer> services;
}

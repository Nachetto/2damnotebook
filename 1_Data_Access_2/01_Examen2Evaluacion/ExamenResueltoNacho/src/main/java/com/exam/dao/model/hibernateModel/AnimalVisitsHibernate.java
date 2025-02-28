package com.exam.dao.model.hibernateModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Animal_Visits", schema = "IgnacioLlorente_SecondTerm")
@NamedQueries({
        @NamedQuery(name = "AnimalVisitsHibernate.findAll", query = "select a from AnimalVisitsHibernate a")
})
public class AnimalVisitsHibernate {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(name = "Visitor_ID", nullable = false)
    private int visitor_ID;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "Animal_ID", nullable = false)
    private AnimalHibernate animal_ID;

    @NotNull
    @Column(name = "Visit_Date", nullable = false)
    private Date date;

    public AnimalVisitsHibernate(int visitorID, AnimalHibernate animalID, Date from) {
        id=-1;
        this.visitor_ID=visitorID;
        this.animal_ID=animalID;
        this.date=from;
    }
}

package com.exam.dao.model.hibernateModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Animals", schema = "IgnacioLlorente_SecondTerm")
@NamedQueries({
        @NamedQuery(name = "AnimalHibernate.findByHabitat_HabitatID", query = "select a from AnimalHibernate a where a.habitat.habitatID = :habitatID")
})
public class AnimalHibernate extends HabitatHibernate {
    @Id
    @Column(name = "Animal_ID", nullable = false, length = 200)
    private int id;

    @Size(max = 200)
    @NotNull
    @Column(name = "Name", nullable = false, length = 200)
    private String  name;

    @Size(max = 200)
    @NotNull
    @Column(name = "Species", nullable = false, length = 200)
    private String species;

    @NotNull
    @Column(name = "Age", nullable = false)
    private int age;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Habitat_ID", nullable = false)
    private HabitatHibernate habitat;
}
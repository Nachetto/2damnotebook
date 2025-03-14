package com.hospital_jpa.dao.model.hibernateModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Animals", schema = "IgnacioLLorente_SecondTerm")
@NamedQueries({
        @NamedQuery(name = "AnimalHibernate.findByHabitat_HabitatID", query = "select a from AnimalHibernate a where a.habitat.habitatID = :habitatID")
})
public class AnimalHibernate {
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
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Habitat_ID", nullable = false)
    private HabitatHibernate habitat;
}
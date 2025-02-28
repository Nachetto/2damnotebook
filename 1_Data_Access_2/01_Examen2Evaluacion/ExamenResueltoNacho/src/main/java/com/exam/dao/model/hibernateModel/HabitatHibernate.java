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
@Table(name = "Habitats", schema = "IgnacioLlorente_SecondTerm")
@NamedQueries({
        @NamedQuery(name = "HabitatHibernate.findByName", query = "select h.id from HabitatHibernate h where h.name = :name")
})
public class HabitatHibernate {
    @Id
    @Column(name = "Habitat_ID", nullable = false, length = 200)
    private int habitatID;

    @Size(max = 200)
    @NotNull
    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Size(max = 200)
    @NotNull
    @Column(name = "Type", nullable = false, length = 200)
    private String type;
}

package com.hospital_jpa.dao.model.hibernateModel;

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
@Table(name = "Visitors", schema = "IgnacioLLorente_SecondTerm")
@NamedQueries({
        @NamedQuery(name = "VisitorHibernate.findByName", query = "select v.id from VisitorHibernate v where v.name = :name"),
        @NamedQuery(name = "VisitorHibernate.findById", query = "select v from VisitorHibernate v where v.id = :id")
})
public class VisitorHibernate {
    @Id
    @Column(name = "Visitor_ID", nullable = false, length = 200)
    private int id;

    @Size(max = 200)
    @NotNull
    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Size(max = 200)
    @NotNull
    @Column(name = "Email", nullable = false, length = 200)
    private String email;

    @NotNull
    @Column(name = "Tickets", nullable = false)
    private int tickets;


}

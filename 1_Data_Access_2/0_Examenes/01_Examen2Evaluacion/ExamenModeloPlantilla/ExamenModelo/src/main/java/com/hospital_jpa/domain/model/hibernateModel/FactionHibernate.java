package com.hospital_jpa.domain.model.hibernateModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "faction", schema = "examdb")
@NamedQueries({
        @NamedQuery(name = "FactionHibernate.deleteByFname", query = "delete from FactionHibernate f where f.fname = :fname"),
        @NamedQuery(name = "FactionHibernate.findByFname", query = "select f from FactionHibernate f where f.fname = :fname")
})
public class FactionHibernate {
    @Id
    @Size(max = 200)
    @Column(name = "fname", nullable = false, length = 200)
    private String fname;

    @Size(max = 200)
    @NotNull
    @Column(name = "contact", nullable = false, length = 200)
    private String contact;

    @Size(max = 200)
    @NotNull
    @Column(name = "planet", nullable = false, length = 200)
    private String planet;

    @NotNull
    @Column(name = "number_controlled_systems", nullable = false)
    private Integer numberControlledSystems;

    @Column(name = "date_last_purchase")
    private String dateLastPurchase;

    @OneToMany(mappedBy = "faction", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<WeaponHibernate> weapons;

}
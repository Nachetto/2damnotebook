package com.hospital_jpa.domain.model.hibernateModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "sales", schema = "examdb")
public class SaleHibernate {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_weapons_faction", nullable = false)
    private WeaponsFactionHibernate idWeaponsFactionHibernate;

    @NotNull
    @Column(name = "units", nullable = false)
    private Integer units;

    @NotNull
    @Column(name = "sldate", nullable = false)
    private LocalDate sldate;

}
package com.hospital_jpa.domain.model.hibernateModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "weapons_factions", schema = "examdb")
public class WeaponsFactionHibernate {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "name_faction", nullable = false)
    private FactionHibernate nameFactionHibernate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_weapon", nullable = false)
    private WeaponHibernate idWeaponHibernate;

    @ManyToOne
    @JoinColumn(name="id_weapon")
    private WeaponHibernate weapon;

}
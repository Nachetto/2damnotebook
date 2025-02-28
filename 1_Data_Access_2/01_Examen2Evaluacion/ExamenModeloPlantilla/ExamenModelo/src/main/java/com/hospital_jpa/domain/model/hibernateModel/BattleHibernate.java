package com.hospital_jpa.domain.model.hibernateModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "battles", schema = "examdb")
@NamedQueries({
        @NamedQuery(name = "getAllBattle", query = "from BattleHibernate "),
})
public class BattleHibernate {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 200)
    @NotNull
    @Column(name = "bname", nullable = false, length = 200)
    private String bname;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "faction_one", nullable = false)
    private FactionHibernate factionHibernateOne;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "faction_two", nullable = false)
    private FactionHibernate factionHibernateTwo;

    @Size(max = 200)
    @NotNull
    @Column(name = "bplace", nullable = false, length = 200)
    private String bplace;

    @NotNull
    @Column(name = "bdate", nullable = false)
    private LocalDate bdate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_spy", nullable = false)
    private SpyHibernate idSpyHibernate;

}
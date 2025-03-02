package com.hospital_jpa.domain.model.hibernateModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.List;

@Getter
@Setter
@Entity
@Data
@Table(name = "weapons", schema = "examdb")
@NamedQueries({
        @NamedQuery(name = "WeaponHibernate.deleteByFaction_FnameLike", query = "delete from WeaponHibernate w where w.faction.fname like :fname")
})
public class WeaponHibernate {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 200)
    @NotNull
    @Column(name = "wname", nullable = false, length = 200)
    private String wname;

    @NotNull
    @Column(name = "wprice", nullable = false)
    private Double wprice;

    @ManyToOne
    @JoinColumn(name = "fname")
    private FactionHibernate faction;

    @OneToMany(mappedBy = "faction", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<WeaponsFactionHibernate> weaponsFactionHibernates;

}
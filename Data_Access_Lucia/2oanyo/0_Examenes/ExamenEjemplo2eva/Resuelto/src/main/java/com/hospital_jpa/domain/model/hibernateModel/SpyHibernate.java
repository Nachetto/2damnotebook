package com.hospital_jpa.domain.model.hibernateModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "spies", schema = "examdb")
public class SpyHibernate {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 200)
    @NotNull
    @Column(name = "sname", nullable = false, length = 200)
    private String sname;

    @Size(max = 200)
    @NotNull
    @Column(name = "srace", nullable = false, length = 200)
    private String srace;

}
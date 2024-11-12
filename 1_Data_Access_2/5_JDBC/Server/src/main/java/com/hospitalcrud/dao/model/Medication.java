package com.hospitalcrud.dao.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Medication {
    @XmlTransient // This annotation is used to exclude a field from the XML representation.
    private int id;

    @XmlElement(name = "medication")
    private String medicationName;

    @XmlTransient
    private int medRecordId;
}





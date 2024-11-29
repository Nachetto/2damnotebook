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
    @XmlTransient // Para que no tenga en cuenta esto en el XML.
    private int id;

    @XmlElement(name = "medication")
    private String medicationName;

    @XmlTransient //Tampoco lo tiene en cuenta en el XML.
    private int medRecordId;
}





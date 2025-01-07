package com.hospitalcrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "medication")
@XmlAccessorType(XmlAccessType.NONE)
public class Medication {
    @XmlTransient
    private int id;
    @XmlValue
    private String medicationName;
    @XmlTransient
    private int medRecordId;
    private String dosage;


    public Medication(int id, String medicationName, int medRecordId) {
        this.id = id;
        this.medicationName = medicationName;
        this.medRecordId = medRecordId;
        this.dosage = "";
    }
    public Medication(String medicationName, int medRecordId, String dosage) {
        this.id = 0;
        this.medicationName = medicationName;
        this.medRecordId = medRecordId;
        this.dosage = "";
    }
}

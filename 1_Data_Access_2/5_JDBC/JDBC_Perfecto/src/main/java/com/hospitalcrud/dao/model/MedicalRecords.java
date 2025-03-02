package com.hospitalcrud.dao.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement(name = "medRecords")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedicalRecords {
    @XmlElement(name="medRecord")
    public List<MedicalRecord> medicalRecords;

    public MedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
}

package org.example.domain.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordXML {
    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "medications")
    private MedicationsXML medications;
    @XmlElement(name = "patient")
    private PatientXML patient;
    @XmlElement(name = "doctor")
    private String doctor;
    @XmlElement(name = "diagnosis")
    private String diagnosis;
}

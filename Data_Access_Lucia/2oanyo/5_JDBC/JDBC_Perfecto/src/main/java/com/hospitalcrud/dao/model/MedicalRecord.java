package com.hospitalcrud.dao.model;

import com.hospitalcrud.dao.mappers.files_mappers.LocalDateAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "medRecord")
@XmlAccessorType(XmlAccessType.NONE)
public class MedicalRecord {
    @XmlElement
    private int id;
    @XmlElement
    private int idPatient;
    @XmlElement(name = "doctor")
    private int idDoctor;
    @XmlElement
    private String diagnosis;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate date;
    @XmlElementWrapper(name="medications")
    @XmlElement(name = "medication")
    private List<Medication> medications;

    public MedicalRecord(int id, int idPatient, int idDoctor, String diagnosis, LocalDate date) {
        this.id = id;
        this.idPatient = idPatient;
        this.idDoctor = idDoctor;
        this.diagnosis = diagnosis;
        this.date = date;
    }
}

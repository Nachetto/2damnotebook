package com.hospitalcrud.dao.model;

import com.hospitalcrud.dao.model.xml.LocalDateAdapter;
import com.hospitalcrud.domain.model.MedRecordUI;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class MedRecord {
    private int id;
    private int idPatient;
    @XmlElement(name = "doctor")
    private int idDoctor;
    private String diagnosis;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate date;
    private List<Medication> medications;

    public MedRecordUI toMedRecordUI() {
        return new MedRecordUI(id, idPatient, idDoctor, diagnosis, date.toString(),
                medications.stream().map(m -> m.getMedicationName()).toList());
    }
}
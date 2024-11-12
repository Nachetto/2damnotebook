package com.hospitalcrud.dao.model.xml;

import com.hospitalcrud.dao.model.MedRecord;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement(name="medRecords")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedRecordsXML {
    @XmlElement(name = "medRecord")
    private List<MedRecord> medicalRecords;
}

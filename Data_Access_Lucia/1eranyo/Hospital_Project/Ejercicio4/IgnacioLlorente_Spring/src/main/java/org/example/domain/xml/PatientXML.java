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
public class PatientXML {
    @XmlElement(name = "patientID")
    private int patientID;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "contactDetails")
    private String contactDetails;
    @XmlElement(name = "personalInformation")
    private String personalInformation;
    @XmlElement(name = "username")
    private String username;
    @XmlElement(name = "password")
    private String password;

}

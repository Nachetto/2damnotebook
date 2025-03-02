package dao.model;

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
@XmlRootElement(name="AnimalVisits")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalVisitsXml {
    @XmlElement(name = "Visit")
    private List<Visit> visits;
}
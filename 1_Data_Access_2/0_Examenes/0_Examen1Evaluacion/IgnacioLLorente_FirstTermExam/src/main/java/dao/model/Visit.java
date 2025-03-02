package dao.model;

import dao.adapters.LocalDateXmlAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Visit {//name in sql: Animal_Visits

    @XmlElement(name = "AnimalID")
    private int animalId;//name in sql: Animal_ID
    @XmlElement(name = "VisitorID")
    private int visitorId;//name in sql: Visitor_ID
    @XmlJavaTypeAdapter(value = LocalDateXmlAdapter.class)
    @XmlElement(name = "VisitDate")
    private LocalDate date; //name in sql: Visit_Date


}

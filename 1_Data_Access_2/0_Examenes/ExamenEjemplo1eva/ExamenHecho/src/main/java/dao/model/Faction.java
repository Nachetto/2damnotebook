package dao.model;

import dao.util.xml.LocalDateXmlAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
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
public class Faction {
    private String name;
    private String contact;
    private String planet;
    private int numberCS;
    @XmlJavaTypeAdapter(value = LocalDateXmlAdapter.class)
    private LocalDate dateLastPurchase;
    @XmlElementWrapper(name="weapons")
    @XmlElement(name = "weapon")
    List<Weapon> weapons;

    public Faction(String name, String contact, String planet, int numberCS, LocalDate dateLastPurchase) {
        this.name = name;
        this.contact = contact;
        this.planet = planet;
        this.numberCS = numberCS;
        this.dateLastPurchase = dateLastPurchase;
    }

    @Override
    public String toString() {
        return "\nFaction{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", planet='" + planet + '\'' +
                ", numberCS=" + numberCS +
                ", dateLastPurchase=" + dateLastPurchase +
                ", weapons=" + weapons +
                '}';
    }
}

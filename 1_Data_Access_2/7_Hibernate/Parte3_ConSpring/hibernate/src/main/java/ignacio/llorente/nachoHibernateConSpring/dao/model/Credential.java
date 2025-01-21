package ignacio.llorente.nachoHibernateConSpring.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_login")
@NamedQueries({
        @NamedQuery(name = "Credential.login", query = "select c FROM Credential c where c.username = :username and c.password = :password"),
        @NamedQuery(name = "Credential.validate_username", query = "SELECT COUNT(c) FROM Credential c WHERE c.username = :username"),
        @NamedQuery(name = "Credential.getAll", query = "FROM Credential c")
})
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    private Patient patient;


    public Credential(String userName, String password, int id){
        this.id = id;
        this.username = userName;
        this.password = password;
    }
}

package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name = UtilitiesModel.CREDENTIALS, schema = UtilitiesModel.REYMORTIZ_RESTAURANT)
public class Credentials {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = UtilitiesModel.IDCREDENTIALS, nullable = false)
    private Integer idcredentials;

    @Column(name = UtilitiesModel.USERNAME, nullable = false, unique = true, length = 45)
    private String username;

    @Column(name = UtilitiesModel.PASSWORD, nullable = false, length = 45)
    private String password;


    public Credentials(Integer idcredentials, String username, String password) {
        this.idcredentials = idcredentials;
        this.username = username;
        this.password = password;
    }
}

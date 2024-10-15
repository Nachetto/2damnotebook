/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"coffee"})

@Entity
@Table(name = "encriptedcodes")
public class EncriptedCode {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_coffee")
    private int idCoffee;

    @Column(name = "code")
    private int code;

    public EncriptedCode(int code) {
        this.code = code;
    }

}
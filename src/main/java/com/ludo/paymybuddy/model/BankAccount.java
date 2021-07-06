package com.ludo.paymybuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * La classe BankAccount définit les comptes disponibles pour l'utilisateur.
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name = "rib")
    private String rib;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<Transfert> transferts;

}

package com.ludo.paymybuddy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * La classe Transaction contient les transactions enregistrées pour chaque connexion.
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private Double taxe;

    private Double amount;

    @Column(name = "date_transaction")
    private LocalDate dateTransaction;

    public Transaction(int id, User sender, User receiver, double taxe, double amout, LocalDate date) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.taxe = taxe;
        this.amount = amout;
        this.dateTransaction = date;

    }
}

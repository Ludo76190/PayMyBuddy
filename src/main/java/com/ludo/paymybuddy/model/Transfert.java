package com.ludo.paymybuddy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transfert")
public class Transfert {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    private Double amount;

    @Column(name = "date_transfert")
    private LocalDate dateTransfert;

    private String type;

    public Transfert(Double amount, String type) {
        this.amount = amount;
        this.type = type;
    }
}

package com.ludo.paymybuddy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * La classe User contient les informations utilisateur de Pay My Buddy.
 */

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String email;

    private String password;

    private double balance;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinTable(name = "friend", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_friend_id"))
    private List<User> friends;

    public User(String firstname, String lastname, String email, String password, double balance) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }
}

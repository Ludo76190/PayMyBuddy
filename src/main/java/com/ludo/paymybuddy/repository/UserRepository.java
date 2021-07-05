package com.ludo.paymybuddy.repository;

import com.ludo.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByLastname(String lastname);
    User findById(int id);

}

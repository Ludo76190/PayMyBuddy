package com.ludo.paymybuddy.integration;

import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    User user1;

    @BeforeEach
    public void setUp() {
        user1 = new User();
        user1.setFirstname("Allegaert");
        user1.setLastname("Ludovic");
        user1.setEmail("allegaertl@gmail.com");
        user1.setPassword("azerty");
        user1.setBalance(100.0);
        userRepository.save(user1);
    }

    @AfterEach
    public void clearAll() {
        this.userRepository.deleteAll();

    }

    User user;
    @Test
    public void findUserByEmailTest() {
        user = userRepository.findByEmail("allegaertl@gmail.com");

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("allegaertl@gmail.com");
    }
}

package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest()
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserDetailsService userDetailsService;

    User user1 = new User();
    User user2 = new User();
    User user3 = new User();
    List<User> listUsers = new ArrayList<>();

    @BeforeAll
    public void setup() {
        user1 = new User("Ludovic", "Allegaert", "allegaertl@gmail.com", "azerty", 0);
        user2 = new User("Audrey", "Le Pallec", "alpaudrey@gmail.com", "qsdfgh", 0);
        user3 = new User("Test", "Test", "test2@gmail.com","test_test", 0 );

        listUsers.add(user1);
        listUsers.add(user2);
        listUsers.add(user3);

    }

    @AfterEach
    public void clearAll() {
        this.userRepository.deleteAll();

    }

    @Test
    void testFindByEmail_WithValidEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(user3);

        User foundUser = userService.findByEmail(anyString());

        assertThat(foundUser).isEqualTo(user3);
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    void testFindByEmail_WhithNonValidEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        User foundUser = userService.findByEmail(anyString());

        assertThat(foundUser).isNull();
    }

    @Test
    void testSaveUser_withNonRegisteredUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user2);
        User saveUser = userService.saveUser(user2);

        assertThat(saveUser).isEqualTo(user2);
    }

    @Test
    void testSaveUser_withRegisteredUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(user2);
        when(userRepository.save(any(User.class))).thenReturn(user2);
        User saveUser = userService.saveUser(user2);

        assertThat(saveUser).isNull();
    }

}

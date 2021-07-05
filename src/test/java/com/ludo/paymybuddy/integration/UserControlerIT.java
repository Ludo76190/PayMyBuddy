package com.ludo.paymybuddy.integration;

import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.BankAccountRepository;
import com.ludo.paymybuddy.repository.TransactionRepository;
import com.ludo.paymybuddy.repository.UserRepository;
import com.ludo.paymybuddy.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControlerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    User user1 = new User();
    User user2 = new User();

    @BeforeEach
    public void setUp() {

        user1.setFirstname("Ludovic");
        user1.setLastname("Allegaert");
        user1.setEmail("allegaertl@gmail.com");
        user1.setPassword("azerty");
        user1.setBalance(100.0);
        userRepository.save(user1);

        user2.setFirstname("Audrey");
        user2.setLastname("Le Pallec");
        user2.setEmail("alpaudrey@gmail.com");
        user2.setPassword("qsdfgh");
        user2.setBalance(100.0);
        userRepository.save(user2);

    }

    @AfterEach
    public void clearAll() {
        this.bankAccountRepository.deleteAll();
        this.transactionRepository.deleteAll();
        this.userRepository.deleteAll();

    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void getUserProfilePageTest() throws Exception {
        mockMvc.perform(get("/home/profile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void getHomePageTest() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void getContactPageTest() throws Exception {
        mockMvc.perform(get("/home/contact"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void testDeleteContact() throws Exception {
        userService.addFriend(user2.getEmail());
        mockMvc.perform(get("/deleteContact/" + user2.getId()))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/home/contact"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void addFriendTest() throws Exception {
        mockMvc.perform(post("/home/addContact")
                .param("friendMail", "alpaudrey@gmail.com"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/home/contact"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void addFriendWithNullValueTest() throws Exception {
        mockMvc.perform(post("/home/addContact")
                .param("friendMail", ""))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/home/contact"))
                .andExpect(model().hasNoErrors());
    }

}

package com.ludo.paymybuddy.integration;

import com.ludo.paymybuddy.model.Transaction;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.BankAccountRepository;
import com.ludo.paymybuddy.repository.TransactionRepository;
import com.ludo.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

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
    public void getTransactionPageWithoutAuthenticationTest() throws Exception {
        mockMvc.perform(get("/home/transaction"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void getTransactionPageTest() throws Exception {
        mockMvc.perform(get("/home/transaction"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void postTransactionPageTest() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        mockMvc.perform(post("/saveSendTransaction")
                .param("mail", "alpaudrey@gmail.com")
                .param("amount", "100"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/home/transaction"))
                .andExpect(model().hasNoErrors());

    }

}

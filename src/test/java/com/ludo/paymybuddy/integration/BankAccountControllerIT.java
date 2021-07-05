package com.ludo.paymybuddy.integration;

import com.ludo.paymybuddy.model.BankAccount;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.BankAccountRepository;
import com.ludo.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    User user1 = new User();
    BankAccount bankAccount1 = new BankAccount();

    @BeforeEach
    public void setUp() {

        user1.setFirstname("Ludovic");
        user1.setLastname("Allegaert");
        user1.setEmail("allegaertl@gmail.com");
        user1.setPassword("azerty");
        user1.setBalance(100.0);
        userRepository.save(user1);

    }

    @AfterEach
    public void clearAll() {
        this.bankAccountRepository.deleteAll();
        this.userRepository.deleteAll();

    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    void TestAddBankAccount() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/addBankAccount")
                .contentType(APPLICATION_JSON)
                .param("rib", "123"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/home/profile"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    void TestDeleteBankAccount() throws Exception {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1);
        bankAccount.setRib("123");
        bankAccount.setUser(user1);
        bankAccountRepository.save(bankAccount);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/deleteBankAccount/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/home/profile"))
                .andExpect(model().hasNoErrors());
    }

}

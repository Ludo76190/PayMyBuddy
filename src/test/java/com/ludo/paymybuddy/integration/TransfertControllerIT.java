package com.ludo.paymybuddy.integration;

import com.ludo.paymybuddy.model.BankAccount;
import com.ludo.paymybuddy.model.Transfert;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.BankAccountRepository;
import com.ludo.paymybuddy.repository.TransfertRepository;
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
public class TransfertControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private TransfertRepository transfertRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    User user1 = new User();
    BankAccount bankAccount1 = new BankAccount();
    Transfert transfert1 = new Transfert();

    @BeforeEach
    public void setUp() {

        user1.setFirstname("Ludovic");
        user1.setLastname("Allegaert");
        user1.setEmail("allegaertl@gmail.com");
        user1.setPassword("azerty");
        user1.setBalance(100.0);
        userRepository.save(user1);

        bankAccount1.setUser(user1);
        bankAccount1.setRib("fr76123456789");
        bankAccountRepository.save(bankAccount1);

    }

    @AfterEach
    public void clearAll() {
        this.transfertRepository.deleteAll();
        this.bankAccountRepository.deleteAll();
        this.userRepository.deleteAll();

    }

    @Test
    public void getTransfertPageWithoutAuthenticationTest() throws Exception {
        mockMvc.perform(get("/home/transfert"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void postGetTransfertPageTest() throws Exception {
        mockMvc.perform(post("/saveGetTransfert")
                .param("rib", "fr76123456789")
                .param("amount", "1000"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/home/transfert"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void postSendTransfertPageTest() throws Exception {
        List<Transfert> transferts = new ArrayList<>();
        mockMvc.perform(post("/saveSendTransfert")
                .param("rib", "fr76123456789")
                .param("amount", "100"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/home/transfert"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    @WithMockUser("allegaertl@gmail.com")
    public void getTransfertPageTest() throws Exception {
        mockMvc.perform(get("/home/transfert"))
                .andExpect(status().isOk());
    }

}

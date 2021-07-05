package com.ludo.paymybuddy.integration;

import com.ludo.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    UserRepository userRepository;

    @AfterEach
    public void clearAll() {
        this.userRepository.deleteAll();

    }

    @Test
    public void registrationTest() throws Exception {
       mockMvc.perform(post("/user_registration")
               .param("email", "test3@gmail.com")
               .param("firstname", "test")
               .param("lastname", "PayMyBuddy")
               .param("password", "azerty"))
               .andExpect(status().is(302))
               .andExpect(view().name("redirect:/login"))
               .andExpect(model().hasNoErrors());
    }

    @Test
    public void failedRegistrationTest() throws Exception {
        mockMvc.perform(post("/user_registration")
                .param("email", "")
                .param("firstname", "test")
                .param("lastname", "PayMyBuddy")
                .param("password", "azerty"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/login"))
                .andExpect(model().hasNoErrors());
    }

}

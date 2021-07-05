package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.BankAccount;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class BankAccountServiceTest {

    @InjectMocks
    BankAccountServiceImpl bankAccountService;

    @Mock
    BankAccountRepository bankAccountRepository;

    @Mock
    UserService userService;

    BankAccount bankAccount1 = new BankAccount();
    BankAccount bankAccount2 = new BankAccount();
    List<BankAccount> listBankAccount = new ArrayList<>();
    User user1 = new User();
    User user2 = new User();

    @BeforeAll
    public void setUp() {

        user1.setFirstname("Allegaert");
        user1.setLastname("Ludovic");
        user1.setEmail("allegaertl@gmail.com");
        user1.setPassword("azerty");

        user2.setFirstname("Le Pallec");
        user2.setLastname("Audrey");
        user2.setEmail("alpaudrey@gmail.com");
        user2.setPassword("qsdfgh");

        bankAccount1.setUser(user1);
        bankAccount1.setRib("fr76123456789");

        bankAccount2.setUser(user2);
        bankAccount2.setRib("fr76987654321");

        listBankAccount.add(bankAccount1);
        listBankAccount.add(bankAccount2);

    }

    @Test
    void testSaveBankAccount() {
        when(userService.getCurrentUser()).thenReturn(user1);
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount1);

        BankAccount saveBankAccount = bankAccountService.saveBankAccount(bankAccount1.getRib());

        assertThat(saveBankAccount).isEqualTo(bankAccount1);

    }

    @Test
    void testGetAllUserBankAccountById() {

        List<BankAccount> bankAccount = bankAccountService.getAllUserBankAccountById(1);

        assertNotNull(bankAccount);

    }

}

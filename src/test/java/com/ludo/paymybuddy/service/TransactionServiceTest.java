package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.Transaction;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.TransactionRepository;
import com.ludo.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class TransactionServiceTest {

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;


    User user1 = new User();
    User user2 = new User();
    Transaction transaction1 = new Transaction();
    List<Transaction> transactions = new ArrayList<>();

    @BeforeAll
    public void setUp() {

        user1.setFirstname("Ludovic");
        user1.setLastname("Allegaert");
        user1.setEmail("allegaertl@gmail.com");
        user1.setPassword("azerty");
        user1.setBalance(100.0);

        user2.setFirstname("Audrey");
        user2.setLastname("Le Pallec");
        user2.setEmail("alpaudrey@gmail.com");
        user2.setPassword("qsdfgh");
        user2.setBalance(100.0);

        transaction1.setAmount(10.0);
        transaction1.setDateTransaction(LocalDate.now());
        transaction1.setTaxe(0.005);
        transaction1.setReceiver(user2);
        transaction1.setSender(user1);

        transactions.add(transaction1);

        Authentication auth = new UsernamePasswordAuthenticationToken(user1, null);

        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @Test
    void testGetTransactions() {
        List<Transaction> transaction = transactionService.getTransactions(user1);

        assertNotNull(transaction);

    }

    @Test
    void testSaveTransaction() {
        when(userService.getCurrentUser()).thenReturn(user1);
        when(userService.findByEmail(user2.getEmail())).thenReturn(user2);
        when(userRepository.save(user1)).thenReturn(user1);
        when(userRepository.save(user2)).thenReturn(user2);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction1);


        Transaction saveTransaction = transactionService.saveTransaction(user2.getEmail(),transaction1.getAmount());

        assertThat(saveTransaction).isEqualTo(transaction1);

    }

    @Test
    void testSaveTransactionKO() {
        when(userService.getCurrentUser()).thenReturn(user1);
        when(userService.findByEmail(user2.getEmail())).thenReturn(user2);
        when(userRepository.save(user1)).thenReturn(user1);
        when(userRepository.save(user2)).thenReturn(user2);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction1);


        Transaction saveTransaction = transactionService.saveTransaction(user2.getEmail(),user1.getBalance()+100);

        assertThat(saveTransaction).isNull();

    }

}

package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.BankAccount;
import com.ludo.paymybuddy.model.Transfert;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.BankAccountRepository;
import com.ludo.paymybuddy.repository.TransfertRepository;
import com.ludo.paymybuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest()
public class TransfertServiceTest {

    private static final Logger logger = LogManager.getLogger(TransfertServiceTest.class);

    @InjectMocks
    TransfertServiceImpl transfertService;

    @Mock
    TransfertRepository transfertRepository;

    @Mock
    BankAccountRepository bankAccountRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;

    User user1;
    BankAccount bankAccount1;
    Transfert transfert1;
    Transfert transfert2;
    List<Transfert> listTransferts = new ArrayList<>();
    List<BankAccount> bankAccountList = new ArrayList<>();

    @BeforeAll
    public void setUp() {
        user1 = new User();
        user1.setFirstname("Allegaert");
        user1.setLastname("Ludovic");
        user1.setEmail("allegaertl@gmail.com");
        user1.setPassword("azerty");
        user1.setBalance(100.0);

        bankAccount1 = new BankAccount();
        bankAccount1.setRib("fr76123456789");
        bankAccountList.add(bankAccount1);

        bankAccount1.setUser(user1);

        transfert1 = new Transfert();
        transfert1.setAmount(100.0);
        transfert1.setType("Approvisionnement");
        transfert1.setDateTransfert(LocalDate.now());
        transfert1.setBankAccount(bankAccount1);

        transfert2 = new Transfert();
        transfert2.setAmount(100.0);
        transfert2.setType("Approvisionnement");
        transfert2.setDateTransfert(LocalDate.now());
        transfert2.setBankAccount(bankAccount1);

        listTransferts.add(transfert1);
        listTransferts.add(transfert2);
    }

}

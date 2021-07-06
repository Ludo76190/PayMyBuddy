package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.BankAccount;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.BankAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger logger = LogManager.getLogger(BankAccountServiceImpl.class);

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired UserService userService;

    @Override
    public BankAccount saveBankAccount(String rib) {

        User user = userService.getCurrentUser();
        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setRib(rib);
        newBankAccount.setUser(user);
        logger.info("sauvegarde du bankAccount pour le user "+user.getFirstname()+" "+user.getLastname());

        return bankAccountRepository.save(newBankAccount);
    }

    @Override
    public List<BankAccount> getAllUserBankAccountById(Integer id) {
        logger.info("Recherche du bankAccount pour le userId " + id);
        return bankAccountRepository.findAllBankAccountByUserId(id);
    }

    @Override
    public void deleteBankAccountById(Integer id) {
        logger.info("Suppression du bankAccount pour le userId " + id);
        bankAccountRepository.deleteById(id);
    }

}

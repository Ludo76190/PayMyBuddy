package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.BankAccount;
import com.ludo.paymybuddy.model.Transfert;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.BankAccountRepository;
import com.ludo.paymybuddy.repository.TransfertRepository;
import com.ludo.paymybuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransfertServiceImpl implements TransfertService {

    private static final Logger logger = LogManager.getLogger(TransfertServiceImpl.class);

    @Autowired
    TransfertRepository transfertRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public List<Transfert> findAllTransfertByBankAccountUserId(Integer userId) {

        return transfertRepository.findAllTransfertByBankAccountUserId(userId);

    }

    @Override
    @Transactional
    public void saveTransfert(String rib, Double amount, String type) {

        User user = userService.getCurrentUser();

        BankAccount bankAccount = bankAccountRepository.findByRib(rib);

        Transfert transfert = new Transfert();
        transfert.setAmount(amount);
        transfert.setBankAccount(bankAccount);
        transfert.setDateTransfert(LocalDate.now());
        transfert.setType(type);

        if (transfert.getType().equals("Approvisionnement")) {
            user.setBalance(user.getBalance() + transfert.getAmount());
            logger.info("Approvisionnement du compte pour le user "+user.getId());
            userRepository.save(user);
        } else if (transfert.getType().equals("Versement") && user.getBalance() >= transfert.getAmount()) {
            logger.info("transfert d'argent vers un le compte "+ bankAccount.getRib());
            user.setBalance(user.getBalance() - transfert.getAmount());
            userRepository.save(user);
        } else {
            logger.info("problème lors du transfert d'argent");
            return;
        }

        transfertRepository.save(transfert);
    }

}

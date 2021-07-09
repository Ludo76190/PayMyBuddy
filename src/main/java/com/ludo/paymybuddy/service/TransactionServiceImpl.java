package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.constant.Taxe;
import com.ludo.paymybuddy.model.Transaction;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.TransactionRepository;
import com.ludo.paymybuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public Transaction saveTransaction(String mail, Double amount) {

        User sender = userService.getCurrentUser();
        User receiver = userService.findByEmail(mail);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setReceiver(receiver);
        transaction.setSender(sender);
        transaction.setDateTransaction(LocalDate.now());
        transaction.setTaxe(Taxe.TAXE_RATE);

        if (sender.getBalance() >= transaction.getAmount() + (transaction.getAmount() * Taxe.TAXE_RATE)) {
            logger.info("Sauvegarde de la transaction = "+transaction.getId());
            receiver.setBalance((double) ((int)((receiver.getBalance() + transaction.getAmount())*100))/100);
            userRepository.save(receiver);

            sender.setBalance((double) ((int)((sender.getBalance() - transaction.getAmount() - transaction.getAmount() * Taxe.TAXE_RATE)*100))/100);
            userRepository.save(sender);

        } else {
            logger.info("Sauvegarde de la transaction = "+transaction.getId() + " impossible");
            return null;
        }

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactions(User user) {
        logger.info("Recherche de la liste des transactions");
        return transactionRepository.findAllBySenderOrReceiverOrderByDateTransaction(user, user);
    }
}

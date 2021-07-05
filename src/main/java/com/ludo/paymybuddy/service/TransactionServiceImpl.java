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

        logger.info("transaction="+transaction);

        if (sender.getBalance() >= transaction.getAmount() + (transaction.getAmount() * Taxe.TAXE_RATE)) {

            receiver.setBalance(receiver.getBalance() + transaction.getAmount());
            userRepository.save(receiver);

            sender.setBalance(sender.getBalance() - transaction.getAmount() - transaction.getAmount() * Taxe.TAXE_RATE);
            userRepository.save(sender);

        } else {
            return null;
        }

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactions(User user) {
        return transactionRepository.findAllBySenderOrReceiverOrderByDateTransaction(user, user);
    }
}

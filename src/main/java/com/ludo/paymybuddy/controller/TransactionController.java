package com.ludo.paymybuddy.controller;

import com.ludo.paymybuddy.model.Transaction;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.service.TransactionService;
import com.ludo.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TransactionController {

    private static final Logger logger = LogManager.getLogger(TransactionController.class);

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/home/transaction")
    public String getTransaction(Model model) {

        logger.info("Affichage de la page des transactions");

        User user = userService.getCurrentUser();
        double balance = user.getBalance();
        model.addAttribute("balance",balance);

        List<User> friends = user.getFriends();
        model.addAttribute("friends", friends);

        List<Transaction> transactionSenderReceiver = transactionService.getTransactions(user);
        model.addAttribute("listTransactionsSenderReceiver", transactionSenderReceiver);

        return "transaction";
    }

    @PostMapping("/saveSendTransaction")
    public String saveSendTransaction(@RequestParam(value = "mail") String mail, @RequestParam(value = "amount") double amount) {

        logger.info("Sauvegarde de la transaction");

        transactionService.saveTransaction(mail, amount);

        return "redirect:/home/transaction";
    }
}

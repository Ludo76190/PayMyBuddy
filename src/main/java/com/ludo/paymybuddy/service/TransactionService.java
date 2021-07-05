package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.Transaction;
import com.ludo.paymybuddy.model.User;

import java.util.List;

public interface TransactionService {

    Transaction saveTransaction(String mail, Double amount);

    List<Transaction> getTransactions(User receiver);

}

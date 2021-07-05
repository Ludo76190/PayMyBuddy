package com.ludo.paymybuddy.repository;

import com.ludo.paymybuddy.model.Transaction;
import com.ludo.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllBySenderOrReceiverOrderByDateTransaction(User sender, User receiver);
}

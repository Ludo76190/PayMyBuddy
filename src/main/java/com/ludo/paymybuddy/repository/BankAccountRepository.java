package com.ludo.paymybuddy.repository;

import com.ludo.paymybuddy.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    List<BankAccount> findAllBankAccountByUserId(int userId);
    BankAccount findByRib(String rib);

}

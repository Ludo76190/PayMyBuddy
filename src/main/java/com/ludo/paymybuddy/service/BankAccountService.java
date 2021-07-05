package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.BankAccount;

import java.util.List;

public interface BankAccountService {

    BankAccount saveBankAccount(String rib);

    List<BankAccount> getAllUserBankAccountById(Integer id);

    void deleteBankAccountById(Integer id);

}

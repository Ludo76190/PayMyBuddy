package com.ludo.paymybuddy.service;

import com.ludo.paymybuddy.model.Transfert;

import java.util.List;

public interface TransfertService {

    List<Transfert> findAllTransfertByBankAccountUserId(Integer userID);

    void saveTransfert(String rib, Double amount, String type);

}

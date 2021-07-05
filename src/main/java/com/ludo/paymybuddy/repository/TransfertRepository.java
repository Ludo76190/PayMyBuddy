package com.ludo.paymybuddy.repository;

import com.ludo.paymybuddy.model.Transfert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransfertRepository extends JpaRepository<Transfert, Integer> {

    List<Transfert> findAllTransfertByBankAccountUserId(Integer userId);
}

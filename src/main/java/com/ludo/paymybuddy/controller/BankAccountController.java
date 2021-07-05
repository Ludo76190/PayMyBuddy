package com.ludo.paymybuddy.controller;

import com.ludo.paymybuddy.service.BankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BankAccountController {

    private static final Logger logger = LogManager.getLogger(BankAccountController.class);

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/addBankAccount")
    public String saveBankAccount(@RequestParam(value = "rib") String rib){
        logger.info("Sauvegarde du BankAccount");
        bankAccountService.saveBankAccount(rib);
        return "redirect:/home/profile";
    }

    @GetMapping("/deleteBankAccount/{id}")
    public String deleteUserBankAccount(@PathVariable(name = "id") Integer id){
        logger.info("Suppression du BankAccount");
        bankAccountService.deleteBankAccountById(id);
        return "redirect:/home/profile";
    }

}

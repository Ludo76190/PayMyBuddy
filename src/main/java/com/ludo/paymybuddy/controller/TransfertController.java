package com.ludo.paymybuddy.controller;

import com.ludo.paymybuddy.model.BankAccount;
import com.ludo.paymybuddy.model.Transfert;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.repository.BankAccountRepository;
import com.ludo.paymybuddy.security.MyUserDetails;
import com.ludo.paymybuddy.service.BankAccountService;
import com.ludo.paymybuddy.service.TransfertServiceImpl;
import com.ludo.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * gestion des urls :
 *  post : /saveGetTransfert pour sauvegarder le transfert depuis la banque
 *  post : /saveSendTransfert pour sauvegarder le transfert vers la banque
 *  get : /home/transfert pour afficher la page des transfert
 */

@Controller
public class TransfertController {

    private static final Logger logger = LogManager.getLogger(TransfertController.class);

    @Autowired
    UserService userService;

    @Autowired
    TransfertServiceImpl transfertService;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @GetMapping("/home/transfert")
    public String transfert(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        logger.info("Affichage de la page des transferts de l'utilisateur");

        User user = userService.getCurrentUser();
        double balance = user.getBalance();
        model.addAttribute("balance",balance);

        List<BankAccount> bankAccountList = bankAccountService.getAllUserBankAccountById(user.getId());
        model.addAttribute("listBankAccount", bankAccountList);

        List<Transfert> transfertList = transfertService.findAllTransfertByBankAccountUserId(user.getId());
        model.addAttribute("listTransferts", transfertList);

        return "transfert";
    }

    @PostMapping("/saveGetTransfert")
    public String saveGetTransfert(@RequestParam(value = "rib") String rib, @RequestParam(value = "amount") double amount){

        logger.info("Sauvegarde du transfert en Approvisionnement depuis la bank");

        String type = "Approvisionnement";
        transfertService.saveTransfert(rib, amount, type);
        logger.info("Sauvegarde du transfert en Approvisionnement terminé");

        return "redirect:/home/transfert";
    }

    @PostMapping("/saveSendTransfert")
    public String saveSendTransfert(@RequestParam(value = "rib") String rib, @RequestParam(value = "amount") double amount){

        logger.info("Sauvegarde du transfert en Versement vers la bank");

        String type = "Versement";
        transfertService.saveTransfert(rib, amount, type);

        logger.info("Sauvegarde du transfert en Versement terminé");

        return "redirect:/home/transfert";
    }

}

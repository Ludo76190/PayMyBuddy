package com.ludo.paymybuddy.controller;

import com.ludo.paymybuddy.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    @GetMapping("/")
    public String homepage(){
        logger.info("Affichage de la page de connexion");
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        logger.info("Affichage de la page d'enregistrement d'un nouvel utilisateur");
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

}

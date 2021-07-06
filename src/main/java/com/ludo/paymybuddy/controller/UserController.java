package com.ludo.paymybuddy.controller;

import com.ludo.paymybuddy.model.BankAccount;
import com.ludo.paymybuddy.model.User;
import com.ludo.paymybuddy.security.MyUserDetails;
import com.ludo.paymybuddy.service.BankAccountService;
import com.ludo.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * gestion des urls :
 *  post : /user_registration pour enregistrer l'utilisateur
 *  post : /home/addContact pour sauvegarder les contacts de l'utilisateur
 *  get : /home pour afficher la page d'accueil
 *  get : /home/profile pour afficher le profil de l'utilisateur
 *  get : /home/contact" pour afficher les contacts de l'utilisateur
 *  get : /deleteContact/{id} pour supprimer un contact de l'utilisateur
 *
 */

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    BankAccountService bankAccountService;

    @PostMapping("/user_registration")
    public String registration(@Valid User user, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            logger.info("erreur lors de la saisie du formulaire");
            return "registration";
        }
        logger.info("Enregistrement du user");
        userService.saveUser(user);
        logger.info("user enregistré");
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal Model model){
        logger.info("Affichage de la page d'accueil de l'utilisateur");

        User user = userService.getCurrentUser();
        double balance = user.getBalance();
        model.addAttribute("balance",balance);
        model.addAttribute("userFirstName", user.getFirstname());
        model.addAttribute("userLastName", user.getLastname());

        return "home";
    }

    @GetMapping("/home/profile")
    public String userProfile(Model model){
        logger.info("Affichage de la page du profil de l'utilisateur");
        User user = userService.getCurrentUser();
        List<BankAccount> listUserBankAccount= bankAccountService.getAllUserBankAccountById(user.getId());
        model.addAttribute("listBankAccount",listUserBankAccount);
        return "profile";
    }

    @GetMapping("/home/contact")
    public String contact(@AuthenticationPrincipal MyUserDetails userDetails, Model model){
        logger.info("Affichage de la page des contacts de l'utilisateur");
        User user = userService.getCurrentUser();
        List<User> friends = user.getFriends();
        model.addAttribute("listContact",friends);
        return "contact";
    }

    @PostMapping("/home/addContact")
    public String addFriend(@RequestParam(value = "friendMail") String friendMail, HttpServletResponse response) {
        User addFriend = userService.addFriend(friendMail);
        logger.info("ajout de l'utisateur " + addFriend.getEmail() +" en ami");

        if (addFriend != null) {
            logger.info("SUCCESS - addFriend POST request - Person " + friendMail);
            response.setStatus(200);
        } else {
            logger.error("FAILED - addFriend POST request - Person " + friendMail);
            response.setStatus(404);
        }
        return "redirect:/home/contact";

    }

    @GetMapping("/deleteContact/{id}")
    public String deleteUserContact(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal MyUserDetails userDetails){
        logger.info("suppression d'un contact");
        User owner = userService.getCurrentUser();
        userService.deleteFriend(owner, id);

        return "redirect:/home/contact";
    }

}

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
            return "registration";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal MyUserDetails userDetails, Model model){
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
